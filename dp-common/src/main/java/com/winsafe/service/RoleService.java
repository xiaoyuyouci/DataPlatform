package com.winsafe.service;

import java.util.List;
import java.util.Map;

import com.winsafe.model.Role;


public interface RoleService {
	public List<Role> selectRoleList(Role record);
	public List<Role> selectRoleListByUserId(Map<String, Object> map);
	public List<Role> selectUnselectedRoleListByUserId(Map<String, Object> map);
	public int insertRole(Role record);
	public int updateByPrimaryKeySelective(Role record);
	public int deleteByPrimaryKey(Integer id);
	public Role selectByPrimaryKey(Integer id);
	public void deleteRole(Integer id);
}
