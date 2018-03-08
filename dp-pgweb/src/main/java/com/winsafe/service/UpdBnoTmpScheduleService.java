package com.winsafe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.ResourceMapper;
import com.winsafe.dao.UpdBnoTmpScheduleMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.model.Resource;
import com.winsafe.utils.DateUtil;

/**
 * 工厂定时任务Service
 * @author Ryan
 *
 */
@Service
public class UpdBnoTmpScheduleService {
	
	@Autowired 
	UpdBnoTmpScheduleMapper updBnoTmpScheduleMapper;
	
	@Autowired 
	ResourceMapper resourceMapper;
	
	/**
	 * 从upload_idcode_temporary表获取单据号和批号
	 * @param filter
	 * @param datasource
	 * @return
	 */
	@DataSourceAnnotation
	public List<Map<String, Object>> selectBnoAndPbatchFromUploadIdcodeTemporary(Map<String, Object> filter, DataSourceName datasource){
		return updBnoTmpScheduleMapper.selectBnoAndPbatchFromUploadIdcodeTemporary(filter);
	}
	
	/**
	 * 从upload_idcode_temporary表获取最大Id
	 * @param filter
	 * @param datasource
	 * @return
	 */
	@DataSourceAnnotation
	public Long getMaxIdOfUploadIdcodeTemporary(DataSourceName datasource){
		return updBnoTmpScheduleMapper.getMaxIdOfUploadIdcodeTemporary();
	}
	
	@Transactional
	public int updBnoTmp(List<Map<String, Object>> list, Resource r ){
		
		int insertCount = 0;
		
		if(list == null || list.size() <=0 || r == null){
			return insertCount;
		}
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		int step = 5000;
		if(list.size() <= step){
			filter.put("list", list);
			insertCount += updBnoTmpScheduleMapper.insertBnoTmp(filter);
		}
		else{
			int a = list.size()/step;
			int b = list.size()%step;
			int fromIndex = 0;
			for(int i=0; i< a; i++){
				filter.put("list", list.subList(fromIndex, fromIndex+step-1));
				insertCount += updBnoTmpScheduleMapper.insertBnoTmp(filter);
				fromIndex = fromIndex + step;
			}
			if(b > 0){
				filter.put("list", list.subList(fromIndex, fromIndex+b-1));
				insertCount += updBnoTmpScheduleMapper.insertBnoTmp(filter);
				fromIndex = fromIndex + b;
			}
		}
	
		Resource record = new Resource();
		record.setId(r.getId());
		record.setModificationTime(DateUtil.now());
		record.setSvalue(r.getSvalue());
		resourceMapper.updateByPrimaryKeySelective(record);
		
		return insertCount;
	}
	
}
