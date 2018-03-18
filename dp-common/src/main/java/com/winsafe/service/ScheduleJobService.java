package com.winsafe.service;

import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;

import com.winsafe.model.ScheduleJob;

public interface ScheduleJobService {
	/**
	 * 从数据库查询ScheduleJob列表
	 * @param record
	 * @return
	 */
	List<ScheduleJob> selectScheduleJobList(ScheduleJob record);
	
	/**
	 * 根据ID从数据库查询ScheduleJob
	 * @param id
	 * @return
	 */
	ScheduleJob selectByPrimaryKey(Integer id);
	
	/**
	 * 从数据看删除ScheduleJob
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Integer id);
	
	/**
	 * 插入ScheduleJob到数据库
	 * @param record
	 * @return
	 */
	int insert(ScheduleJob record);
	
	/**
	 * 更新ScheduleJob到数据库
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(ScheduleJob record);
	
	public void updJobStatusOfRunningInMemory(List<ScheduleJob> list) throws SchedulerException;
	public void addJob(ScheduleJob job) throws SchedulerException;
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;
	public void updateCron(Integer id, String cron) throws Exception;
	public void changeStatus(Integer id, String cmd) throws Exception;
	/**
	 * 验证ScheduleJob的cron表达式是否有效，springId是否存在，beanClass是否存在，methodName是否存在
	 * @param job
	 * @return
	 */
	public List<Map<String, String>> validateScheduleJob(ScheduleJob job);
}
