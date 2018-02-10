package com.winsafe.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.winsafe.model.Permission;
import com.winsafe.service.PermissionService;
import com.winsafe.shiro.KickoutSessionControlFilter;
import com.winsafe.shiro.MyShiroRealm;

@Configuration
public class ShiroConfig {

	@Autowired(required = false)
	PermissionService permissionService;
	
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;
    
    @Value("${spring.redis.password}")
    private String password;
    
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 权限控制map.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //从数据库获取
        Permission record = new Permission();
        List<Permission> list = permissionService.selectPermissionList(record);

        for (Permission sysPermissionInit : list) {
        	if(sysPermissionInit.getUrl() != null && !"".equals(sysPermissionInit.getUrl().trim())){
        		filterChainDefinitionMap.put(sysPermissionInit.getUrl(), sysPermissionInit.getFilter());
        	}
        }

        filterChainDefinitionMap.put("/**", "authc");
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
    
    @Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(myShiroRealm());
		
		// 自定义缓存实现 使用redis
		securityManager.setCacheManager(cacheManager());
		
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		
		//注入记住我管理器;
	    securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}
    
    /**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 * 
	 * @return
	 */
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		return myShiroRealm;
	}

	
	/**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
	@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
	
	/**
	 * 配置shiro redisManager
	 * 使用的是shiro-redis开源插件
	 * @return
	 */
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setExpire(1800);// 配置缓存过期时间

		redisManager.setTimeout(timeout);
		redisManager.setPassword(password);

		return redisManager;
	}

	/**

	 * cacheManager 缓存 redis实现

	 * 使用的是shiro-redis开源插件

	 * @return

	 */
	public RedisCacheManager cacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	/**

	 * RedisSessionDAO shiro sessionDao层的实现 通过redis

	 * 使用的是shiro-redis开源插件

	 */
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}

	/**

	 * Session Manager

	 * 使用的是shiro-redis开源插件

	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO());
		return sessionManager;
	}
	
	/**

     * cookie对象;

     * @return

     */
    public SimpleCookie rememberMeCookie(){
       //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe

       SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
       //<!-- 记住我cookie生效时间30天 ,单位秒;-->

       simpleCookie.setMaxAge(2592000);
       return simpleCookie;
    }
    
    /**

     * cookie管理对象;记住我功能

     * @return

     */
    public CookieRememberMeManager rememberMeManager(){
       CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
       cookieRememberMeManager.setCookie(rememberMeCookie());
       //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)

       cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
       return cookieRememberMeManager;
    }
    
    /**
     * 限制同一账号登录同时登录人数控制
     * @return
     */
    public KickoutSessionControlFilter kickoutSessionControlFilter(){
    	KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
    	//使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；

    	//这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理

    	//也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性

    	kickoutSessionControlFilter.setCacheManager(cacheManager());
    	//用于根据会话ID，获取会话进行踢出操作的；

    	kickoutSessionControlFilter.setSessionManager(sessionManager());
    	//是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。

    	kickoutSessionControlFilter.setKickoutAfter(false);
    	//同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；

    	kickoutSessionControlFilter.setMaxSession(1);
    	//被踢出后重定向到的地址；

    	kickoutSessionControlFilter.setKickoutUrl("/kickout");
        return kickoutSessionControlFilter;
     }
}
