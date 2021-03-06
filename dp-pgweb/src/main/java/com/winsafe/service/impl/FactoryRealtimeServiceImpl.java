package com.winsafe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.FactoryRealtimeMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.FactoryRealtimeService;

@Service
public class FactoryRealtimeServiceImpl implements FactoryRealtimeService {

	@Autowired FactoryRealtimeMapper factoryRealtimeMapper;
	
	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getFcRealtimeData(Map<String, Object> filter, DataSourceName datasource) {
		return factoryRealtimeMapper.getFcRealtimeData(filter);
	}

}
