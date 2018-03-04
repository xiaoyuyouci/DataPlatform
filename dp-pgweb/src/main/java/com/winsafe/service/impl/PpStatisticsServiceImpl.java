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
	public List<Map<String, Object>> getIpcUploadData(Map<String, Object> filter, DataSourceName datasource) {
		return ppStatisticsMapper.getIpcUploadData(filter);
	}

	@Override
	@DataSourceAnnotation
	public Map<String, Integer> getPlannedDataUploadToAimia(Map<String, Object> filter, DataSourceName datasource) {
		return ppStatisticsMapper.getPlannedDataUploadToAimia(filter);
	}

	@Override
	@DataSourceAnnotation
	public Map<String, Integer> getActualDataUploadToAimia(Map<String, Object> filter, DataSourceName datasource) {
		return ppStatisticsMapper.getActualDataUploadToAimia(filter);
	}

	@Override
	@DataSourceAnnotation
	public Map<String, Integer> getPDUTA14Days(Map<String, Object> filter, DataSourceName datasource) {
		return ppStatisticsMapper.getPDUTA14Days(filter);
	}

}
