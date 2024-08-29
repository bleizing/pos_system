package com.bleizing.pos.listener;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bleizing.pos.constant.MenuConstant;
import com.bleizing.pos.constant.PermissionContstant;
import com.bleizing.pos.constant.RoleConstant;
import com.bleizing.pos.constant.SysParamConstant;
import com.bleizing.pos.constant.VariableConstant;
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
import com.bleizing.pos.util.RedisUtil;

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
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Startup Listener");
		initData();
    }
	
	private void initData() {
		try {
			SysParam sysParam = SysParam.builder()
					.name("Auth Required")
					.code(SysParamConstant.AUTH_REQUIRED.toString())
					.description("Auth Required for Each API")
					.value("true")
					.build();
			sysParamRepository.save(sysParam);
			redisUtil.setOps(VariableConstant.SYS_PARAM.getValue(), SysParamConstant.AUTH_REQUIRED.toString(), sysParam);
			
			SysParam sysParam2 = SysParam.builder()
					.name("Access Control Required")
					.code(SysParamConstant.ACCESS_CONTROL_REQUIRED.toString())
					.description("Access Control Required for Each API")
					.value("true")
					.build();
			sysParamRepository.save(sysParam2);
			redisUtil.setOps(VariableConstant.SYS_PARAM.getValue(), SysParamConstant.ACCESS_CONTROL_REQUIRED.toString(), sysParam2);
			
			User user;
			user = User.builder()
					.name("superadmin")
					.email("superadmin@tes.com")
					.password(PasswordUtil.createHash("superadmin"))
					.build();
			
			user.setCreatedBy(1111L);
			userRepository.saveAndFlush(user);
			
			User user1 = User.builder()
					.name("manager")
					.email("manager@tes.com")
					.password(PasswordUtil.createHash("manager"))
					.build();
			userRepository.saveAndFlush(user1);
			
			Role role = Role.builder().name(RoleConstant.SUPERADMIN.toString()).build();
			roleRepository.save(role);
			
			UserRole userRole = UserRole.builder()
					.role(role)
					.user(user)
					.build();
			userRoleRepository.saveAndFlush(userRole);
			redisUtil.setOps(VariableConstant.USER_ROLE.getValue(), String.valueOf(user.getId()), role);
			
			Role role1 = Role.builder().name(RoleConstant.MANAGER.toString()).build();
			roleRepository.save(role1);
			
			UserRole userRole1 = UserRole.builder()
					.role(role1)
					.user(user1)
					.build();
			userRoleRepository.saveAndFlush(userRole1);
			redisUtil.setOps(VariableConstant.USER_ROLE.getValue(), String.valueOf(user1.getId()), role1);
			
			Permission permission = Permission.builder().name(PermissionContstant.CREATE.toString()).build();
			permissionRepository.saveAndFlush(permission);
			redisUtil.setOps(VariableConstant.PERMISSION_ID.getValue(), permission.getName(), permission.getId());
			
			Permission permission1 = Permission.builder().name(PermissionContstant.READ.toString()).build();
			permissionRepository.saveAndFlush(permission1);
			redisUtil.setOps(VariableConstant.PERMISSION_ID.getValue(), permission1.getName(), permission1.getId());
			
			Permission permission2 = Permission.builder().name(PermissionContstant.UPDATE.toString()).build();
			permissionRepository.saveAndFlush(permission2);
			redisUtil.setOps(VariableConstant.PERMISSION_ID.getValue(), permission2.getName(), permission2.getId());
			
			Permission permission3 = Permission.builder().name(PermissionContstant.DELETE.toString()).build();
			permissionRepository.saveAndFlush(permission3);
			redisUtil.setOps(VariableConstant.PERMISSION_ID.getValue(), permission3.getName(), permission3.getId());
			
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
					.name(MenuConstant.SYSTEM.getName())
					.code(MenuConstant.SYSTEM.getCode())
					.path(MenuConstant.SYSTEM.getPath())
					.build();
			menuRepository.saveAndFlush(menu);
			redisUtil.setOps(VariableConstant.MENU_ID.getValue(), menu.getPath(), menu.getId());
			
			Menu menu1 = Menu.builder()
					.name(MenuConstant.STORE.getName())
					.code(MenuConstant.STORE.getCode())
					.path(MenuConstant.STORE.getPath())
					.build();
			menuRepository.saveAndFlush(menu1);
			redisUtil.setOps(VariableConstant.MENU_ID.getValue(), menu1.getPath(), menu1.getId());
			
			Menu menu2 = Menu.builder()
					.name(MenuConstant.PRODUCT.getName())
					.code(MenuConstant.PRODUCT.getCode())
					.path(MenuConstant.PRODUCT.getPath())
					.build();
			menuRepository.saveAndFlush(menu2);
			redisUtil.setOps(VariableConstant.MENU_ID.getValue(), menu2.getPath(), menu2.getId());
			
			Menu menu3 = Menu.builder()
					.name(MenuConstant.STORAGE.getName())
					.code(MenuConstant.STORAGE.getCode())
					.path(MenuConstant.STORAGE.getPath())
					.build();
			menuRepository.saveAndFlush(menu3);
			redisUtil.setOps(VariableConstant.MENU_ID.getValue(), menu3.getPath(), menu3.getId());
			
			userStoreRepository.save(UserStore.builder()
					.store(store1)
					.user(user1)
					.build());
			
			MenuRolePermission menuRolePermission = MenuRolePermission.builder()
					.menu(menu1)
					.role(role1)
					.permission(permission1)
					.build();
			menuRolePermissionRepository.saveAndFlush(menuRolePermission);
			redisUtil.setOps(VariableConstant.MENU_ROLE_PERMISSION.getValue(), 
					role1.getId() + ":" + permission1.getId() + ":" + menu1.getId(), 
					true);
			MenuRolePermission menuRolePermission1 = MenuRolePermission.builder()
					.menu(menu1)
					.role(role1)
					.permission(permission2)
					.build();
			menuRolePermissionRepository.saveAndFlush(menuRolePermission1);
			redisUtil.setOps(VariableConstant.MENU_ROLE_PERMISSION.getValue(), 
					role1.getId() + ":" + permission2.getId() + ":" + menu1.getId(), 
					true);
			
			MenuRolePermission menuRolePermission2 = MenuRolePermission.builder()
					.menu(menu2)
					.role(role1)
					.permission(permission)
					.build();
			menuRolePermissionRepository.saveAndFlush(menuRolePermission2);
			redisUtil.setOps(VariableConstant.MENU_ROLE_PERMISSION.getValue(), 
					role1.getId() + ":" + permission.getId() + ":" + menu2.getId(), 
					true);
			MenuRolePermission menuRolePermission3 = MenuRolePermission.builder()
					.menu(menu2)
					.role(role1)
					.permission(permission1)
					.build();
			menuRolePermissionRepository.saveAndFlush(menuRolePermission3);
			redisUtil.setOps(VariableConstant.MENU_ROLE_PERMISSION.getValue(), 
					role1.getId() + ":" + permission1.getId() + ":" + menu2.getId(), 
					true);
			MenuRolePermission menuRolePermission4 = MenuRolePermission.builder()
					.menu(menu2)
					.role(role1)
					.permission(permission2)
					.build();
			menuRolePermissionRepository.saveAndFlush(menuRolePermission4);
			redisUtil.setOps(VariableConstant.MENU_ROLE_PERMISSION.getValue(), 
					role1.getId() + ":" + permission2.getId() + ":" + menu2.getId(), 
					true);
			MenuRolePermission menuRolePermission5 = MenuRolePermission.builder()
					.menu(menu2)
					.role(role1)
					.permission(permission3)
					.build();
			menuRolePermissionRepository.saveAndFlush(menuRolePermission5);
			redisUtil.setOps(VariableConstant.MENU_ROLE_PERMISSION.getValue(), 
					role1.getId() + ":" + permission3.getId() + ":" + menu2.getId(), 
					true);
			
			MenuRolePermission menuRolePermission6 = MenuRolePermission.builder()
					.menu(menu3)
					.role(role1)
					.permission(permission1)
					.build();
			menuRolePermissionRepository.saveAndFlush(menuRolePermission6);
			redisUtil.setOps(VariableConstant.MENU_ROLE_PERMISSION.getValue(), 
					role1.getId() + ":" + permission1.getId() + ":" + menu3.getId(), 
					true);
			MenuRolePermission menuRolePermission7 = MenuRolePermission.builder()
					.menu(menu3)
					.role(role1)
					.permission(permission3)
					.build();
			menuRolePermissionRepository.saveAndFlush(menuRolePermission7);
			redisUtil.setOps(VariableConstant.MENU_ROLE_PERMISSION.getValue(), 
					role1.getId() + ":" + permission3.getId() + ":" + menu3.getId(), 
					true);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
}
