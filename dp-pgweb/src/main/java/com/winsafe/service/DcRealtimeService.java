package com.winsafe.service;

import java.util.List;
import java.util.Map;

import com.winsafe.datasource.DataSourceName;

/**
 * Dc实时状态Service
 * @author Ryan
 *
 */
public interface DcRealtimeService {
	public List<Map<String, Object>> getDcRealtimeData(Map<String, Object> filter, DataSourceName datasource);
}
