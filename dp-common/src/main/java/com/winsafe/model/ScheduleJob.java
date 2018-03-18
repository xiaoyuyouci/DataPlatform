package com.winsafe.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务
 * @author Ryan
 *
 */
public class ScheduleJob implements Serializable {

	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	
	private Integer id;
	
	private Integer creator;

    private Date creationTime;

    private Integer modifier;

    private Date modificationTime;
    /**
	 * 任务名称
	 */
    private String jobName;
    /**
	 * 任务分组
	 */
    private String jobGroup;

    /**
	 * 任务状态 是否启动任务。1：是，0：否
	 */
    private String jobStatus;
    /**
	 * cron表达式
	 */
    private String cronExpression;

    private String description;
    /**
	 * 任务执行时调用哪个类的方法 包名+类名
	 */
    private String beanClass;

    /**
     * 是否同时存在JobDetail。1：是， 0： 否
     */
    private String isConcurrent;
    /**
	 * spring bean
	 */
    private String springId;
    /**
	 * 任务调用的方法名
	 */
    private String methodName;

    private String jobStatusInMemory;//在内存中的运行状态

    
    private String searchVal;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public String getSpringId() {
		return springId;
	}

	public void setSpringId(String springId) {
		this.springId = springId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getJobStatusInMemory() {
		return jobStatusInMemory;
	}

	public void setJobStatusInMemory(String jobStatusInMemory) {
		this.jobStatusInMemory = jobStatusInMemory;
	}

	public String getSearchVal() {
		return searchVal;
	}

	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}

}