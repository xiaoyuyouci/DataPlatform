package com.winsafe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.FactoryDailyMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.FactoryDailyService;

@Service
public class FactoryDailyServiceImpl implements FactoryDailyService {

	@Autowired FactoryDailyMapper factoryDailyMapper;
	
	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getFcDailyData(Map<String, Object> filter, DataSourceName datasource) {
		return factoryDailyMapper.getFcDailyData(filter);
	}

}
