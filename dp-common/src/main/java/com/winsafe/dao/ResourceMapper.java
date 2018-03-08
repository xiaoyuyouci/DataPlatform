package com.winsafe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.winsafe.model.Resource;

@Mapper
public interface ResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
    List<Resource> selectResourceList(Resource record);
    
    Resource selectBySnameAndSkey(Resource record);
}