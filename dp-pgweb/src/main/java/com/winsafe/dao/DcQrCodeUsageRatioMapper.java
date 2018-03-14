package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DcQrCodeUsageRatioMapper {
	public List<Map<String, Object>> selectQrCodeUsageRate(Map<String, Object> filter);
	/**
	 * 将不在表中的文件信息添加进去
	 * @param filter
	 * @return
	 */
	public int insertQrCodeUsageRate(Map<String, Object> filter);
	public int updateQrCodeUsageRate(Map<String, Object> filter);
	
	/**
	 * 从DC获取文件列表
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getFileInfo(Map<String, Object> filter);
	/**
	 * 从DC获取上传信息
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getUploadInfo(Map<String, Object> filter);
	/**
	 * 从DC获取出库信息
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getOutInfo(Map<String, Object> filter);
}
