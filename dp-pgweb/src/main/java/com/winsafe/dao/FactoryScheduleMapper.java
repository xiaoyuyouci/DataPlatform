package com.winsafe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.winsafe.schedule.FactoryRealtime;

@Mapper
public interface FactoryScheduleMapper {
	/**
	 * 从UPLOAD_PRODUCE_REPORT表获取批号数据
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getBatchNoFromUploadProduceReport(Map<String, Object> filter);
	
	/**
	 * 根据整体批次号查产品资料
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getProductFromUploadProduceReport(Map<String, Object> filter);
	
	/**
	 * 从Upload_produce_report_Item 根据当天的所有生产上传的BatchhNo查询对应的Item数量、工厂代码、线号、所属BU  生产实际上传生产数据
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getInfoFromUploadProduceReportItem(Map<String, Object> filter);
	
	/**
	 * 根据当天的所有生产上传的BatchhNo查询对应的Item数量、工厂代码、线号、所属BU  生产实际上传生产数据
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getInfoFromFactoryDataContranst(Map<String, Object> filter);
	
	/**
	 * 从Upload_produce_report 根据当天的所有生产上传的BatchhNo查询对应的Case数量、工厂代码、线号、所属BU
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getData5List(Map<String, Object> filter);
	
	/**
	 * 从FACTORY_DATA_CONTRAS 根据当天查询的所有生产上传的BatchhNo查询对应的Case数据
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> getData6List(Map<String, Object> filter);
	
	public List<Map<String, Object>> getListBySql(Map<String, Object> filter);
	
	public int insertListBySql(Map<String, Object> filter);
	
	public List<Map<String, Object>> getFcRealtimeBatchs(Map<String, Object> filter);
	
	public List<Map<String, Object>> getFcrtData2List(Map<String, Object> filter);
	public List<Map<String, Object>> getFcrtData3List(Map<String, Object> filter);
	public List<Map<String, Object>> getFcrtData4List(Map<String, Object> filter);
	public List<Map<String, Object>> getFcrtData5List(Map<String, Object> filter);
	public List<Map<String, Object>> getFcrtData6List(Map<String, Object> filter);
	
	public int updateFaRealtimeStatus();
	public int updateFaRealtime(FactoryRealtime obj);
	public int updateFaRealtime2(FactoryRealtime obj);
	public List<FactoryRealtime> getFaRealtimeIfStatusIsSuspended();
	
	/**
	 * 从DC的UPLOAD_PRODUCE_REPORT表获取最大Id
	 * @param filter
	 * @return
	 */
	public Long getMaxIdOfUploadProduceReport();
}
