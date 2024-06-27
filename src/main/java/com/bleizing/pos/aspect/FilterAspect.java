package com.bleizing.pos.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bleizing.pos.constant.PermissionContstant;
import com.bleizing.pos.error.ErrorList;
import com.bleizing.pos.error.ForbiddenAccessException;
import com.bleizing.pos.error.PathInvalidException;
import com.bleizing.pos.error.TokenInvalidException;
import com.bleizing.pos.error.TokenRequiredException;
import com.bleizing.pos.model.Menu;
import com.bleizing.pos.repository.MenuRepository;
import com.bleizing.pos.repository.MenuRolePermissionRepository;
import com.bleizing.pos.repository.PermissionRepository;
import com.bleizing.pos.repository.UserRoleRepository;
import com.bleizing.pos.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class FilterAspect  {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private MenuRolePermissionRepository menuRolePermissionRepository;
	
	@Pointcut("execution(public * com.bleizing.pos.controller..*(..))")
    public void allControllerMethods() {
        
    }
    
    @Around("allControllerMethods()")
    public Object checkAccess(ProceedingJoinPoint pjp) throws Exception {
    	boolean needAuth = false;
    	boolean needAccessControl = false;
        Object retObject = null;
        Method method = getMethod(pjp);
        
        for(Annotation anno : method.getAnnotations()){
            if (anno.annotationType().toString().contains("pos.annotation.Authenticated")) {
            	needAuth = true;
            }
            
            if (anno.annotationType().toString().contains("pos.annotation.AccessControl")) {
            	needAccessControl = true;
            }
        }
        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        if (needAuth) {
        	String token = authProcess(request.getHeader("Authorization"));
    		
    		Long userId = Long.valueOf(jwtService.extractClaim(token, "id").toString());
    		
    		if (needAccessControl) {
    			String permission = convertReqMethod(request.getMethod());
				
        		String[] paths = request.getServletPath().split("/");
        		String path = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
        		
        		if (!hasAccessControl(userId, permission, path)) {
        			throw new ForbiddenAccessException(ErrorList.FORBIDDEN_ACCESS.getDescription());
        		}
    		}
    		
    		request.setAttribute("userId", userId);
        }
        
        try {            
            retObject = pjp.proceed();
        } catch (Throwable e) {
        	throw new Exception(e.getMessage());
        }
        
        return retObject;
    }
    
    private String authProcess(String bearer) throws Exception {
    	if (bearer == null || bearer.isBlank()) {
    		throw new TokenRequiredException(ErrorList.TOKEN_REQUIRED.getDescription());
    	}
    	String token = bearer.substring(7);
		
		if (!jwtService.isTokenValid(token)) {
			throw new TokenInvalidException(ErrorList.TOKEN_INVALID.getDescription());
		}
		return token;
    }

    private Method getMethod(ProceedingJoinPoint pjp) throws Exception {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
        	method = pjp.getTarget().getClass().getDeclaredMethod(pjp.getSignature().getName(), method.getParameterTypes());
        }
        
        return method;
    }
    
	private String convertReqMethod(String reqMethod) {
		String permission = PermissionContstant.DELETE.toString();
		
		if (reqMethod.equals(RequestMethod.POST.toString())) {
			permission = PermissionContstant.CREATE.toString();
		} else if (reqMethod.equals(RequestMethod.GET.toString())) {
			permission = PermissionContstant.READ.toString();
		} else if (reqMethod.equals(RequestMethod.PUT.toString())) {
			permission = PermissionContstant.UPDATE.toString();
		}
		
		return permission;
	}
	
	private boolean hasAccessControl(Long userId, String permission, String path) throws Exception {
		menuRolePermissionRepository.findByRoleIdAndPermissionIdAndMenuIdAndActiveTrue(getRoleId(userId), getPermissionId(permission), getMenuId(path)).orElseThrow(() -> new Exception("No Access"));
		
		return true;
	}
	
	private Long getMenuId(String path) throws Exception {
		List<Menu> menus = menuRepository.findByPathAndActiveTrue(path).orElseThrow(() -> new Exception("Path Invalid"));
		if (menus.isEmpty()) {
			throw new PathInvalidException(ErrorList.PATH_INVALID.getDescription());
		}
		return menus.get(0).getId();
	}
	
	private Long getPermissionId(String name) throws Exception {
		return permissionRepository.findByNameAndActiveTrue(name).orElseThrow(() -> new Exception("Permission Invalid")).getId();
	}
	
	private Long getRoleId(Long userId) throws Exception {
		return userRoleRepository.findByUserIdAndActiveTrue(userId).orElseThrow(() -> new Exception("Role Invalid")).getRole().getId();
	}
}
