package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FactoryRealtimeMapper {
	public List<Map<String, Object>> getFcRealtimeData(Map<String, Object> filter);
}
