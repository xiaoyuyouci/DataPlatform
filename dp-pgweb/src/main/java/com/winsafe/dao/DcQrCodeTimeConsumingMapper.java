package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DcQrCodeTimeConsumingMapper {
	public Map<String, Object> getAppliedFileInfo(Map<String, Object> filter);
	public Map<String, Object> getUploadProduceReportInfo(Map<String, Object> filter);
	public Map<String, Object> getOutInfo(Map<String, Object> filter);
	public List<Map<String, Object>> getQrCodeUsageRate(Map<String, Object> filter);
}
