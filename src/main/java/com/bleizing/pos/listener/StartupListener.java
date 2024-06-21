package com.bleizing.pos.listener;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bleizing.pos.model.Menu;
import com.bleizing.pos.model.MenuRolePermission;
import com.bleizing.pos.model.Permission;
import com.bleizing.pos.model.Product;
import com.bleizing.pos.model.Role;
import com.bleizing.pos.model.Store;
import com.bleizing.pos.model.User;
import com.bleizing.pos.model.UserRole;
import com.bleizing.pos.model.UserStore;
import com.bleizing.pos.repository.MenuRepository;
import com.bleizing.pos.repository.MenuRolePermissionRepository;
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
	
	@Autowired
	private MenuRolePermissionRepository menuRolePermissionRepository;
	
	@Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Startup Listener");
		initData();
    }
	
	private void initData() {
		log.info("Start initData");
		
		User user = User.builder()
				.name("superadmin")
				.email("superadmin@tes.com")
				.password("superadmin")
				.build();
		user.setCreatedBy(1111L);
		userRepository.save(user);
		
		User user1 = User.builder()
				.name("manager")
				.email("manager@tes.com")
				.password("manager")
				.build();
		userRepository.save(user1);
		
		Role role = Role.builder().name("SUPERADMIN").build();
		roleRepository.save(role);
		userRoleRepository.save(UserRole.builder()
				.role(role)
				.user(user)
				.build());
		
		Role role1 = Role.builder().name("MANAGER").build();
		roleRepository.save(role1);
		userRoleRepository.save(UserRole.builder()
				.role(role1)
				.user(user1)
				.build());
		
		Permission permission = Permission.builder().name("WRITE").build();
		permissionRepository.save(permission);
		
		Permission permission1 = Permission.builder().name("READ").build();
		permissionRepository.save(permission1);
		
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
				.name("Test1")
				.code("T1")
				.price(new BigDecimal(100))
				.store(store)
				.build());
		productRepository.save(Product.builder()
				.name("Test2")
				.code("T2")
				.price(new BigDecimal(100.50))
				.store(store)
				.build());
		
		Menu menu = Menu.builder()
				.name("Product")
				.code("product")
				.path("/product")
				.build();
		menuRepository.save(menu);
		
		Menu menu1 = Menu.builder()
				.name("Store")
				.code("store")
				.path("/store")
				.build();
		menuRepository.save(menu1);
		
		userStoreRepository.save(UserStore.builder()
				.store(store1)
				.user(user1)
				.build());
		
		menuRolePermissionRepository.save(MenuRolePermission.builder()
				.menu(menu)
				.role(role)
				.permission(permission)
				.build());
		menuRolePermissionRepository.save(MenuRolePermission.builder()
				.menu(menu)
				.role(role)
				.permission(permission1)
				.build());
		menuRolePermissionRepository.save(MenuRolePermission.builder()
				.menu(menu1)
				.role(role)
				.permission(permission)
				.build());
		menuRolePermissionRepository.save(MenuRolePermission.builder()
				.menu(menu1)
				.role(role)
				.permission(permission1)
				.build());
		menuRolePermissionRepository.save(MenuRolePermission.builder()
				.menu(menu1)
				.role(role1)
				.permission(permission1)
				.build());
		
		log.info("End initData");
	}
}
