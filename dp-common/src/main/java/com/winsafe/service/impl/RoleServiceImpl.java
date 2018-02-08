package com.winsafe.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsafe.dao.RoleMapper;
import com.winsafe.dao.RolePermissionMapper;
import com.winsafe.model.Role;
import com.winsafe.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
    private RoleMapper roleMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	//@Transactional
	public List<Role> selectRoleList(Role record) {
		return roleMapper.selectRoleList(record);
	}
	
	@Transactional
	public int updateByPrimaryKeySelective(Role record) {
		return roleMapper.updateByPrimaryKeySelective(record);
	}
	
	@Transactional
	public int insertRole(Role record) {
		record.setCreationTime(new Date());
		record.setDeletable(record.getDeletable() == null? 1: record.getDeletable());
		return roleMapper.insert(record);
	}
	
	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		return roleMapper.deleteByPrimaryKey(id);
	}
	
	public Role selectByPrimaryKey(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	public List<Role> selectRoleListByUserId(Map<String, Object> map) {
		return roleMapper.selectRoleListByUserId(map);
	}
	
	public List<Role> selectUnselectedRoleListByUserId(Map<String, Object> map) {
		return roleMapper.selectUnselectedRoleListByUserId(map);
	}

	public void deleteRole(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
		rolePermissionMapper.deleteByRoleId(id);
	}
}
