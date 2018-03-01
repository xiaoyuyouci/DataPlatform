package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.winsafe.datasource.DataSourceName;

@Mapper
public interface UidMapper {
	public List<Map<String, Object>> getUidData(Map<String, Object> filter);
	public List<Map<String, Object>> getCartonUidBaseData(Map<String, Object> filter);
	public List<Map<String, Object>> getCartonUidDetailData(Map<String, Object> filter);
	public List<Map<String, Object>> getItemUidBaseData(Map<String, Object> filter);
	public List<Map<String, Object>> getItemUidDetailData(Map<String, Object> filter);
	public List<Map<String, Object>> getBnoList(String batchNo);
	public List<Map<String, Object>> getBatchDetail(Map<String, Object> filter);
}
