package com.winsafe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.winsafe.model.UserRole;

@Mapper
public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
    
    List<UserRole> selectUserRoleList(UserRole record);
    
    List<UserRole> selectUserRoleFetchUserAndRole(UserRole record);
    
    public int insertUserRoleList(List<UserRole> records);
    
    public int deleteUserRoleList(List<UserRole> records);
    
    int deleteByUserId(Integer userId);
    
    int deleteByRoleId(Integer roleId);
}