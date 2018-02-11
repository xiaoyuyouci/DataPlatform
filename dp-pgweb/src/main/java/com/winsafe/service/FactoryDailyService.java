package com.winsafe.service;

import java.util.List;
import java.util.Map;

import com.winsafe.datasource.DataSourceName;

/**
 * 工厂每日状态Service
 * @author Ryan
 *
 */
public interface FactoryDailyService {
	public List<Map<String, Object>> getFcDailyData(Map<String, Object> filter, DataSourceName datasource);
}
