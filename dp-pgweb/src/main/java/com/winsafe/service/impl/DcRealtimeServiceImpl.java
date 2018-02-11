package com.winsafe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.DcRealtimeMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.DcRealtimeService;

@Service
public class DcRealtimeServiceImpl implements DcRealtimeService {

	@Autowired DcRealtimeMapper dcRealtimeMapper;
	
	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getDcRealtimeData(Map<String, Object> filter, DataSourceName datasource) {
		return dcRealtimeMapper.getDcRealtimeData(filter);
	}

}
