package com.bleizing.pos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.dto.CreateProductRequest;
import com.bleizing.pos.dto.CreateProductReseponse;
import com.bleizing.pos.dto.DeleteProductRequest;
import com.bleizing.pos.dto.DeleteProductResponse;
import com.bleizing.pos.dto.GetProductResponse;
import com.bleizing.pos.dto.GetProductWrapper;
import com.bleizing.pos.dto.UpdateProductRequest;
import com.bleizing.pos.dto.UpdateProductResponse;
import com.bleizing.pos.error.DataExistsException;
import com.bleizing.pos.error.DataNotFoundException;
import com.bleizing.pos.model.Product;
import com.bleizing.pos.model.Store;
import com.bleizing.pos.repository.ProductRepository;
import com.bleizing.pos.repository.StoreRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StoreRepository storeRepository;
	
	@Logged
	public GetProductResponse get(Long id, String code) throws Exception {
		List<Product> products;
		if (id == 0) {
			if (Objects.isNull(code) || code.isBlank()) {
				throw new Exception("Code must be filled");
			}
			id = storeRepository.findByCodeAndActiveTrue(code).orElseThrow(() -> new DataNotFoundException("Store not found")).getId();
		}
		products = productRepository.findByStoreIdAndActiveTrue(id).orElseThrow(() -> new DataNotFoundException("Products not found"));
		
		List<GetProductWrapper> wrapper = new ArrayList<>();
		products.stream().forEach(product -> {
			wrapper.add(GetProductWrapper.builder()
					.name(product.getName())
					.code(product.getCode())
					.price(product.getPrice())
					.build());
		});
		
		return GetProductResponse.builder().products(wrapper).build();
	}
	
	@Logged
	public CreateProductReseponse create(CreateProductRequest request, Long id, Long userId) throws Exception {
		Store store;
		if (id == 0) {
			if (Objects.isNull(request.getStoreCode()) || request.getStoreCode().isBlank()) {
				throw new Exception("Code must be filled");
			}
			store = storeRepository.findByCodeAndActiveTrue(request.getStoreCode()).orElseThrow(() -> new DataNotFoundException("Store not found"));
		} else {
			store = storeRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new DataNotFoundException("Store not found"));
		}
		
		if (productRepository.findByCodeAndActiveTrue(request.getCode()).isPresent()) {
			throw new DataExistsException("Product code already exists");
		}
		
		Product product = Product.builder()
				.code(request.getCode())
				.name(request.getName())
				.price(request.getPrice())
				.store(store)
				.build();
		product.setCreatedBy(userId);
		product = productRepository.saveAndFlush(product);
		
		return CreateProductReseponse.builder().id(product.getId()).build();
	}
	
	@Logged
	public UpdateProductResponse update(UpdateProductRequest request, Long storeId, Long userId) {
		Product product;
		if (storeId != 0) {
			product = productRepository.findByCodeAndStoreIdAndActiveTrue(request.getCode(), storeId).orElseThrow(() -> new DataNotFoundException("Product not found"));
		} else {
			product = productRepository.findByCodeAndActiveTrue(request.getCode()).orElseThrow(() -> new DataNotFoundException("Product not found"));
		}
		
		product.setUpdatedBy(userId);
		if (!Objects.isNull(request.getName()) && !request.getName().isBlank()) {
			product.setName(request.getName());
		}
		if (!Objects.isNull(request.getPrice())) {
			product.setPrice(request.getPrice());
		}
		
		productRepository.save(product);
		
		return UpdateProductResponse.builder().success(true).build();
	}
	
	@Logged
	public DeleteProductResponse delete(DeleteProductRequest request, Long storeId, Long userId) {
		Product product;
		if (storeId != 0) {
			product = productRepository.findByCodeAndStoreIdAndActiveTrue(request.getCode(), storeId).orElseThrow(() -> new DataNotFoundException("Product not found"));
		} else {
			product = productRepository.findByCodeAndActiveTrue(request.getCode()).orElseThrow(() -> new DataNotFoundException("Product not found"));
		}
		
		product.setUpdatedBy(userId);
		product.setActive(false);
		productRepository.save(product);
		
		return DeleteProductResponse.builder().success(true).build();
	}
}
