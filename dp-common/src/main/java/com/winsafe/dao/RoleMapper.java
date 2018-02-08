package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.winsafe.model.Role;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> selectRoleList(Role record);
    
    List<Role> selectRoleListByUserId(Map map);
    
    List<Role> selectUnselectedRoleListByUserId(Map map);
}