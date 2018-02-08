package com.winsafe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsafe.dao.RolePermissionMapper;
import com.winsafe.model.RolePermission;
import com.winsafe.service.RolePermissionService;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
	@Autowired
    private RolePermissionMapper rolePermissionMapper;
	
	//@Transactional
	public List<RolePermission> selectRolePermissionList(RolePermission record) {
		return rolePermissionMapper.selectRolePermissionList(record);
	}
	
	@Transactional
	public int updateByPrimaryKeySelective(RolePermission record) {
		return rolePermissionMapper.updateByPrimaryKeySelective(record);
	}
	
	@Transactional
	public int insertRolePermission(RolePermission record) {
		return rolePermissionMapper.insert(record);
	}
	
	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		return rolePermissionMapper.deleteByPrimaryKey(id);
	}
	
	public RolePermission selectByPrimaryKey(Integer id) {
		return rolePermissionMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public int insertRolePermissionList(List<RolePermission> records) {
		return rolePermissionMapper.insertRolePermissionList(records);
	}

	@Transactional
	public int deleteRolePermissionList(List<RolePermission> records) {
		return rolePermissionMapper.deleteRolePermissionList(records);
	}
}
