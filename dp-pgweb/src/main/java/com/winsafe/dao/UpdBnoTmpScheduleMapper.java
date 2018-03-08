package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UpdBnoTmpScheduleMapper {
	/**
	 * 从upload_idcode_temporary表获取单据号和批号
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> selectBnoAndPbatchFromUploadIdcodeTemporary(Map<String, Object> filter);
	
	/**
	 * 从upload_idcode_temporary表获取最大Id
	 * @param filter
	 * @return
	 */
	public Long getMaxIdOfUploadIdcodeTemporary();
	
	public List<Map<String, Object>> getListBySql(Map<String, Object> filter);
	
	public int insertListBySql(Map<String, Object> filter);
	
	public int insertBnoTmp(Map<String, Object> filter);
}
