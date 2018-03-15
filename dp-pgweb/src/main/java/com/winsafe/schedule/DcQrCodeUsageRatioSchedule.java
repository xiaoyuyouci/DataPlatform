package com.winsafe.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.winsafe.datasource.DataSourceName;
import com.winsafe.model.Resource;
import com.winsafe.service.DcQrCodeUsageRatioService;
import com.winsafe.service.ResourceService;
import com.winsafe.utils.DateUtil;
import com.winsafe.utils.MapUtil;

/**
 * DC，获取QR码申请文件中码的使用情况
 * @author Ryan
 *
 */
@Component
public class DcQrCodeUsageRatioSchedule {

	@Autowired
	private DcQrCodeUsageRatioService service;

	@Autowired
	private ResourceService resourceService;
	
	@Scheduled(cron="0 0 0/8 * * ?")
    public void execute() {
        
    	Map<String, Object> filter = new HashMap<String, Object>();
    	DataSourceName pgdc = new DataSourceName("pgdc");
    	
        //从DC数据库获取QR码文件申请日志
    	filter.put("startDate", DateUtil.formatDatetime(DateUtil.addMonth(DateUtil.now(), -6)));
        List<Map<String, Object>> qrFileList = MapUtil.toLowerCaseKey(service.getFileInfo(filter, pgdc));
        
        //如果表dc_qrcodeusageratio不含有上面获得文件，则加入dc_qrcodeusageratio表
        filter.clear();
        filter.put("list", qrFileList);
        service.insertQrCodeUsageRate(filter);
        
        //按条件从dc_qrcodeusageratio获取文件信息
        filter.clear();
        filter.put("startDate", DateUtil.formatDatetime(DateUtil.addMonth(DateUtil.now(), getStartDateOffset())));
        List<Map<String, Object>> fList = MapUtil.toLowerCaseKey(service.selectQrCodeUsageRate(filter));
        
        //从DC获取上传信息
        filter.clear();
		filter.put("list", fList);
		List<Map<String, Object>> uploadInfoList = MapUtil.toLowerCaseKey(service.getUploadInfo(filter, pgdc));
		Map<String, Map<String, Object>> uploadInfoMap = new HashMap<String, Map<String, Object>>();
		for(Map<String, Object> map: uploadInfoList){
			uploadInfoMap.put(String.valueOf(map.get("filename")), map);
		}
		
		//从DC获取出库信息
		filter.clear();
		filter.put("list", fList);
		List<Map<String, Object>> outInfoList = MapUtil.toLowerCaseKey(service.getOutInfo(filter, pgdc));
		Map<String, Map<String, Object>> outInfoMap = new HashMap<String, Map<String, Object>>();
		for(Map<String, Object> map: outInfoList){
			outInfoMap.put(String.valueOf(map.get("filename")), map);
		}
		
		//填充数据
		for(Map<String, Object> map: fList){
			if(uploadInfoMap.containsKey(map.get("filename"))){
				map.putAll(uploadInfoMap.get(map.get("filename")));
			}
			if(outInfoMap.containsKey(map.get("filename"))){
				map.putAll(outInfoMap.get(map.get("filename")));
			}
		}
        
		service.updateQrCodeUsageRate(filter);
    }
	
	private int getStartDateOffset(){
	   	Resource record = new Resource();
    	record.setSname("dc_qrCodeUsageRatio");
    	record.setSkey("startDateOffset");
    	Resource r = resourceService.selectBySnameAndSkey(record);
    	if(r == null || StringUtils.isBlank(r.getSvalue())){
    		return 0;
    	}
    	return Integer.valueOf(r.getSvalue());
	}
}
