package com.bleizing.pos.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.constant.ErrorConstant;
import com.bleizing.pos.dto.AddToCartRequest;
import com.bleizing.pos.dto.AddToCartResponse;
import com.bleizing.pos.dto.CartPaymentRequest;
import com.bleizing.pos.dto.CartPaymentResponse;
import com.bleizing.pos.dto.GetCartResponse;
import com.bleizing.pos.dto.GetCartWrapper;
import com.bleizing.pos.dto.PaymentUpdateRequest;
import com.bleizing.pos.dto.PaymentUpdateResponse;
import com.bleizing.pos.enumeration.BankingCategory;
import com.bleizing.pos.enumeration.PaymentStatus;
import com.bleizing.pos.error.AlreadyPaymentException;
import com.bleizing.pos.error.CartNotExistException;
import com.bleizing.pos.error.DataNotFoundException;
import com.bleizing.pos.error.OutOfStockException;
import com.bleizing.pos.error.QuanityMinimumException;
import com.bleizing.pos.model.Cart;
import com.bleizing.pos.model.CartItem;
import com.bleizing.pos.model.Payment;
import com.bleizing.pos.model.Product;
import com.bleizing.pos.repository.CartItemRepository;
import com.bleizing.pos.repository.CartRepository;
import com.bleizing.pos.repository.PaymentRepository;

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
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Logged
	public AddToCartResponse add(AddToCartRequest request, Long userId) {
		Cart cart;
		List<CartItem> cartItems;
		
		Product product = productService.getProductByCode(request.getProductCode());
		
		if (product.getStock() < request.getQuantity()) {
			throw new OutOfStockException(ErrorConstant.OUT_OF_STOCK.getDescription());
		}
		
		Optional<Cart> cartOptional = cartRepository.findByUserIdAndCompleteFalse(userId);
		if (!cartOptional.isPresent()) {
			if (request.getQuantity() < 1) {
				throw new QuanityMinimumException(ErrorConstant.QUANTITY_MINIMUM.getDescription());
			}
			
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
			
			CartItem cartItem = cartItems.stream()
					.filter(item -> item.getProduct().getCode().equals(request.getProductCode()))
					.findFirst()
					.orElse(CartItem.builder()
							.cart(cart)
							.product(product)
							.build());
			
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
							.productCode(cartItem.getProduct().getCode())
							.productName(cartItem.getProduct().getName())
							.quantity(cartItem.getQuantity())
							.availableToBuy(cartItem.getProduct().isActive() ? true : false)
							.build());
				});
			}
		}
		
		return GetCartResponse.builder().carts(wrapper).build();
	}
	
	@Logged
	public CartPaymentResponse payment(CartPaymentRequest request, Long userId) {
		Payment payment = null;
		
		Cart cart = cartRepository.findByUserIdAndCompleteFalse(userId).orElseThrow(() -> new CartNotExistException(ErrorConstant.CART_NOT_EXIST.getDescription()));
		
		Optional<Payment> paymentOptional = paymentRepository.findByCartIdAndActiveTrue(cart.getId());
		if (!paymentOptional.isPresent()) {
			payment = Payment.builder()
					.cart(cart)
					.invoiceNumber(generateInvoiceNumber())
					.totalPrice(request.getTotalPrice())
					.paymentMethod(BankingCategory.valueOf(request.getPaymentMethod()))
					.paymentStatus(PaymentStatus.WAITING_FOR_PAYMENT)
					.paymentIssueAt(LocalDateTime.now())
					.build();
			
			paymentRepository.save(payment);
			
			if (!Objects.isNull(request.getAddress())) {
				cart.setAddress(request.getAddress());
			}
			
			if (!Objects.isNull(request.getPhone())) {
				cart.setPhone(request.getPhone());
			}
			cart.setPayment(true);
			
			cartRepository.save(cart);
			
		} else {
			throw new AlreadyPaymentException(ErrorConstant.ALREADY_PAYMENT.getDescription());
		}
		
		return CartPaymentResponse.builder().invoiceNumber(payment.getInvoiceNumber()).build();
	}
	
	@Logged
	public PaymentUpdateResponse paymentUpdate(PaymentUpdateRequest request) {
		PaymentStatus paymentStatus;
		
		Payment payment = paymentRepository.findByInvoiceNumberAndActiveTrue(request.getInvoiceNumber()).orElseThrow(() -> new DataNotFoundException(ErrorConstant.PAYMENT_NOT_FOUND.getDescription()));
		Cart cart = payment.getCart();
		
		if (cart.isComplete()) {
			throw new AlreadyPaymentException(ErrorConstant.ALREADY_PAYMENT.getDescription());
		}
		
		try {
			paymentStatus = PaymentStatus.valueOf(request.getStatus().toUpperCase());
		} catch (Exception e) {
			paymentStatus = PaymentStatus.CANCEL;
		}
		
		if (paymentStatus.equals(PaymentStatus.SETTLEMENT) || paymentStatus.equals(PaymentStatus.DENIED) || paymentStatus.equals(PaymentStatus.CANCEL)) {
			payment.setPaymentAt(LocalDateTime.now());
			cart.setComplete(true);
			cartRepository.save(cart);
		}
		
		payment.setPaymentStatus(paymentStatus);
		paymentRepository.save(payment);
		
		return PaymentUpdateResponse.builder().success(true).build();
	}
	
	private String generateInvoiceNumber() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
}
