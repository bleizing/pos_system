package com.bleizing.pos.seeder;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bleizing.pos.model.Product;
import com.bleizing.pos.model.Store;
import com.bleizing.pos.repository.ProductRepository;
import com.bleizing.pos.repository.StoreRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductSeeder implements CommandLineRunner {
	
	private ProductRepository productRepository;
	private StoreRepository storeRepository;
	
	@Value("${seeder.action}")
	private boolean action;
	
	@Value("${seeder.limit}")
	private int limit;
	
	@Autowired
	public ProductSeeder(ProductRepository productRepository, StoreRepository storeRepository) {
		this.productRepository = productRepository;
		this.storeRepository = storeRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("Start ProductSeeder");
		seed();
	}
	
	private void seed() {
		if (action) {
			Store store = Store.builder()
					.name("Store Test 1")
					.code("ST1")
					.build();
			storeRepository.save(store);
			
			IntStream.range(0, limit).forEach(i -> {
				productRepository.save(Product.builder()
						.name("Product Test " + i)
						.code("PT" + i)
						.price(new BigDecimal(100))
						.store(store)
						.stock((int) (Math.random() * 100) + 1)
						.build());
			});
		}
	}
}
