package com.winsafe.shiro;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.winsafe.model.Permission;
import com.winsafe.model.Role;
import com.winsafe.model.User;
import com.winsafe.model.UserRole;
import com.winsafe.service.PermissionService;
import com.winsafe.service.RoleService;
import com.winsafe.service.UserRoleService;
import com.winsafe.service.UserService;

public class MyShiroRealm extends AuthorizingRealm{
	@Autowired
	private UserService userService;
	
	@Autowired
	private  PermissionService permissionService;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	//用户登录次数计数  redisKey 前缀
	private String SHIRO_LOGIN_COUNT = "shiro_login_count_";
	
	//用户登录是否被锁定    一小时 redisKey 前缀
	private String SHIRO_IS_LOCK = "shiro_is_lock_";

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String name = token.getUsername();
		String password = String.valueOf(token.getPassword());
		
		//访问一次，计数一次
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		opsForValue.increment(SHIRO_LOGIN_COUNT+name, 1);
		
		//计数大于5时，设置用户被锁定一小时
		if(Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+name))>=5){
			opsForValue.set(SHIRO_IS_LOCK+name, "LOCK");
			stringRedisTemplate.expire(SHIRO_IS_LOCK+name, 1, TimeUnit.HOURS);
		}
		if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+name))){
			throw new LockedAccountException("由于密码输入错误次数大于5次，帐号已经禁止登录！");
		}
		User user = new User();
		user.setLoginName(name);
		user.setPassword(password);
		//从数据库获取对应用户名密码的用户
		List<User> userList = userService.selectUserList(user);
		if(userList.size() == 0){
			throw new AccountException("帐号或密码不正确！");
		} 
		
		user = userList.get(0);
		if(user.getStatus() == null || user.getStatus() == 1){
			//如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
			throw new DisabledAccountException("此帐号已经设置为禁止登录！");
		}
		
		//登录成功

		//更新登录时间 last login time
		user.setLastLoginTime(new Date());
		userService.updateByPrimaryKeySelective(user);
			
		//清空登录计数
		opsForValue.set(SHIRO_LOGIN_COUNT+name, "0");
		
		Logger.getLogger(getClass()).info("身份认证成功，登录用户："+name);
		return new SimpleAuthenticationInfo(user.getId(), password, getName());
	}

	/**
	 * 授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Logger.getLogger(getClass()).info("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");
		Integer userId = (Integer) SecurityUtils.getSubject().getPrincipal();
		
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		
		//根据用户ID查询角色（role），放入到Authorization里。
		UserRole userRoleModel = new UserRole();
		userRoleModel.setUserId(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<Role> roleList = roleService.selectRoleListByUserId(map);
		Set<String> roleSet = new HashSet<String>();
		for(Role r : roleList){
			if(r.getName() != null && !"".equals(r.getName().trim()) ){
				roleSet.add(r.getName());
			}
		}
		info.addRoles(roleSet);
		
		//根据用户ID查询权限（permission），放入到Authorization里。
		List<Permission> permissionList = permissionService.selectPermissionListByUserId(userId);
		Set<String> permissionSet = new HashSet<String>();
		for(Permission Permission : permissionList){
			permissionSet.add(Permission.getName());
		}
		info.setStringPermissions(permissionSet);
        return info;
	}
	
    /** 
     * 清理权限缓存 
     */  
    public void clearCachedAuthorization(){  
        //清空权限缓存  
        clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());  
    }  
}
