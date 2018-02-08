package com.winsafe.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winsafe.model.Permission;
import com.winsafe.service.PermissionService;
import com.winsafe.utils.AjaxUtil;
import com.winsafe.utils.BaseResult;
import com.winsafe.utils.DatatablePage;
import com.winsafe.utils.DatatablePageHelper;
import com.winsafe.utils.DatatableViewPage;
import com.winsafe.utils.MyShiroRealmHelper;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/permission")
public class PermissionController {
	
	@Resource
	private PermissionService permissionService; 
	
	@RequestMapping("/listPermission")
	public String toListPermission(Map<String, Object> model) {
		return "permission/listPermission";
	}
	
	@RequestMapping(value="/ajaxGetPermission")
	public void ajaxGetPermission(Permission record, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPage(request);
		record.setLevel(0);
		record.setSearchVal(dPage.getSearchValue());
		List<Permission> list = permissionService.selectPermissionList(record);
		JSONObject jsonObject = JSONObject.fromObject(new DatatableViewPage(true, "数据查询成功！", dPage));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/addPermission")
	public String toAddPermission() {
		return "permission/addPermission";
	}
	
	@RequestMapping("/ajaxAddPermission")
	public void ajaxAddPermission(Permission record, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Permission p = new Permission();
		p.setName(record.getName());
		List<Permission> list = permissionService.selectPermissionList(p);
		if(list != null && list.size()>0){
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "权限名已存在！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
		}
		
		record.setLevel(0);
		record.setDeletable(1);
		permissionService.insertPermission(record);
		
		MyShiroRealmHelper helper = new MyShiroRealmHelper();
		helper.clearCachedAuthorization();
		
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "添加成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/updPermission")
	public String toUpdPermission(Integer id, HttpServletRequest request, HttpServletResponse response) {
		Permission permission = permissionService.selectByPrimaryKey(id);
		request.setAttribute("permission", permission);
		return "permission/updPermission";
	}
	
	@RequestMapping("/ajaxUpdPermission")
	public void ajaxUpdPermission(Permission record, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Permission> list = permissionService.selectPermissionList(record);
		if(list != null && list.size() > 0 && !list.get(0).getId().equals(record.getId())){
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "表名已存在！", null));
			AjaxUtil.ajaxReturn(jsonObject, response);
			return;
		}
		
		permissionService.updateByPrimaryKeySelective(record);
		
		MyShiroRealmHelper helper = new MyShiroRealmHelper();
		helper.clearCachedAuthorization();
		
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "更新成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
	
	@RequestMapping("/ajaxDelPermission")
	public void ajaxDelPermission(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		permissionService.deleteByPrimaryKey(id);
		
		MyShiroRealmHelper helper = new MyShiroRealmHelper();
		helper.clearCachedAuthorization();
		
		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "删除成功！", null));
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
}
