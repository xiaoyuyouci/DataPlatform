package com.winsafe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsafe.dao.ResourceMapper;
import com.winsafe.model.Resource;
import com.winsafe.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
    private ResourceMapper resourceMapper;

	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		return resourceMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int insert(Resource record) {
		return resourceMapper.insert(record);
	}

	@Override
	@Transactional
	public int insertSelective(Resource record) {
		return resourceMapper.insertSelective(record);
	}

	@Override
	public Resource selectByPrimaryKey(Integer id) {
		return resourceMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updateByPrimaryKeySelective(Resource record) {
		return resourceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(Resource record) {
		return resourceMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Resource> selectResourceList(Resource record) {
		return resourceMapper.selectResourceList(record);
	}

	@Override
	public Resource selectBySnameAndSkey(Resource record) {
		return resourceMapper.selectBySnameAndSkey(record);
	}
}
