package com.winsafe.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.winsafe.datasource.DataSourceName;
import com.winsafe.model.Resource;
import com.winsafe.service.ResourceService;
import com.winsafe.service.UpdBnoTmpScheduleService;


/**
 * DC工厂每日定时任务
 * @author Ryan
 *
 */
@Component
public class Dc_UpdateBnoTmpSchedule {

	private static final Logger logger = LogManager.getLogger(Dc_UpdateBnoTmpSchedule.class);
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private UpdBnoTmpScheduleService updBnoTmpScheduleService;
	
	//@Scheduled(cron="0 0 0/2 * * ?")
	public void updateBnoTmp(){
		Resource record = new Resource();
		record.setSname("upd_bnotemp");
		record.setSkey("lastid");
		Resource r = resourceService.selectBySnameAndSkey(record);
		if(r == null){
			logger.info("resource[sname='upd_bnotemp',skey='lastid'] not exists.");
			return;
		}
		
		long lastId = StringUtils.isBlank(r.getSvalue())? 0: Long.valueOf(r.getSvalue());
		
		DataSourceName pgdc = new DataSourceName("pgdc");
		Long maxId = updBnoTmpScheduleService.getMaxIdOfUploadIdcodeTemporary(pgdc);
		if(maxId == null || maxId <= lastId){
			logger.info("there is not new data in upload_idcode_temporary.");
			return;
		}
		
		//从upload_idcode_temporary获取数据，存入dc_bnotmp并更新resource中的值
		long step = 10000;
		Map<String, Object> filter = new HashMap<String, Object>();
		while(lastId < maxId){
			long nextLastId = (lastId + step < maxId)? (lastId + step): (maxId);
			filter.put("minId", lastId);
			filter.put("maxId", nextLastId);
			List<Map<String, Object>> list = updBnoTmpScheduleService.selectBnoAndPbatchFromUploadIdcodeTemporary(filter, pgdc);
			r.setSvalue(String.valueOf(nextLastId));
			int count = updBnoTmpScheduleService.updBnoTmp(list, r);
			logger.debug("实际上插入"+count+"条记录，应该插入"+list.size()+"条记录。最后的ID为"+nextLastId);
			lastId = nextLastId;
		}
	}
}
