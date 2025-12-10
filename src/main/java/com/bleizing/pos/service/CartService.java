package com.bleizing.pos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.constant.ErrorConstant;
import com.bleizing.pos.dto.AddToCartRequest;
import com.bleizing.pos.dto.AddToCartResponse;
import com.bleizing.pos.dto.GetCartResponse;
import com.bleizing.pos.dto.GetCartWrapper;
import com.bleizing.pos.error.QuanityMinimumException;
import com.bleizing.pos.model.Cart;
import com.bleizing.pos.model.CartItem;
import com.bleizing.pos.model.Product;
import com.bleizing.pos.repository.CartItemRepository;
import com.bleizing.pos.repository.CartRepository;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;

	@Logged
	public AddToCartResponse add(AddToCartRequest request, Long userId) {
		Cart cart;
		List<CartItem> cartItems;
		Product product = productService.getProduct(Long.valueOf(request.getProductId()));
		
		Optional<Cart> cartOptional = cartRepository.findByUserIdAndCompleteFalse(userId);
		if (!cartOptional.isPresent()) {
			if (request.getQuantity() < 1) {
				throw new QuanityMinimumException(ErrorConstant.QUANTITY_MINIMUM.getDescription());
			}
			
//			try {
//				product = productService.getProduct(Long.valueOf(request.getProductId()));
//			} catch (Exception e) {
//				e.printStackTrace();
//				return AddToCartResponse.builder().success(false).build();
//			}
			
			cart = Cart.builder()
					.user(userService.getUserLoggedIn(userId))
					.build();
			cart = cartRepository.saveAndFlush(cart);
			
			CartItem cartItem = CartItem.builder()
					.product(product)
					.quantity(request.getQuantity())
					.cart(cart)
					.build();
			cartItemRepository.save(cartItem);
		} else {
			cart = cartOptional.get();
			cartItems = cartItemRepository.findByCartId(cart.getId()).get();
			
			CartItem cartItem = null;
			for (CartItem item : cartItems) {
				if (item.getProduct().getId() == Long.valueOf(request.getProductId())) {
					cartItem = item;
					break;
				}
			}
			
			if (cartItem == null) {
				cartItem = CartItem.builder()
						.cart(cart)
						.product(product)
						.build();
			}
			
			if (request.getQuantity() > 0) {
				cartItem.setQuantity(request.getQuantity());
				cartItemRepository.save(cartItem);
			} else {
				cartItemRepository.delete(cartItem);
				cartItems.remove(cartItem);
			}
			
			if (cartItems.size() == 0) {
				cartRepository.delete(cart);
			}
		}
		
		return AddToCartResponse.builder().success(true).build();
	}
	
	@Logged
	public GetCartResponse get(Long userId) {
		List<GetCartWrapper> wrapper = new ArrayList<>();
		Cart cart = null;
		
		Optional<Cart> cartOptional = cartRepository.findByUserIdAndCompleteFalse(userId);
		if (cartOptional.isPresent()) {
			cart = cartOptional.get();
		}
		
		if (cart != null) {
			List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId()).get();
			if (cartItems != null) {
				cartItems.stream().forEach((cartItem) -> {
					wrapper.add(GetCartWrapper.builder()
							.productId(cartItem.getProduct().getId())
							.productName(cartItem.getProduct().getName())
							.quantity(cartItem.getQuantity())
							.build());
				});
			}
		}
		
		return GetCartResponse.builder().carts(wrapper).build();
	}
}
