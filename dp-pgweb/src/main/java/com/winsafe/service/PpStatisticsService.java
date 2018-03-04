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
	/**
	 * IPC 上传数据那一列
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getIpcUploadData(Map<String, Object> filter, DataSourceName datasource);
	/**
	 * 当天应给Aimia 上传数据那列
	 * @param filter
	 * @return
	 */
	public Map<String, Integer> getPlannedDataUploadToAimia(Map<String, Object> filter, DataSourceName datasource);
	/**
	 * 当天实际给Aimia 上传数据那列
	 * @param filter
	 * @return
	 */
	public Map<String, Integer> getActualDataUploadToAimia(Map<String, Object> filter, DataSourceName datasource);
	/**
	 * 14天后需要给Aimia 上传数据
	 * @param filter
	 * @return
	 */
	public Map<String, Integer> getPDUTA14Days(Map<String, Object> filter, DataSourceName datasource);
}
