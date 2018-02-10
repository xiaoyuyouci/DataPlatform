package com.winsafe.service;

import java.util.List;
import java.util.Map;

/**
 * 工厂状态Service
 * @author Ryan
 *
 */
public interface FactoryRealtimeService {
	public List<Map<String, Object>> getFcRealtimeData(Map<String, Object> filter, String datasource);
}
