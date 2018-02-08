package com.winsafe.service;

import java.util.List;

import com.winsafe.model.Permission;

public interface PermissionService {
	public List<Permission> selectPermissionList(Permission record);
	public int insertPermission(Permission record);
	public int updateByPrimaryKeySelective(Permission record);
	public int deleteByPrimaryKey(Integer id);
	public Permission selectByPrimaryKey(Integer id);
	public List<Permission> selectPermissionListByUserId(Integer id);
	public List<Permission> selectPermissionListByRoleId(Permission record);
	public List<Permission> selectUnselectedPermissionListByRoleId(Permission record);
}
