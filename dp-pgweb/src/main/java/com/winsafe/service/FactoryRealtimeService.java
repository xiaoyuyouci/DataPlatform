package com.winsafe.service;

import java.util.List;
import java.util.Map;

import com.winsafe.datasource.DataSourceName;

/**
 * 工厂实时状态Service
 * @author Ryan
 *
 */
public interface FactoryRealtimeService {
	public List<Map<String, Object>> getFcRealtimeData(Map<String, Object> filter, DataSourceName datasource);
}
