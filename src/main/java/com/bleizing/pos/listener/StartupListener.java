package com.bleizing.pos.listener;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bleizing.pos.constant.PermissionContstant;
import com.bleizing.pos.model.Menu;
import com.bleizing.pos.model.MenuRolePermission;
import com.bleizing.pos.model.Permission;
import com.bleizing.pos.model.Product;
import com.bleizing.pos.model.Role;
import com.bleizing.pos.model.Store;
import com.bleizing.pos.model.SysParam;
import com.bleizing.pos.model.User;
import com.bleizing.pos.model.UserRole;
import com.bleizing.pos.model.UserStore;
import com.bleizing.pos.repository.MenuRepository;
import com.bleizing.pos.repository.MenuRolePermissionRepository;
import com.bleizing.pos.repository.PermissionRepository;
import com.bleizing.pos.repository.ProductRepository;
import com.bleizing.pos.repository.RoleRepository;
import com.bleizing.pos.repository.StoreRepository;
import com.bleizing.pos.repository.SysParamRepository;
import com.bleizing.pos.repository.UserRepository;
import com.bleizing.pos.repository.UserRoleRepository;
import com.bleizing.pos.repository.UserStoreRepository;
import com.bleizing.pos.util.PasswordUtil;

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
	
	@Autowired
	private SysParamRepository sysParamRepository;
	
	@Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Startup Listener");
		initData();
    }
	
	private void initData() {
		try {
			sysParamRepository.save(SysParam.builder()
					.name("Auth Required")
					.code("AUTH_REQUIRED")
					.description("Auth Required for Each API")
					.value("true")
					.build());
			
			sysParamRepository.save(SysParam.builder()
					.name("Access Control Required")
					.code("ACCESS_CONTROL_REQUIRED")
					.description("Access Control Required for Each API")
					.value("true")
					.build());
			
			User user;
			user = User.builder()
					.name("superadmin")
					.email("superadmin@tes.com")
					.password(PasswordUtil.createHash("superadmin"))
					.build();
			
			user.setCreatedBy(1111L);
			userRepository.save(user);
			
			User user1 = User.builder()
					.name("manager")
					.email("manager@tes.com")
					.password(PasswordUtil.createHash("manager"))
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
			
			Permission permission = Permission.builder().name(PermissionContstant.CREATE.toString()).build();
			permissionRepository.save(permission);
			
			Permission permission1 = Permission.builder().name(PermissionContstant.READ.toString()).build();
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
			
			Menu menu2 = Menu.builder()
					.name("Store")
					.code("getStore")
					.path("/store/get")
					.build();
			menuRepository.save(menu2);
			
			Menu menu3 = Menu.builder()
					.name("Init Data System")
					.code("sysInitData")
					.path("/sys/initData")
					.build();
			menuRepository.save(menu3);
			
			Menu menu4 = Menu.builder()
					.name("Create Store")
					.code("createStore")
					.path("/store/create")
					.build();
			menuRepository.save(menu4);
			
			userStoreRepository.save(UserStore.builder()
					.store(store1)
					.user(user1)
					.build());
			
			menuRolePermissionRepository.save(MenuRolePermission.builder()
					.menu(menu2)
					.role(role1)
					.permission(permission1)
					.build());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
}
