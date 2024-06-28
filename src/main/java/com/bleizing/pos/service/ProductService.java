package com.bleizing.pos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.dto.GetProductResponse;
import com.bleizing.pos.dto.GetProductWrapper;
import com.bleizing.pos.error.DataNotFoundException;
import com.bleizing.pos.model.Product;
import com.bleizing.pos.repository.ProductRepository;
import com.bleizing.pos.repository.StoreRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StoreRepository storeRepository;
	
	public GetProductResponse get(Long id, String code) throws Exception {
		List<Product> products;
		if (id == 0) {
			if (Objects.isNull(code) || code.isBlank()) {
				throw new Exception("Code must be filled");
			}
			id = storeRepository.findByCodeAndActiveTrue(code).orElseThrow(() -> new DataNotFoundException("Products not found")).getId();
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
}
