package com.winsafe.service;

import java.util.List;

import com.winsafe.model.User;

public interface UserService {
	public List<User> selectUserList(User record);
	public int insertUser(User record);
	public int updateByPrimaryKeySelective(User record);
	public int deleteByPrimaryKey(Integer id);
	public User selectByPrimaryKey(Integer id);
	public User selectByLoginName(User record);
	public void deleteUser(Integer id);
}
