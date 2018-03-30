package com.winsafe.schedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.winsafe.service.FactoryScheduleService;
import com.winsafe.service.ResourceService;
import com.winsafe.utils.DateUtil;


/**
 * DC工厂定时任务
 * @author Ryan
 *
 */
@Component
public class Dc_FcRealtimeSchedule {

	private static final Logger logger = LogManager.getLogger(Dc_FcRealtimeSchedule.class);
	
	@Autowired
	private FactoryScheduleService service;
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 工厂实时状态 监测一个时间段是否有上传数据（设定为2个小时）
	 */
	public void updateRealtime(){
		
		DataSourceName pgdc = new DataSourceName("pgdc");
		DataSourceName primary = new DataSourceName("primary");
		
		Resource record = new Resource();
		record.setSname("dc_fcRealtimeSchedule");
		record.setSkey("lastid");
		Resource r = resourceService.selectBySnameAndSkey(record);
		if(r == null){
			logger.error("resource[sname='dc_fcRealtimeSchedule',skey='lastid'] not exists.");
			return;
		}
		Long lastId = StringUtils.isBlank(r.getSvalue())? 0: Long.valueOf(r.getSvalue());;
		
		Long maxId = service.getMaxIdOfUploadProduceReport(pgdc);
		if(maxId == null || maxId <= lastId){
			logger.info("there is not new data in UPLOAD_PRODUCE_REPORT.");
			return;
		}
		
		//用来存储最后拼凑好的数组
		Map<String,Map<String,Object>> result = new HashMap<String,Map<String,Object>>();
		//String start = DateUtil.formatDatetime(DateUtil.addHour(DateUtil.now(), getStartDateOffset()));
		//String end = DateUtil.formatDatetime(DateUtil.now());
		String start = null;
		String end = null;
		
		//第一次查询，查询改时间段内批次最后一次上传时间
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("start", start);
		filter.put("end", end);
		filter.put("lastId", lastId);
		List<Map<String, Object>> data = service.getFcRealtimeBatchs(filter, pgdc);

		if(data !=null && data.size()>0){
			List<String> batchList = new ArrayList<String>();
			for(Map<String, Object> map: data){
				String batchNo = map.get("BATCHNO") == null? "": String.valueOf(map.get("BATCHNO"));
				if(StringUtils.isNotBlank(batchNo) && !isSameChars(batchNo) && !"1234567890".equals(batchNo)){
					batchList.add(batchNo);
					result.put(batchNo, map);
				}
			}
			
			//不存在批号则退出
			if(batchList.size() == 0){
				return;
			}
			
			
			//第二次查询 将批次、时间与产线、工厂代码、事业部对应起来，并且每条产线只对应最新一次上传记录的批次号
			//根据整体批次号查产品资料
			filter.put("batchList", batchList);
			List<Map<String, Object>> data2 = service.getFcrtData2List(filter, pgdc);
			if(data2 != null && data2.size()>0){
				for(int i=0; i<data2.size(); i++){
					String batch = String.valueOf(data2.get(i).get("BATCHNO"));
					if(result.get(batch) != null){
						result.get(batch).putAll(data2.get(i));
					}
				}
			}
			
			//从Upload_produce_report_Item 根据当天的所有生产上传的BatchhNo查询对应的Item数量、工厂代码、线号、所属BU  生产实际上传生产数据
			List<Map<String,Object>> data3 = service.getFcrtData3List(filter, pgdc);
			if(data3 !=null && data3.size()>0){
				for(int i=0;i<data3.size();i++){
					String obj = String.valueOf(data3.get(i).get("BATCHNO"));
					if(result.get(obj) != null){
						result.get(obj).putAll(data3.get(i));
					}
				}
			}
			
			List<Map<String,Object>> data4 = service.getFcrtData4List(filter, pgdc);
			if(data4 !=null && data4.size()>0){
				for(int i=0;i<data4.size();i++){
					String obj = String.valueOf(data4.get(i).get("FPCBATCH"));
					if(result.get(obj) != null){
						result.get(obj).putAll(data4.get(i));
					}
				}
			}
			
			//从Upload_produce_report 根据当天的所有生产上传的BatchhNo查询对应的Case数量、工厂代码、线号、所属BU
			List<Map<String,Object>> data5 = service.getFcrtData5List(filter, pgdc);
			if(data5 !=null && data5.size()>0){
				for(int i=0;i<data5.size();i++){
					String obj = String.valueOf(data5.get(i).get("BATCHNO"));
					if(result.get(obj) != null){
						result.get(obj).putAll(data5.get(i));
					}
				}
			}
			
			//从FACTORY_DATA_CONTRAS 根据当天查询的所有生产上传的BatchhNo查询对应的Case数据
			List<Map<String,Object>> data6 = service.getFcrtData6List(filter, pgdc);
			if(data6 !=null && data6.size()>0){
				for(int i=0;i<data6.size();i++){
					String obj = String.valueOf(data6.get(i).get("FPCBATCH"));
					if(result.get(obj) != null){
						result.get(obj).putAll(data6.get(i));
					}
				}
			}
		}
		if(result !=null && result.size()>0){
			//先将所有产线状态改为暂停
			service.updateFaRealtimeStatus(primary);
			
			String value = "";
			//更新之前先将状态设置为暂停
			for(Map<String,Object> map : result.values()){
				FactoryRealtime fr =  new FactoryRealtime();
				long kn=0, hn=0, pn=0, mn=0;
				String case_item = "";
				fr.setUp_time(getStringVal(map.get("UP_TIME")));//上传时间
				fr.setPlantcode(getStringVal(map.get("PLANTCODE")));//工厂
				fr.setLinecode(getStringVal(map.get("LINECODE")));//线号
				fr.setBu(getStringVal(map.get("SORTNAME")));//BU
				fr.setBatchno(getStringVal(map.get("BATCHNO")));//批次
				fr.setCase_item(StringUtils.isBlank(getStringVal(map.get("ITEMCASECONNECTION")))?"N": getStringVal(map.get("ITEMCASECONNECTION")));//是否为一瓶一码
				fr.setFpc(getStringVal(map.get("MCODE")));//产品代码
				fr.setProductname(getStringVal(map.get("MATERICALCHDES")));//产品名称
				fr.setCkqrnum1(getStringVal(map.get("CKQRNUM1")));//生产计划item数量
				if(StringUtils.isNotBlank(fr.getCkqrnum1())){
					hn = Long.valueOf(fr.getCkqrnum1());//计划生产item数量
				}
				
				fr.setElqrnum1(getStringVal(map.get("ELQRNUM1")));//item剔除数量
				fr.setScannum1(getStringVal(map.get("SCANNUM1")));//item本地数量
				
				fr.setCount1(getStringVal(map.get("COUNT1")));//实际item数量
				if(StringUtils.isNotBlank(fr.getCount1())){
					kn = Long.valueOf(fr.getCount1());//实际item数量
				}
				
				fr.setContext1("");
				
				fr.setCkqrnum2(getStringVal(map.get("CKQRNUM2")));//生产计划case数量
				if(StringUtils.isNotBlank(fr.getCkqrnum2())){
					mn = Long.valueOf(fr.getCkqrnum2());//计划生产case数量
				}
				
				fr.setElqrnum2(getStringVal(map.get("ELQRNUM2")));//case剔除数量
				
				fr.setScannum2(getStringVal(map.get("SCANNUM2")));//case本地数量
				
				fr.setCount2(getStringVal(map.get("COUNT2")));//实际case数量
				if(StringUtils.isNotBlank(fr.getCount2())){
					pn = Long.valueOf(fr.getCount2());//实际case数量
				}
				fr.setContext2(fr.getCount2());
				
				//item合格率K/H
				if(kn >0 && hn>0){
					 BigDecimal bg = new BigDecimal((double)kn/hn);
			         value = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()*100 +"";
				}else{
					value = null;
				}
				fr.setItem_percent(value);
				
				//case合格率P/M
				if(pn >0 && mn>0){
					 BigDecimal bg = new BigDecimal((double)pn/mn);
			         value = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()*100 +"";
				}else{
					value = null;
				}
				fr.setCase_percent(value);
				
				//实际箱瓶比例K/p
				if(kn >0 && pn>0){
					value =new BigDecimal((double)kn/pn).setScale(0, BigDecimal.ROUND_HALF_UP) +"";
				}else{
					value = null;
				}
				fr.setReal_package(value);
				
				fr.setCase_package(getStringVal(map.get("CASEPACKAGE")));//masterdata中箱瓶比例
				
				//比例是否标准
				if(case_item.equals("Y")){
					if(fr.getReal_package() != null && fr.getCase_package() != null && fr.getReal_package().equals(fr.getCase_package())){
						value = "Y";
					}else{
						value = "N";
					}
				}else{
					value = "Y";
				}
				fr.setIs_true(value);
				service.updateFaRealtime(fr, primary);
				value = "";
			}
			//先完成新上传的批次号的更新
			logger.debug("Finish the first update dc_farealtime data!");
			
			//更新暂停中的批次号的数量变更，如果数量发生改变则在这两个小时之内也有新的数据上传
			List<FactoryRealtime> frList = service.getFaRealtimeIfStatusIsSuspended(primary);
			if(frList != null && frList.size() > 0){
				//查询这些批次号的数量是否有改变
				Map<String,Map<String,Object>> res = new HashMap<String,Map<String,Object>>();
				
				List<String> batchList = new ArrayList<String>();
				for(FactoryRealtime fr: frList){
					if(StringUtils.isNotBlank(fr.getBatchno())){
						batchList.add(fr.getBatchno());
					}
				}
				
				//查询item的count
				filter.clear();
				filter.put("batchList", batchList);
				List<Map<String,Object>> d1 = service.getFcrtData3List(filter, pgdc);
				if(d1 !=null && d1.size()>0){
					for(int i=0;i<d1.size();i++){
						String bh = String.valueOf(d1.get(i).get("BATCHNO"));
						if(bh != null && bh.length()>0){
							res.put(bh, d1.get(i));
						}
					}
				}
				
				//查询item的一些零碎数量
				List<Map<String,Object>> d2 = service.getFcrtData4List(filter, pgdc);
				if(d2 !=null && d2.size()>0){
					for(int i=0;i<d2.size();i++){
						String bh = String.valueOf(d2.get(i).get("FPCBATCH"));
						if(bh != null && bh.length()>0){
							if(res.get(bh) != null){
								res.get(bh).putAll(d2.get(i));
							}else{
								res.put(bh,d2.get(i));
							}
						}
					}
				}
				//查询case的count
				List<Map<String,Object>> d3 = service.getFcrtData5List(filter, pgdc);
				if(d3 !=null && d3.size()>0){
					for(int i=0;i<d3.size();i++){
						String bh = String.valueOf(d3.get(i).get("BATCHNO"));
						if(bh != null && bh.length()>0){
							if(res.get(bh) != null){
								res.get(bh).putAll(d3.get(i));
							}else{
								res.put(bh,d3.get(i));
							}
						}
					}
				}
				//查询case的一些零碎数量
				List<Map<String,Object>> d4 = service.getFcrtData6List(filter, pgdc);
				if(d4 !=null && d4.size()>0){
					for(int i=0;i<d4.size();i++){
						String bh = String.valueOf(d4.get(i).get("FPCBATCH"));
						if(bh != null && bh.length()>0){
							if(res.get(bh) != null){
								res.get(bh).putAll(d4.get(i));
							}else{
								res.put(bh,d4.get(i));
							}
						}
					}
				}
				
				//开始循环比对数据是否发生过变化
				boolean flag = false;//数据变化标志
				for(FactoryRealtime frE: frList){
					String batch = frE.getBatchno();
					Map<String,Object> map2 = res.get(batch);//新查询出来的对象
					if(map2 != null){
						//将两个对象的count1 和count2 做比对，不一致就做数据更新，同时也将该条记录归为生产中
						String ob1 = frE.getCount1();
						String ob2 = getStringVal(map2.get("COUNT1"));
						if(ob1 != null && ob2 != null && !ob1.equals(ob2)){ 
							flag = true;
						}
						
						ob1 = frE.getCount2();
						ob2 = getStringVal(map2.get("COUNT2"));
						if(ob1 != null && ob2 != null && !ob1.equals(ob2)){ 
							flag = true;
						}
						
						if(flag){//数量有变动的情况下
							FactoryRealtime fr =  new FactoryRealtime();
							fr.setBatchno(batch);
							long kn=0,hn=0,pn=0,mn=0;
							
							fr.setCkqrnum1(getStringVal(map2.get("CKQRNUM1")));//生产计划item数量
							if(StringUtils.isNotBlank(fr.getCkqrnum1())){
								hn = Long.valueOf(fr.getCkqrnum1());//计划生产item数量
							}
							
							fr.setElqrnum1(getStringVal(map2.get("CKQELQRNUM1RNUM1")));//item剔除数量
							
							fr.setScannum1(getStringVal(map2.get("SCANNUM1")));//item本地数量
							
							fr.setCount1(getStringVal(map2.get("COUNT1")));
							if(StringUtils.isNotBlank(fr.getCount1())){
								kn = Long.valueOf(fr.getCount1());//实际item数量
							}
							
//							value = String.valueOf(map.get("BATCHNO"));//实际收到item数量
							fr.setContext1("");
							
							fr.setCkqrnum2(getStringVal(map2.get("CKQRNUM2")));//生产计划case数量
							if(StringUtils.isNotBlank(fr.getCkqrnum2())){
								mn = Long.valueOf(fr.getCkqrnum2());//计划生产case数量
							}
							
							fr.setElqrnum2(getStringVal(map2.get("ELQRNUM2")));//case剔除数量
							
							fr.setScannum2(getStringVal(map2.get("SCANNUM2")));//case本地数量
							
							fr.setCount2(getStringVal(map2.get("COUNT2")));
							if(StringUtils.isNotBlank(fr.getCount2())){
								pn = Long.valueOf(fr.getCount2());//实际case数量
							}
//							value = String.valueOf(map.get("BATCHNO"));//实际收到case数量
							fr.setContext2(fr.getCount2());
//							
							//item合格率K/H
							if(kn >0 && hn>0){
								 BigDecimal bg = new BigDecimal((double)kn/hn);
						         value = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()*100 +"";
							}else{
								value = null;
							}
							fr.setItem_percent(value);
//									//case合格率P/M
							if(pn >0 && mn>0){
								 BigDecimal bg = new BigDecimal((double)pn/mn);
						         value = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()*100 +"";
							}else{
								value = null;
							}
							fr.setCase_percent(value);
//									//实际箱瓶比例K/p
							if(kn >0 && pn>0){
								value =new BigDecimal((double)kn/pn).setScale(0, BigDecimal.ROUND_HALF_UP) +"";
							}else{
								value = null;
							}
							fr.setReal_package(value);
							String t1 = value;
							
							value = frE.getCase_package();//masterdata中箱瓶比例
							fr.setCase_package(value);
							String t2 = value;
							
							String case_item = frE.getCase_item();
							fr.setCase_item(case_item);
//							//比例是否标准
							if(case_item != null && case_item.equals("Y")){
								if(t1 != null && t2 != null && t1.equals(t2)){
									value = "Y";
								}else{
									value = "N";
								}
							}else{
								value = "Y";
							}
							fr.setIs_true(value);
							service.updateFaRealtime2(fr, primary);
							logger.debug("Finish update number!");
						}
						flag = false;
					}
				}
			}
		}
		
		Resource res = new Resource();
		res.setId(r.getId());
		res.setModificationTime(DateUtil.now());
		res.setSvalue(String.valueOf(maxId));
		resourceService.updateByPrimaryKeySelective(res);
	}
	
	private boolean isSameChars (String str) throws IllegalArgumentException {
        if (str == null)
                throw new IllegalArgumentException("Input string should not be null.");
        else if (str.length() < 2)
                return true;
        char first = str.charAt(0);
        for (int i=1; i<str.length(); i++){
            if (str.charAt(i) != first){
                    return false;
            }
        }
        return true;
	}
	
	private String getStringVal(Object obj){
		if(obj == null){
			return null;
		}
		else if(obj instanceof Date){
			return DateUtil.formatDate((Date) obj);
		}
		else if(obj instanceof String){
			return "null".equals((String.valueOf(obj).trim()))? null: String.valueOf(obj).trim();
		}
		else {
			return String.valueOf(obj);
		}
	}
	
	private int getStartDateOffset(){
	   	Resource record = new Resource();
    	record.setSname("dc_fcRealtimeSchedule");
    	record.setSkey("startDateOffset");
    	Resource r = resourceService.selectBySnameAndSkey(record);
    	if(r == null || StringUtils.isBlank(r.getSvalue())){
    		return -2;
    	}
    	return Integer.valueOf(r.getSvalue());
	}
}
