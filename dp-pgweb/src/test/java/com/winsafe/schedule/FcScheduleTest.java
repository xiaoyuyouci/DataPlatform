package com.winsafe.schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FcScheduleTest {

	@Autowired
	private FcDailySchedule fcSchedule;
	
	@Autowired
	private FcRealtimeSchedule fcRealtimeSchedule;
	
	@Autowired
	private UpdateBnoTmpSchedule updateBnoTmpSchedule;
	
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
}
