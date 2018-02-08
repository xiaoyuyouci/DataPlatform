package com.winsafe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.winsafe.model.Permission;

@Mapper
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
    
    List<Permission> selectPermissionList(Permission record);
    
    public List<Permission> selectPermissionListByRoleId(Permission record);
    
    public List<Permission> selectPermissionListByUserId(Integer id);
    
    public List<Permission> selectUnselectedPermissionListByRoleId(Permission record);
}