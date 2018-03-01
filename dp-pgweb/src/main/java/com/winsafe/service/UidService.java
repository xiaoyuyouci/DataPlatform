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
	
	public List<Map<String, Object>> getCartonUidBaseData(Map<String, Object> filter, DataSourceName datasource);
	
	public List<Map<String, Object>> getCartonUidDetailData(Map<String, Object> filter, DataSourceName datasource);
	
	public List<Map<String, Object>> getItemUidBaseData(Map<String, Object> filter, DataSourceName datasource);
	
	public List<Map<String, Object>> getItemUidDetailData(Map<String, Object> filter, DataSourceName datasource);
	
	public List<Map<String, Object>> getBnoList(String batchNo, DataSourceName datasource);
	
	public List<Map<String, Object>> getBatchDetail(Map<String, Object> filter, DataSourceName datasource);
}
