package com.winsafe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.winsafe.model.ScheduleJob;

@Mapper
public interface ScheduleJobMapper {
	List<ScheduleJob> selectScheduleJobList(ScheduleJob record);
	ScheduleJob selectByPrimaryKey(Integer id);
	int deleteByPrimaryKey(Integer id);
	int insert(ScheduleJob record);
	int updateByPrimaryKey(ScheduleJob record);
}