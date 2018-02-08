package com.winsafe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.winsafe.model.RolePermission;

@Mapper
public interface RolePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByPermissionId(Integer permissionId);
    
    int deleteByRoleId(Integer roleId);
    
    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);
    
    List<RolePermission> selectRolePermissionList(RolePermission record);
    
    public int insertRolePermissionList(List<RolePermission> records);
    
    public int deleteRolePermissionList(List<RolePermission> records);
}