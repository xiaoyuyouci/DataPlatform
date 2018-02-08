package com.winsafe.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsafe.dao.UserMapper;
import com.winsafe.dao.UserRoleMapper;
import com.winsafe.model.User;
import com.winsafe.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
    private UserMapper userMapper;
	
	@Autowired
    private UserRoleMapper userRoleMapper;
	
	//@Transactional
	public List<User> selectUserList(User record) {
		return userMapper.selectUserList(record);
	}
	
	public User selectByLoginName(User record) {
		return userMapper.selectByLoginName(record);
	}
	
	@Transactional
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}
	
	@Transactional
	public int insertUser(User record) {
		record.setCreationTime(new Date());
		record.setDeletable(record.getDeletable() == null? 1: record.getDeletable());
		record.setGender(record.getGender() == null? 0: record.getGender());
		record.setLoginTimes(record.getLoginTimes() == null? 0: record.getLoginTimes());
		record.setStatus(record.getStatus() == null? 0: record.getStatus());
		return userMapper.insert(record);
	}
	
	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}
	
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public void deleteUser(Integer id) {
		userMapper.deleteByPrimaryKey(id);
		userRoleMapper.deleteByUserId(id);
	}
}
