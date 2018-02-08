package com.winsafe.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsafe.dao.PermissionMapper;
import com.winsafe.dao.RolePermissionMapper;
import com.winsafe.model.Permission;
import com.winsafe.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
    private PermissionMapper permissionMapper;
	
	@Autowired
    private RolePermissionMapper rolePermissionMapper;
	
	//@Transactional
	public List<Permission> selectPermissionList(Permission record) {
		return permissionMapper.selectPermissionList(record);
	}
	
	@Transactional
	public int updateByPrimaryKeySelective(Permission record) {
		record.setModificationTime(new Date());
		return permissionMapper.updateByPrimaryKeySelective(record);
	}
	
	@Transactional
	public int insertPermission(Permission record) {
		record.setCreationTime(new Date());
		record.setLevel(record.getLevel() == null? 0: record.getLevel());
		record.setDeletable(record.getDeletable() == null? 1: record.getDeletable());
		return permissionMapper.insert(record);
	}
	
	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		rolePermissionMapper.deleteByPermissionId(id);
		permissionMapper.deleteByPrimaryKey(id);
		return 1;
	}
	
	public Permission selectByPrimaryKey(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}
	
	public List<Permission> selectPermissionListByUserId(Integer id){
		return permissionMapper.selectPermissionListByUserId(id);
	}

	public List<Permission> selectPermissionListByRoleId(Permission record) {
		return permissionMapper.selectPermissionListByRoleId(record);
	}

	public List<Permission> selectUnselectedPermissionListByRoleId(Permission record) {
		return permissionMapper.selectUnselectedPermissionListByRoleId(record);
	}
}
