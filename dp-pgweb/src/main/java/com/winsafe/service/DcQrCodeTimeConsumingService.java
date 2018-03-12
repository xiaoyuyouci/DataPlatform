package com.winsafe.service;

import java.util.List;
import java.util.Map;

import com.winsafe.datasource.DataSourceName;

/**
 * DC耗时查询Service
 * @author Ryan
 *
 */
public interface DcQrCodeTimeConsumingService {
	public Map<String, Object> getAppliedFileInfo(Map<String, Object> filter, DataSourceName datasource);
	public Map<String, Object> getUploadProduceReportInfo(Map<String, Object> filter, DataSourceName datasource);
	public Map<String, Object> getOutInfo(Map<String, Object> filter, DataSourceName datasource);
	public List<Map<String, Object>> getQrCodeUsageRate(Map<String, Object> filter, DataSourceName datasource);
}
