package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PpStatisticsMapper {
	public List<Map<String, Object>> getPpStatisticsData(Map<String, Object> filter);
}
