package com.winsafe.service;

import java.util.List;
import java.util.Map;

import com.winsafe.datasource.DataSourceName;

/**
 * DC每日状态Service
 * @author Ryan
 *
 */
public interface DcDailyService {
	public List<Map<String, Object>> getDcDailyData(Map<String, Object> filter, DataSourceName datasource);
}
