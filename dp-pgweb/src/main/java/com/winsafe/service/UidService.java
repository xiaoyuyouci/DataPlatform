package com.winsafe.service;

import java.util.List;
import java.util.Map;

import com.winsafe.datasource.DataSourceName;

/**
 * UID查询Service
 * @author Ryan
 *
 */
public interface UidService {
	public List<Map<String, Object>> getUidData(Map<String, Object> filter, DataSourceName datasource);
}
