package com.winsafe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winsafe.model.Permission;
import com.winsafe.model.Role;
import com.winsafe.model.RolePermission;
import com.winsafe.service.PermissionService;
import com.winsafe.service.RolePermissionService;
import com.winsafe.service.RoleService;
import com.winsafe.utils.AjaxUtil;
import com.winsafe.utils.BaseResult;
import com.winsafe.utils.DatatablePage;
import com.winsafe.utils.DatatablePageHelper;
import com.winsafe.utils.DatatableViewPage;
import com.winsafe.utils.MyShiroRealmHelper;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Resource
	private RoleService roleService; 
	
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private RolePermissionService rolePermissionService;
	
	@RequestMapping("/listRole")
	public String toListRole(Map<String, Object> model) {
		return "role/listRole";
	}
	
	@RequestMapping(value="/ajaxGetRole")
	public void ajaxGetRole(Role record, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPage(request);
		record.setSearchVal(dPage.getSearchValue());
		List<Role> list = roleService.selectRoleList(record);
		JSONObject jsonObject = JSONObject.fromObject(new DatatableViewPage(true, "数据查询成功！", dPage));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/addRole")
	public String toAddRole() {
		return "role/addRole";
	}
	
	@RequestMapping("/ajaxAddRole")
	public void ajaxAddRole(Role record, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Role model = new Role();
		model.setName(record.getName());
		List<Role> list = roleService.selectRoleList(model);
		if(list != null && list.size()>0){
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "角色名已存在！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
		}
		
		roleService.insertRole(record);
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "添加成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/updRole")
	public String toUpdRole(Integer id, HttpServletRequest request, HttpServletResponse response) {
		Role role = roleService.selectByPrimaryKey(id);
		request.setAttribute("role", role);
		return "role/updRole";
	}
	
	@RequestMapping("/ajaxUpdRole")
	public void ajaxUpdRole(Role record, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Role> list = roleService.selectRoleList(record);
		if(list != null && list.size() > 0 && !list.get(0).getId().equals(record.getId())){
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "表名已存在！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
		}
		
		roleService.updateByPrimaryKeySelective(record);
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "更新成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/ajaxDelRole")
	public void ajaxDelRole(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		roleService.deleteRole(id);
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "删除成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/listRolePermission")
	public String listRolePermission(HttpServletRequest request) {
		request.setAttribute("roleId", request.getParameter("roleId"));
		return "role/listRolePermission";
	}
	
	@RequestMapping("/listUnselectedRolePermission")
	public String listUnselectedRolePermission(HttpServletRequest request) {
		request.setAttribute("roleId", request.getParameter("roleId"));
		return "role/listUnselectedRolePermission";
	}
	
	@RequestMapping(value="/ajaxGetRolePermission")
	public void ajaxGetRolePermission(Integer roleId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPage(request);

		Permission record = new Permission();
		record.setRoleId(roleId);
		record.setSearchVal(dPage.getSearchValue());
		List<Permission> list = permissionService.selectPermissionListByRoleId(record);
		JSONObject jsonObject = JSONObject.fromObject(new DatatableViewPage(true, "数据查询成功！", dPage));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping(value="/ajaxGetUnselectedRolePermission")
	public void ajaxGetUnselectedRolePermission(Integer roleId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPage(request);

		Permission record = new Permission();
		record.setRoleId(roleId);
		record.setSearchVal(dPage.getSearchValue());
		List<Permission> list = permissionService.selectUnselectedPermissionListByRoleId(record);
		JSONObject jsonObject = JSONObject.fromObject(new DatatableViewPage(true, "数据查询成功！", dPage));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping(value="/ajaxDelRolePermission")
	public void ajaxDelRolePermission(Integer roleId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String[] permissionIds = request.getParameterValues("permissionIds[]");
		List<RolePermission> list = new ArrayList<RolePermission>();
		RolePermission rp = null;
		for(String permissionId: permissionIds){
			rp = new RolePermission();
			rp.setDeletable(1);
			rp.setPermissionId(Integer.valueOf(permissionId));
			rp.setRoleId(roleId);
			list.add(rp);
			rp = null;
		}
		rolePermissionService.deleteRolePermissionList(list);
		
		MyShiroRealmHelper helper = new MyShiroRealmHelper();
		helper.clearCachedAuthorization();
		
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "删除成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
		return;
	}
	@RequestMapping(value="/ajaxAddRolePermission")
	public void ajaxAddRolePermission(Integer roleId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String[] permissionIds = request.getParameterValues("permissionIds[]");
		List<RolePermission> list = new ArrayList<RolePermission>();
		RolePermission rp = null;
		for(String permissionId: permissionIds){
			rp = new RolePermission();
			rp.setDeletable(1);
			rp.setPermissionId(Integer.valueOf(permissionId));
			rp.setRoleId(roleId);
			list.add(rp);
			rp = null;
		}
		rolePermissionService.insertRolePermissionList(list);
		
		MyShiroRealmHelper helper = new MyShiroRealmHelper();
		helper.clearCachedAuthorization();
		
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "添加成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
		return;
	}
}
