package com.winsafe.service;

import java.util.List;

import com.winsafe.model.RolePermission;

public interface RolePermissionService {
	public List<RolePermission> selectRolePermissionList(RolePermission record);
	public int insertRolePermission(RolePermission record);
	public int insertRolePermissionList(List<RolePermission> records);
	public int updateByPrimaryKeySelective(RolePermission record);
	public int deleteByPrimaryKey(Integer id);
	public RolePermission selectByPrimaryKey(Integer id);
	public int deleteRolePermissionList(List<RolePermission> records);
}
