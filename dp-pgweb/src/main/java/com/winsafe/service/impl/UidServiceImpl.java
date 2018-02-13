package com.winsafe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.UidMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.UidService;

@Service
public class UidServiceImpl implements UidService {

	@Autowired UidMapper uidMapper;
	
	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getUidData(Map<String, Object> filter, DataSourceName datasource) {
		return uidMapper.getUidData(filter);
	}

}
