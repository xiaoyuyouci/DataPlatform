package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DcDailyMapper {
	public List<Map<String, Object>> getDcDailyData(Map<String, Object> filter);
}
