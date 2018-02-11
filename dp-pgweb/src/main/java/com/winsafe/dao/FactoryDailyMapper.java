package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FactoryDailyMapper {
	public List<Map<String, Object>> getFcDailyData(Map<String, Object> filter);
}
