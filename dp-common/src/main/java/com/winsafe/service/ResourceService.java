package com.winsafe.service;

import java.util.List;

import com.winsafe.model.Resource;

public interface ResourceService {
	int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
    List<Resource> selectResourceList(Resource record);
    
    Resource selectBySnameAndSkey(Resource record);
    
    Resource selectBySnameAndSkey(String name, String key);
}
