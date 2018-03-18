package com.winsafe.schedule;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winsafe.model.ScheduleJob;
import com.winsafe.service.ScheduleJobService;

import junit.framework.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FcScheduleTest {

	@Autowired
	private Dc_FcDailySchedule fcSchedule;
	
	@Autowired
	private Dc_FcRealtimeSchedule fcRealtimeSchedule;
	
	@Autowired
	private Dc_UpdateBnoTmpSchedule updateBnoTmpSchedule;
	
	@Autowired
	private Dc_QrCodeUsageRatioSchedule dcQrCodeUsageRatioSchedule;
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	//@Test
    public void test1(){
		fcSchedule.factoryDailyData();
	}
	
	//@Test
    public void test2(){
		fcRealtimeSchedule.updateRealtime();
	}
	
	//@Test
	public void test3(){
		updateBnoTmpSchedule.updateBnoTmp();
	}
	
	//@Test
	public void test4(){
		dcQrCodeUsageRatioSchedule.execute();
	}
	
	//@Test
	public void test5(){
		ScheduleJob record = new ScheduleJob();
		List<ScheduleJob> list = scheduleJobService.selectScheduleJobList(record);
		int size = list.size();
		
		ScheduleJob newOne = new ScheduleJob();
		newOne.setBeanClass("setBeanClass");
		newOne.setCronExpression("this cron");
		newOne.setDescription("setDescription");
		newOne.setIsConcurrent("setIsConcurrent");
		newOne.setJobGroup("setJobGroup");
		newOne.setJobName("setJobName");
		newOne.setJobStatus("setJobStatus");
		newOne.setMethodName("setMethodName");
		newOne.setSpringId("setSpringId");
		scheduleJobService.insert(newOne);
		
		list = scheduleJobService.selectScheduleJobList(new ScheduleJob());
		
		Assert.assertEquals(size+1, list.size());
		
		newOne.setBeanClass("setBeanClass2");
		newOne.setCronExpression("this cron2");
		newOne.setDescription("setDescription2");
		newOne.setIsConcurrent("setIsConcurrent2");
		newOne.setJobGroup("setJobGroup2");
		newOne.setJobName("setJobName2");
		newOne.setJobStatus("setJobStatus2");
		newOne.setMethodName("setMethodName2");
		newOne.setSpringId("setSpringId2");
		int i = scheduleJobService.updateByPrimaryKey(newOne);
		
		ScheduleJob sj2 = scheduleJobService.selectByPrimaryKey(newOne.getId());
		Assert.assertEquals(newOne.getBeanClass(), sj2.getBeanClass());
		
		scheduleJobService.deleteByPrimaryKey(sj2.getId());
		
		list = scheduleJobService.selectScheduleJobList(new ScheduleJob());
		
		Assert.assertEquals(size, list.size());
	}
}
