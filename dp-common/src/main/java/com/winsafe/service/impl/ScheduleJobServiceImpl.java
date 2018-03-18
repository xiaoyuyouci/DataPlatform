package com.winsafe.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsafe.dao.ScheduleJobMapper;
import com.winsafe.model.Resource;
import com.winsafe.model.ScheduleJob;
import com.winsafe.service.QuartzJobFactory;
import com.winsafe.service.QuartzJobFactoryDisallowConcurrentExecution;
import com.winsafe.service.ResourceService;
import com.winsafe.service.ScheduleJobService;
import com.winsafe.utils.SpringContextUtil;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
	@Autowired
    private ScheduleJobMapper scheduleJobMapper;

	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private ResourceService resourceService;
	
	@Override
	public List<ScheduleJob> selectScheduleJobList(ScheduleJob record) {
		return scheduleJobMapper.selectScheduleJobList(record);
	}

	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		return scheduleJobMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int insert(ScheduleJob record) {
		return scheduleJobMapper.insert(record);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(ScheduleJob record) {
		return scheduleJobMapper.updateByPrimaryKey(record);
	}

	@Override
	public ScheduleJob selectByPrimaryKey(Integer id) {
		return scheduleJobMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 更改任务状态
	 * @throws Exception 
	 */
	public void changeStatus(Integer id, String cmd) throws Exception {
		ScheduleJob job = selectByPrimaryKey(id);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteJob(job);
			job.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			addJob(job);
		}
		scheduleJobMapper.updateByPrimaryKey(job);
	}

	/**
	 * 更改任务 cron表达式
	 * @throws Exception 
	 */
	public void updateCron(Integer id, String cron) throws Exception {
		ScheduleJob job = selectByPrimaryKey(id);
		if (job == null) {
			return;
		}
		job.setCronExpression(cron);
		//if (ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
		if (isScheduleJobRunningInMemory(job)) {
			updateJobCron(job);
		}
		scheduleJobMapper.updateByPrimaryKey(job);

	}

	public boolean isScheduleJobRunningInMemory(ScheduleJob job) throws SchedulerException{
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		return trigger != null;
	}
	
	public void updJobStatusOfRunningInMemory(List<ScheduleJob> list) throws SchedulerException{
		for(ScheduleJob job: list){
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if(trigger == null){
				job.setJobStatusInMemory("0");
			}
			else{
				job.setJobStatusInMemory("1");
			}
		}
	}
	
	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return;
		}

		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			trigger.getJobDataMap().put("scheduleJob", job);
			
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}
	
	@PostConstruct
	public void init() throws Exception {
		Resource r = resourceService.selectBySnameAndSkey("scheduleJob", "runOnStart");
		if(r != null && StringUtils.isNotBlank(r.getSvalue()) && "1".equals(r.getSvalue().trim()) ){
			// 这里获取任务信息数据
			ScheduleJob model = new ScheduleJob();
			model.setJobStatus("1");
			List<ScheduleJob> jobList = scheduleJobMapper.selectScheduleJobList(model);
			
			for (ScheduleJob job : jobList) {
				addJob(job);
			}
		}
	}

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}
	
	/**
	 * 验证ScheduleJob的cron表达式是否有效，springId是否存在，beanClass是否存在，methodName是否存在
	 * @param job
	 * @return
	 */
	public List<Map<String, String>> validateScheduleJob(ScheduleJob job) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// 验证表达式
		if (StringUtils.isEmpty(job.getCronExpression())) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("element", "cronExpression");
			map.put("msg", "Cron表达式为空！");
			list.add(map);
		}
		job.setCronExpression(job.getCronExpression().trim());
		try {
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("element", "cronExpression");
			map.put("msg", "Cron表达式有误，不能被解析！");
			list.add(map);
		}

		Class<?> clazz = null;
		Method method = null;
		//如果springId不空，从springId获得bean，再获得bean的类
		if (!StringUtils.isEmpty(job.getSpringId())) {
			job.setSpringId(job.getSpringId().trim());
			try {
				Object bean = SpringContextUtil.getBean(job.getSpringId());
				clazz = bean.getClass();
			} catch (Exception e) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("element", "springId");
				map.put("msg", "SpringId不存在！");
				list.add(map);
			}
		}
		//如果springId不空，从beanClass获得类。此时beanClass为必填项
		else {
			// 验证类名
			if (StringUtils.isBlank(job.getBeanClass())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("element", "beanClass");
				map.put("msg", "执行类名为空！");
				list.add(map);
			}
			else{
				job.setBeanClass(job.getBeanClass().trim());
				
				try {
					clazz = Class.forName(job.getBeanClass());
				} catch (Exception e) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("element", "beanClass");
					map.put("msg", "类" + job.getBeanClass() + "不存在");
					list.add(map);
				}
			}
		}
		
		//如果类存在
		if(clazz != null){
			//验证类中是否有该方法
			if (StringUtils.isEmpty(job.getMethodName())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("element", "methodName");
				map.put("msg", "方法名为空！");
				list.add(map);
			}
			job.setMethodName(job.getMethodName().trim());
			try {
				method = clazz.getMethod(job.getMethodName(), null);
			} catch (Exception e) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("element", "methodName");
				map.put("msg", "类" + clazz.getCanonicalName() + "中没有方法" + job.getMethodName());
				list.add(map);
			}
		}
		return list;
	}
}
