package com.bleizing.pos.listener;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bleizing.pos.model.Menu;
import com.bleizing.pos.model.Permission;
import com.bleizing.pos.model.Product;
import com.bleizing.pos.model.Role;
import com.bleizing.pos.model.Store;
import com.bleizing.pos.model.User;
import com.bleizing.pos.repository.MenuRepository;
import com.bleizing.pos.repository.PermissionRepository;
import com.bleizing.pos.repository.ProductRepository;
import com.bleizing.pos.repository.RoleRepository;
import com.bleizing.pos.repository.StoreRepository;
import com.bleizing.pos.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Startup Listener");
		initData();
    }
	
	private void initData() {
		User user = User.builder()
				.name("tes")
				.email("tes@tes.com")
				.password("tes")
				.build();
		user.setCreatedBy(1111L);
		userRepository.save(user);
		
		roleRepository.save(Role.builder().name("SUPERADMIN").build());
		permissionRepository.save(Permission.builder().name("WRITE").build());
		
		Store store = Store.builder()
				.name("Toko A")
				.code("S1")
				.build();
		storeRepository.save(store);
				
		productRepository.save(Product.builder()
				.name("Test")
				.code("T1")
				.price(new BigDecimal(100.50))
				.store(store)
				.build());
		
		menuRepository.save(Menu.builder()
				.name("Product")
				.code("product")
				.path("/product")
				.build());
	}
}
