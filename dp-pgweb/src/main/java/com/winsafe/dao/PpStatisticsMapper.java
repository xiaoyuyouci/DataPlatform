package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PpStatisticsMapper {
	/**
	 * IPC 上传数据那一列
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getIpcUploadData(Map<String, Object> filter);
	/**
	 * 当天应给Aimia 上传数据那列
	 * @param filter
	 * @return
	 */
	public Map<String, Integer> getPlannedDataUploadToAimia(Map<String, Object> filter);
	/**
	 * 当天实际给Aimia 上传数据那列
	 * @param filter
	 * @return
	 */
	public Map<String, Integer> getActualDataUploadToAimia(Map<String, Object> filter);
	/**
	 * 14天后需要给Aimia 上传数据
	 * @param filter
	 * @return
	 */
	public Map<String, Integer> getPDUTA14Days(Map<String, Object> filter);
}
