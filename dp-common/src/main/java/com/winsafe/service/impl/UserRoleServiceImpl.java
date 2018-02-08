package com.winsafe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsafe.dao.UserRoleMapper;
import com.winsafe.model.UserRole;
import com.winsafe.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
    private UserRoleMapper userRoleMapper;
	
	//@Transactional
	public List<UserRole> selectUserRoleList(UserRole record) {
		return userRoleMapper.selectUserRoleList(record);
	}
	
	@Transactional
	public int updateByPrimaryKeySelective(UserRole record) {
		return userRoleMapper.updateByPrimaryKeySelective(record);
	}
	
	@Transactional
	public int insertUserRole(UserRole record) {
		return userRoleMapper.insert(record);
	}
	
	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		return userRoleMapper.deleteByPrimaryKey(id);
	}
	
	public UserRole selectByPrimaryKey(Integer id) {
		return userRoleMapper.selectByPrimaryKey(id);
	}

	public List<UserRole> selectUserRoleFetchUserAndRole(UserRole record) {
		return userRoleMapper.selectUserRoleFetchUserAndRole(record);
	}

	@Transactional
	public int insertUserRoleList(List<UserRole> records) {
		return userRoleMapper.insertUserRoleList(records);
	}

	@Transactional
	public int deleteUserRoleList(List<UserRole> records) {
		return userRoleMapper.deleteUserRoleList(records);
	}
}
