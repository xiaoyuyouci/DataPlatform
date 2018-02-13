package com.winsafe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.PpStatisticsMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.PpStatisticsService;

@Service
public class PpStatisticsServiceImpl implements PpStatisticsService {

	@Autowired PpStatisticsMapper ppStatisticsMapper;
	
	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getPpStatisticsData(Map<String, Object> filter, DataSourceName datasource) {
		return ppStatisticsMapper.getPpStatisticsData(filter);
	}

}
