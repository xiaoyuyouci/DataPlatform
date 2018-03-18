package com.winsafe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.winsafe.model.ScheduleJob;
import com.winsafe.service.ScheduleJobService;
import com.winsafe.utils.AjaxUtil;
import com.winsafe.utils.BaseResult;
import com.winsafe.utils.DatatablePage;
import com.winsafe.utils.DatatablePageHelper;
import com.winsafe.utils.DatatableViewPage;


@Controller
@RequestMapping("/scheduleJob")
public class ScheduleJobController {
	
	public final Logger logger = LogManager.getLogger(ScheduleJobController.class);
	
	@Resource
	private ScheduleJobService scheduleJobService; 
	
	@RequestMapping("/listScheduleJob")
	public String toListScheduleJob(HttpServletRequest request) throws Exception {
		return "scheduleJob/listScheduleJob";
	}

	@RequestMapping(value="/ajaxGetScheduleJob")
	public void ajaxGetScheduleJob(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPage(request);
		ScheduleJob record = new ScheduleJob();
		record.setSearchVal(dPage.getSearchValue());
		List<ScheduleJob> list = scheduleJobService.selectScheduleJobList(new ScheduleJob());
		
		scheduleJobService.updJobStatusOfRunningInMemory(list);
 		
		AjaxUtil.ajaxReturn(JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue), response);
	}
	
	@RequestMapping(value="/toAddScheduleJob")
	public String toAddScheduleJob(HttpServletRequest request) throws Exception{
		return "scheduleJob/addScheduleJob";
	}
	
	@RequestMapping(value="/ajaxAddScheduleJob")
	public void ajaxAddScheduleJob(ScheduleJob obj, HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Map<String, String>> result = scheduleJobService.validateScheduleJob(obj);
		if(!result.isEmpty()){
			AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, JSON.toJSONString(result), null), SerializerFeature.WriteMapNullValue), response);
			return;
		}
		
		ScheduleJob model = new ScheduleJob();
		model.setJobGroup(obj.getJobGroup());
		model.setJobName(obj.getJobName());
		List<ScheduleJob> oldList = scheduleJobService.selectScheduleJobList(model);
		if(oldList != null && oldList.size() > 0){
			Map<String, String> map = new HashMap<String, String>();
			map.put("element", "jobName");
			map.put("msg", "定时任务名称已存在当前组！");
			result.add(map);
			AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, JSON.toJSONString(result), null), SerializerFeature.WriteMapNullValue), response);
			return;
		}
		
		
		//如果有springId,验证SpringId与方法是否已存在
		if(!StringUtils.isEmpty(obj.getSpringId())){
			model = new ScheduleJob();
			model.setSpringId(obj.getSpringId());
			model.setMethodName(obj.getMethodName());
			oldList = scheduleJobService.selectScheduleJobList(model);
			if (oldList != null && oldList.size() > 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("element", "methodName");
				map.put("msg", "SpringId与方法名已存在定时任务表中！");
				result.add(map);
				AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, JSON.toJSONString(result), null), SerializerFeature.WriteMapNullValue), response);
				return;
			}
		}
		
		//如果有beanClass,验证beanClass与方法是否已存在
		if (!StringUtils.isEmpty(obj.getBeanClass())) {
			model = new ScheduleJob();
			model.setSpringId(obj.getBeanClass());
			model.setMethodName(obj.getMethodName());
			oldList = scheduleJobService.selectScheduleJobList(model);
			if (oldList != null && oldList.size() > 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("element", "methodName");
				map.put("msg", "BeanClass与方法名已存在定时任务表中！");
				result.add(map);
				AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, JSON.toJSONString(result), null), SerializerFeature.WriteMapNullValue), response);
				return;
			}
		}
		
		obj.setJobStatus("0");
		scheduleJobService.insert(obj);
		
		scheduleJobService.addJob(obj);
		
		AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(true, "定时任务新增成功！", null), SerializerFeature.WriteMapNullValue), response);
	}
	
	@RequestMapping(value="/toUpdScheduleJob")
	public String toUpdScheduleJob(HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		ScheduleJob obj = scheduleJobService.selectByPrimaryKey(Integer.valueOf(id));
		request.setAttribute("obj", obj);
		return "scheduleJob/updScheduleJob";
	}
	
	@RequestMapping(value="/ajaxUpdScheduleJob")
	public void ajaxUpdScheduleJob(ScheduleJob obj, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ScheduleJob old = scheduleJobService.selectByPrimaryKey(obj.getId());
		if(!old.getJobGroup().equals(obj.getJobGroup()) || !old.getJobName().equals(obj.getJobName())){
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("element", "jobName");
			map.put("msg", "定时任务组与名称不允许修改！");
			result.add(map);
			AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, JSON.toJSONString(result), null), SerializerFeature.WriteMapNullValue), response);
			return;
		}
		
		//验证定时任务名称是否已存在当前组
		/*
		ScheduleJob model = new ScheduleJob();
		model.setJobGroup(obj.getJobGroup());
		model.setJobName(obj.getJobName());
		List<ScheduleJob> oldList = commonService.selectList(model);
		if(oldList != null && oldList.size() > 0){
			for(ScheduleJob s: oldList){
				if(!s.getCode().equals(obj.getCode())){
					JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "定时任务名称已存在当前组！", null));
					AjaxUtil.ajaxReturn(jsonObject, response);
					return;
				}
			}
		}
		*/
		
		//验证ScheduleJob的cron表达式是否有效，springId是否存在，beanClass是否存在，methodName是否存在
		List<Map<String, String>> result = scheduleJobService.validateScheduleJob(obj);
		if(!result.isEmpty()){
			AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, JSON.toJSONString(result), null), SerializerFeature.WriteMapNullValue), response);
			return;
		}
		
		//如果有springId,验证SpringId与方法是否已存在
		if(!StringUtils.isEmpty(obj.getSpringId())){
			ScheduleJob model = new ScheduleJob();
			model.setSpringId(obj.getSpringId());
			model.setMethodName(obj.getMethodName());
			List<ScheduleJob> oldList = scheduleJobService.selectScheduleJobList(model);
			if (oldList != null && oldList.size() > 0) {
				for(ScheduleJob s: oldList){
					if(!s.getId().equals(obj.getId())){
						Map<String, String> map = new HashMap<String, String>();
						map.put("element", "methodName");
						map.put("msg", "SpringId与方法名已存在定时任务表中！");
						result.add(map);
						AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, JSON.toJSONString(result), null), SerializerFeature.WriteMapNullValue), response);
						return;
					}
				}
			}
		}
				
		// 如果有beanClass,验证beanClass与方法是否已存在
		if (!StringUtils.isEmpty(obj.getBeanClass())) {
			ScheduleJob model = new ScheduleJob();
			model.setSpringId(obj.getBeanClass());
			model.setMethodName(obj.getMethodName());
			List<ScheduleJob> oldList = scheduleJobService.selectScheduleJobList(model);
			if (oldList != null && oldList.size() > 0) {
				for (ScheduleJob s : oldList) {
					if (!s.getId().equals(obj.getId())) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("element", "methodName");
						map.put("msg", "BeanClass与方法名已存在定时任务表中！");
						result.add(map);
						AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, JSON.toJSONString(result), null), SerializerFeature.WriteMapNullValue), response);
						return;
					}
				}
			}
		}
		
		boolean isCronExpressionChanged = !old.getCronExpression().equalsIgnoreCase(obj.getCronExpression());
		old.setSpringId(obj.getSpringId());
		old.setBeanClass(obj.getBeanClass());
		old.setDescription(obj.getDescription());
		old.setIsConcurrent(obj.getIsConcurrent());
		//old.setJobGroup(obj.getJobGroup());
		//old.setJobName(obj.getJobName());
		old.setMethodName(obj.getMethodName());
		scheduleJobService.updateByPrimaryKey(old);
		//如果cron表达式变了，需要重新设置定时任务的触发时间
		if(isCronExpressionChanged){
			scheduleJobService.updateCron(obj.getId(), obj.getCronExpression());
		}
		
		AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(true, "定时任务更新成功！", null), SerializerFeature.WriteMapNullValue), response);
	}
	
	@RequestMapping(value="/ajaxDelScheduleJob")
	public void ajaxDelScheduleJob(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		ScheduleJob obj = scheduleJobService.selectByPrimaryKey(Integer.valueOf(id));
		if(obj == null){
			AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, "定时任务不存在", null), SerializerFeature.WriteMapNullValue), response);
			return;
		}
		//关闭定时任务
		scheduleJobService.deleteJob(obj);
		
		//从数据库删除定时任务
		scheduleJobService.deleteByPrimaryKey(obj.getId());
		
		AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(true, "删除定时任务！", null), SerializerFeature.WriteMapNullValue), response);
	}
	
	@RequestMapping("/ajaxChangeScheduleJobStatus")
	public void ajaxChangeScheduleJobStatus(HttpServletRequest request, HttpServletResponse response, Integer id, String cmd) throws Exception {
		try {
			scheduleJobService.changeStatus(id, cmd);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(false, "任务状态改变失败！", null), SerializerFeature.WriteMapNullValue), response);
			return;
		} 
		AjaxUtil.ajaxReturn(JSON.toJSONString(new BaseResult(true, "任务状态改变成功！", null), SerializerFeature.WriteMapNullValue), response);
		return;
	}
}
