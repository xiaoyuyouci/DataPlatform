package com.winsafe.service;

import java.util.List;
import java.util.Map;

import com.winsafe.datasource.DataSourceName;

/**
 * DC的QR码使用情况Service
 * @author Ryan
 *
 */
public interface DcQrCodeUsageRatioService {
	public List<Map<String, Object>> selectQrCodeUsageRate(Map<String, Object> filter);
	public int insertQrCodeUsageRate(Map<String, Object> filter);
	public int updateQrCodeUsageRate(Map<String, Object> filter);
	/**
	 * 从DC获取文件列表
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getFileInfo(Map<String, Object> filter, DataSourceName dsn);
	/**
	 * 从DC获取上传信息
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getUploadInfo(Map<String, Object> filter, DataSourceName dsn);
	/**
	 * 从DC获取出库信息
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getOutInfo(Map<String, Object> filter, DataSourceName dsn);
}
