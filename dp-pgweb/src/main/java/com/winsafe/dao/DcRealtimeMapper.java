package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DcRealtimeMapper {
	public List<Map<String, Object>> getDcRealtimeData(Map<String, Object> filter);
}
