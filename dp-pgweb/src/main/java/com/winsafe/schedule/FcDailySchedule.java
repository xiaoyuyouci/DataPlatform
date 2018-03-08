package com.winsafe.schedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.FactoryScheduleService;
import com.winsafe.utils.DateUtil;


/**
 * DC工厂每日定时任务
 * @author Ryan
 *
 */
@Component
public class FcDailySchedule {

	@Autowired
	private FactoryScheduleService service;
	
	@Scheduled(cron="0 0 5 * * ?")//每天凌晨五点统计上一天的数据存入缓存数据库中
	public void factoryDailyData(){
		
		//获取当前日期的前一天
        String date = DateUtil.formatDate(DateUtil.addDay(DateUtil.now(), -1));
        //String date = "2016-12-05";
		 //唯一批次号对应某一天
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("date", date);
		
		DataSourceName dsn = new DataSourceName("pgdc");
		List<Map<String,Object>> data = service.getBatchNoFromUploadProduceReport(filter, dsn);
		
		String patchs = "";
		if(data != null && data.size() > 0){
			List<String> batchList = new ArrayList<String>();
			for(Map<String, Object> map: data){
				String batchNo = map.get("BATCHNO") == null? "": String.valueOf(map.get("BATCHNO"));
				if(StringUtils.isNotBlank(batchNo) && !isSameChars(batchNo) && !"1234567890".equals(batchNo)){
					batchList.add(batchNo);
				}
			}
			
			//不存在批号则退出
			if(batchList.size() == 0){
				return;
			}
			
			//根据整体批次号查产品资料
			filter.clear();
			filter.put("batchList", batchList);
			List<Map<String,Object>> data2 = service.getProductFromUploadProduceReport(filter, dsn);
			if(data2 !=null && data2.size()>0){
				for(Map<String, Object> map: data2){
					String batch = String.valueOf(map.get("BATCHNO"));
					if(StringUtils.isNotBlank(batch)){
						result.put(batch, map);
					}
				}
			}
			
			//从Upload_produce_report_Item 根据当天的所有生产上传的BatchhNo查询对应的Item数量、工厂代码、线号、所属BU  生产实际上传生产数据
			filter.clear();
			filter.put("batchList", batchList);
			filter.put("date", date);
			List<Map<String,Object>> data3 = service.getInfoFromUploadProduceReportItem(filter, dsn);
			if(data3 !=null && data3.size()>0){
				for(Map<String, Object> map: data3){
					String batchNo = String.valueOf(map.get("BATCHNO"));
					if(result.containsKey(batchNo)){
						result.get(batchNo).putAll(map);
					}
				}
			}
			
			// 根据当天的所有生产上传的BatchhNo查询对应的Item数量、工厂代码、线号、所属BU  生产实际上传生产数据
			List<Map<String,Object>> data4 = service.getInfoFromFactoryDataContranst(filter, dsn);
			if(data4 !=null && data4.size()>0){
				for(Map<String, Object> map: data4){
					String obj = String.valueOf(map.get("FPCBATCH"));
					if(result.containsKey(obj)){
						result.get(obj).putAll(map);
					}
				}
			}
			
			//从Upload_produce_report 根据当天的所有生产上传的BatchhNo查询对应的Case数量、工厂代码、线号、所属BU
			List<Map<String,Object>> data5 = service.getData5List(filter, dsn);
			if(data5 !=null && data5.size()>0){
				for(Map<String, Object> map: data5){
					String obj = String.valueOf(map.get("BATCHNO"));
					if(result.containsKey(obj)){
						result.get(obj).putAll(map);
					}
				}
			}
			
			//从FACTORY_DATA_CONTRAS 根据当天查询的所有生产上传的BatchhNo查询对应的Case数据
			List<Map<String,Object>> data6 = service.getData6List(filter, dsn);
			if(data6 !=null && data6.size()>0){
				for(Map<String, Object> map: data6){
					String obj = String.valueOf(map.get("FPCBATCH"));
					if(result.containsKey(obj)){
						result.get(obj).putAll(map);
					}
				}
			}
		}
		
		String value = "";
		int  k =1;
		for(Map<String,Object> map : result.values()){
			long kn=0,hn=0,pn=0,mn=0;
			String sql = "insert into dayreport values('"+date+"',";
			
			value = String.valueOf(map.get("PLANTCODE"));//工厂
			value = value==null || value.equals("null")? "":value;
			sql = sql +"'"+value+"',";
			
			value = String.valueOf(map.get("LINECODE"));//线号
			value = value==null || value.equals("null")? "":value;
			sql = sql +"'"+value+"',";
			
			value = String.valueOf(map.get("SORTNAME")); //BU
			value = value==null || value.equals("null")? "":value;
			sql = sql +"'"+value+"',";
			
			value = String.valueOf(map.get("BATCHNO"));//批次
			value = value==null || value.equals("null")? "":value;
			sql = sql +"'"+value+"',";
			
			value = String.valueOf(map.get("PROTYPE"));//是否为一瓶一码
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			
			
			value = String.valueOf(map.get("MCODE"));//产品代码
			value = value==null || value.equals("null")? "":value;
			sql = sql +"'"+value+"',";
			
			value = String.valueOf(map.get("MATERICALCHDES"));//产品名称
			value = value==null || value.equals("null")? "":value;
			sql = sql +"'"+value+"',";
			
			value = String.valueOf(map.get("CKQRNUM1"));//生产计划item数量
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			if(value.length()>0){
				hn = Long.valueOf(value);//计划生产item数量
			}
			
			value = String.valueOf(map.get("ELQRNUM1"));//item剔除数量
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			
			value = String.valueOf(map.get("SCANNUM1"));//item本地数量
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			
			value = String.valueOf(map.get("COUNT1"));//实际item数量
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			if(value.length()>0){
				kn = Long.valueOf(value);//实际item数量
			}
			
//			value = String.valueOf(map.get("BATCHNO"));//实际收到item数量
			sql = sql +"null,";
			
			value = String.valueOf(map.get("CKQRNUM2"));//生产计划case数量
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			if(value.length()>0){
				mn = Long.valueOf(value);//计划生产case数量
			}
			
			value = String.valueOf(map.get("ELQRNUM2"));//case剔除数量
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			
			value = String.valueOf(map.get("SCANNUM2"));//case本地数量
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			
			value = String.valueOf(map.get("COUNT2"));//实际case数量
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			if(value.length()>0){
				pn = Long.valueOf(value);//实际case数量
			}
			
//						value = String.valueOf(map.get("BATCHNO"));//实际收到case数量
			sql = sql +"null,";
			
//						//item合格率K/H
			if(kn >0 && hn>0){
				 BigDecimal bg = new BigDecimal((double)kn/hn);
		         value = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()*100 +"";
			}else{
				value = "";
			}
			sql = sql +(value.equals("")? "null":value)+",";
			
//						//case合格率P/M
			if(pn >0 && mn>0){
				 BigDecimal bg = new BigDecimal((double)pn/mn);
		         value = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()*100 +"";
			}else{
				value = "";
			}
			sql = sql +(value.equals("")? "null":value)+",";
			
//						//实际箱瓶比例K/p
			if(kn >0 && pn>0){
				value =new BigDecimal((double)kn/pn).setScale(0, BigDecimal.ROUND_HALF_UP) +"";
			}else{
				value = "";
			}
			sql = sql +(value.equals("")? "null":value)+",";
			String t1 = value;
			
			value = String.valueOf(map.get("CASEPACKAGE"));//masterdata中箱瓶比例
			value = value==null || value.equals("null")? "":value;
			sql = sql +(value.equals("")? "null":value)+",";
			String t2 = value;
			
			String case_item = String.valueOf(map.get("ITEMCASECONNECTION"));
			if(case_item == null || case_item.equals("")){
				case_item = "N";
			}
			//比例是否标准
			if(case_item.equals("Y")){
				if(t1 != null && t2 != null && t1.equals(t2)){
					value = "Y";
				}else{
					value = "N";
				}
			}else{
				value = "Y";
			}
			sql = sql +"'"+value+"','"+case_item+"')";
			System.out.println(sql);
			filter.clear();
			filter.put("sql", sql);
			service.insertListBySql(filter, new DataSourceName("primary"));
			value = "";
			k++;
		}
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
}
