package com.winsafe.service;

import java.util.List;

import com.winsafe.model.UserRole;

public interface UserRoleService {
	public List<UserRole> selectUserRoleList(UserRole record);
	public int insertUserRole(UserRole record);
	public int updateByPrimaryKeySelective(UserRole record);
	public int deleteByPrimaryKey(Integer id);
	public UserRole selectByPrimaryKey(Integer id);
	List<UserRole> selectUserRoleFetchUserAndRole(UserRole record);
	
	public int insertUserRoleList(List<UserRole> records);
	public int deleteUserRoleList(List<UserRole> records);
}
