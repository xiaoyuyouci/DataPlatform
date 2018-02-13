package com.winsafe.service;

import java.util.List;
import java.util.Map;

import com.winsafe.datasource.DataSourceName;

/**
 * 包材厂数据统计Service
 * @author Ryan
 *
 */
public interface PpStatisticsService {
	public List<Map<String, Object>> getPpStatisticsData(Map<String, Object> filter, DataSourceName datasource);
}
