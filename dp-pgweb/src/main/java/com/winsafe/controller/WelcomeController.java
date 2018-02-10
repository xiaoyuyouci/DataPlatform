package com.winsafe.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.model.User;
import com.winsafe.service.UserService;
import com.winsafe.utils.AjaxUtil;
import com.winsafe.utils.BaseResult;
import com.winsafe.utils.PasswordHelper;

import net.sf.json.JSONObject;

@Controller
public class WelcomeController {
	
	@Resource
	private UserService userService; 
	
	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		Integer userId = (Integer) SecurityUtils.getSubject().getPrincipal();
		User user = userService.selectByPrimaryKey(userId);
		request.setAttribute("loginName", user.getLoginName());
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

	@RequestMapping("/toLogin")
	public String toLogin(Map<String, Object> model) {
		return "login";
	}
	
	@RequestMapping("/403")
	public String page403(Map<String, Object> model) {
		return "403";
	}
	
	@RequestMapping(value="/login")
    public void login(HttpServletRequest request, HttpServletResponse response, User user, Model model) throws Exception{
        if (StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getPassword())) {
            JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "用户名或密码不能为空！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
        }
        Subject subject = SecurityUtils.getSubject();
        PasswordHelper passwordHelper = new PasswordHelper();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), passwordHelper.encryptPassword(user.getLoginName(), user.getPassword()));
        try {
            subject.login(token);
            JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "登录成功！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
        }catch (LockedAccountException lae) {
            token.clear();
            JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "用户已经被锁定不能登录，请与管理员联系！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
        } catch (DisabledAccountException e) {
            token.clear();
            JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "此帐号已经设置为禁止登录！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
        } catch(AccountException e){
        	token.clear();
            JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "账号或密码不正确！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
        }
    }
	
	@RequestMapping("/logout")
	public String tologout(Map<String, Object> model) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/toLogin";
	}
}
