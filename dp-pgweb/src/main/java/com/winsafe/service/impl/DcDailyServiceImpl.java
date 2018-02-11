package com.winsafe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.DcDailyMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.DcDailyService;

@Service
public class DcDailyServiceImpl implements DcDailyService {

	@Autowired DcDailyMapper dcDailyMapper;
	
	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getDcDailyData(Map<String, Object> filter, DataSourceName datasource) {
		return dcDailyMapper.getDcDailyData(filter);
	}

}
