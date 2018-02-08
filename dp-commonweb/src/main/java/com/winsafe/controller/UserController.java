package com.winsafe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winsafe.model.Role;
import com.winsafe.model.RolePermission;
import com.winsafe.model.User;
import com.winsafe.model.UserRole;
import com.winsafe.service.RoleService;
import com.winsafe.service.UserRoleService;
import com.winsafe.service.UserService;
import com.winsafe.utils.AjaxUtil;
import com.winsafe.utils.BaseResult;
import com.winsafe.utils.DatatablePage;
import com.winsafe.utils.DatatablePageHelper;
import com.winsafe.utils.DatatableViewPage;
import com.winsafe.utils.MyShiroRealmHelper;
import com.winsafe.utils.PasswordHelper;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService; 
	
	@Resource
	private RoleService roleService; 
	
	@Resource
	private UserRoleService userRoleService; 
	
	@RequestMapping("/listUser")
	//@RequiresPermissions("user:list")
	public String toListUser(Map<String, Object> model) {
		return "user/listUser";
	}
	
	@RequestMapping(value="/ajaxGetUser")
	public void ajaxGetUser(User record, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPage(request);
		record.setSearchVal(dPage.getSearchValue());
		List<User> list = userService.selectUserList(record);
		JSONObject jsonObject = JSONObject.fromObject(new DatatableViewPage(true, "数据查询成功！", dPage));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/addUser")
	public String toAddUser() {
		return "user/addUser";
	}
	
	@RequestMapping("/ajaxAddUser")
	public void ajaxAddUser(User record, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user = userService.selectByLoginName(record);
		if(user != null){
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "用户名已存在！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
		}
		
		PasswordHelper passwordHelper = new PasswordHelper();
		record.setPassword(passwordHelper.encryptPassword(record.getLoginName(), record.getPassword()));
		userService.insertUser(record);
		
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "添加成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/updUser")
	public String toUpdUser(Integer id, HttpServletRequest request, HttpServletResponse response) {
		User user = userService.selectByPrimaryKey(id);
		request.setAttribute("user", user);
		return "user/updUser";
	}
	
	@RequestMapping("/ajaxUpdUser")
	public void ajaxUpdUser(User record, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<User> list = userService.selectUserList(record);
		if(list != null && list.size() > 0 && !list.get(0).getId().equals(record.getId())){
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "表名已存在！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
		}
		
		userService.updateByPrimaryKeySelective(record);
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "更新成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/ajaxDelUser")
	public void ajaxDelUser(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User record = new User();
		record.setId(id);
		record = userService.selectByPrimaryKey(id);
		
		if(record.getDeletable() == null || record.getDeletable() == 0){
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "该用户无法删除！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
		}
		
		userService.deleteUser(id);
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "删除成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/listUserRole")
	public String listUserRole(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("userId", request.getParameter("userId"));
		return "user/listUserRole";
	}
	
	@RequestMapping("/listUnselectedUserRole")
	public String listUnselectedUserRole(HttpServletRequest request) {
		request.setAttribute("userId", request.getParameter("userId"));
		return "user/listUnselectedUserRole";
	}
	
	@RequestMapping(value="/ajaxGetUserRole")
	public void ajaxGetUserRole(Integer userId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPage(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("searchVal", dPage.getSearchValue());
		List<Role> list = roleService.selectRoleListByUserId(map);
		
		JSONObject jsonObject = JSONObject.fromObject(new DatatableViewPage(true, "数据查询成功！", dPage));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping(value="/ajaxGetUnselectedUserRole")
	public void ajaxGetUnselectedUserRole(Integer userId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPage(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("searchVal", dPage.getSearchValue());
		List<Role> list = roleService.selectUnselectedRoleListByUserId(map);
		
		JSONObject jsonObject = JSONObject.fromObject(new DatatableViewPage(true, "数据查询成功！", dPage));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping(value="/ajaxDelUserRole")
	public void ajaxDelUserRole(Integer userId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		//TODO
		String[] roleIds = request.getParameterValues("roleIds[]");
		List<UserRole> list = new ArrayList<UserRole>();
		UserRole rp = null;
		for(String roleId: roleIds){
			rp = new UserRole();
			rp.setDeletable(1);
			rp.setUserId(userId);
			rp.setRoleId(Integer.valueOf(roleId));
			list.add(rp);
			rp = null;
		}
		userRoleService.deleteUserRoleList(list);
		
		MyShiroRealmHelper helper = new MyShiroRealmHelper();
		helper.clearCachedAuthorization();
		
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "删除成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
		return;
	}
	@RequestMapping(value="/ajaxAddUserRole")
	public void ajaxAddUserRole(Integer userId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String[] roleIds = request.getParameterValues("roleIds[]");
		List<UserRole> list = new ArrayList<UserRole>();
		UserRole rp = null;
		for(String roleId: roleIds){
			rp = new UserRole();
			rp.setDeletable(1);
			rp.setUserId(userId);
			rp.setRoleId(Integer.valueOf(roleId));
			list.add(rp);
			rp = null;
		}
		userRoleService.insertUserRoleList(list);
		
		MyShiroRealmHelper helper = new MyShiroRealmHelper();
		helper.clearCachedAuthorization();
		
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "添加成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
		return;
	}
}
