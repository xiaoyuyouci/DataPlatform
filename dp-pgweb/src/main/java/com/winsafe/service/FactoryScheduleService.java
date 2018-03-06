package com.winsafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.FactoryScheduleMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.schedule.FactoryRealtime;

/**
 * 工厂定时任务Service
 * @author Ryan
 *
 */
@Service
public class FactoryScheduleService {
	
	@Autowired 
	FactoryScheduleMapper factoryScheduleMapper;
	
	/**
	 * 从UPLOAD_PRODUCE_REPORT表获取批号数据
	 * @param filter
	 * @param datasource
	 * @return
	 */
	@DataSourceAnnotation
	public List<Map<String, Object>> getBatchNoFromUploadProduceReport(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getBatchNoFromUploadProduceReport(filter);
	}
	
	/**
	 * 根据整体批次号查产品资料
	 * @param filter
	 * @param datasource
	 * @return
	 */
	@DataSourceAnnotation
	public List<Map<String, Object>> getProductFromUploadProduceReport(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getProductFromUploadProduceReport(filter);
	}
	
	/**
	 * 从Upload_produce_report_Item 根据当天的所有生产上传的BatchhNo查询对应的Item数量、工厂代码、线号、所属BU  生产实际上传生产数据
	 * @param filter
	 * @param datasource
	 * @return
	 */
	@DataSourceAnnotation
	public List<Map<String, Object>> getInfoFromUploadProduceReportItem(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getInfoFromUploadProduceReportItem(filter);
	}
	
	/**
	 * 根据当天的所有生产上传的BatchhNo查询对应的Item数量、工厂代码、线号、所属BU  生产实际上传生产数据
	 * @param filter
	 * @param datasource
	 * @return
	 */
	@DataSourceAnnotation
	public List<Map<String, Object>> getInfoFromFactoryDataContranst(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getInfoFromFactoryDataContranst(filter);
	}
	
	/**
	 * 从Upload_produce_report 根据当天的所有生产上传的BatchhNo查询对应的Case数量、工厂代码、线号、所属BU
	 * @param filter
	 * @param datasource
	 * @return
	 */
	@DataSourceAnnotation
	public List<Map<String, Object>> getData5List(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getData5List(filter);
	}
	
	/**
	 * 从FACTORY_DATA_CONTRAS 根据当天查询的所有生产上传的BatchhNo查询对应的Case数据
	 * @param filter
	 * @param datasource
	 * @return
	 */
	@DataSourceAnnotation
	public List<Map<String, Object>> getData6List(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getData6List(filter);
	}
	
	@DataSourceAnnotation
	public List<Map<String, Object>> getListBySql(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getListBySql(filter);
	}
	
	@DataSourceAnnotation
	public int insertListBySql(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.insertListBySql(filter);
	}
	
	/**
	 * 
	 * @param filter
	 * @param datasource
	 * @return
	 */
	@DataSourceAnnotation
	public List<Map<String, Object>> getFcRealtimeBatchs(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getFcRealtimeBatchs(filter);
	}
	@DataSourceAnnotation
	public List<Map<String, Object>> getFcrtData2List(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getFcrtData2List(filter);
	}
	@DataSourceAnnotation
	public List<Map<String, Object>> getFcrtData3List(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getFcrtData3List(filter);
	}
	@DataSourceAnnotation
	public List<Map<String, Object>> getFcrtData4List(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getFcrtData4List(filter);
	}
	@DataSourceAnnotation
	public List<Map<String, Object>> getFcrtData5List(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getFcrtData5List(filter);
	}
	@DataSourceAnnotation
	public List<Map<String, Object>> getFcrtData6List(Map<String, Object> filter, DataSourceName datasource){
		return factoryScheduleMapper.getFcrtData6List(filter);
	}
	@DataSourceAnnotation
	public int updateFaRealtimeStatus(DataSourceName datasource){
		return factoryScheduleMapper.updateFaRealtimeStatus();
	}
	
	@DataSourceAnnotation
	public int updateFaRealtime(FactoryRealtime obj, DataSourceName datasource){
		return factoryScheduleMapper.updateFaRealtime(obj);
	}
	
	@DataSourceAnnotation
	public int updateFaRealtime2(FactoryRealtime obj, DataSourceName datasource){
		return factoryScheduleMapper.updateFaRealtime2(obj);
	}
	
	@DataSourceAnnotation
	public List<FactoryRealtime> getFaRealtimeIfStatusIsSuspended(DataSourceName datasource){
		return factoryScheduleMapper.getFaRealtimeIfStatusIsSuspended();
	}
}
