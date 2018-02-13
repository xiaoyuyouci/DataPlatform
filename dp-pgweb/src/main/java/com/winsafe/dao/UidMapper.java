package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UidMapper {
	public List<Map<String, Object>> getUidData(Map<String, Object> filter);
}
