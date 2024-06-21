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
import com.bleizing.pos.model.UserRole;
import com.bleizing.pos.model.UserStore;
import com.bleizing.pos.repository.MenuRepository;
import com.bleizing.pos.repository.PermissionRepository;
import com.bleizing.pos.repository.ProductRepository;
import com.bleizing.pos.repository.RoleRepository;
import com.bleizing.pos.repository.StoreRepository;
import com.bleizing.pos.repository.UserRepository;
import com.bleizing.pos.repository.UserRoleRepository;
import com.bleizing.pos.repository.UserStoreRepository;

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
	
	@Autowired
	private UserStoreRepository userStoreRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
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
		
		User user1 = User.builder()
				.name("tes1")
				.email("tes1@tes.com")
				.password("tes1")
				.build();
		userRepository.save(user1);
		
		Role role = Role.builder().name("SUPERADMIN").build();
		roleRepository.save(role);
		
		userRoleRepository.save(UserRole.builder()
				.role(role)
				.user(user)
				.build());
		userRoleRepository.save(UserRole.builder()
				.role(role)
				.user(user1)
				.build());
		
		permissionRepository.save(Permission.builder().name("WRITE").build());
		
		Store store = Store.builder()
				.name("Toko A")
				.code("S1")
				.build();
		storeRepository.save(store);
		
		Store store1 = Store.builder()
				.name("Toko B")
				.code("S2")
				.build();
		storeRepository.save(store1);
				
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
		
		userStoreRepository.save(UserStore.builder()
				.store(store)
				.user(user)
				.build());
		userStoreRepository.save(UserStore.builder()
				.store(store1)
				.user(user)
				.build());
		userStoreRepository.save(UserStore.builder()
				.store(store1)
				.user(user1)
				.build());
	}
}
