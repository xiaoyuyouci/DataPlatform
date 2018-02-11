package com.winsafe.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.DcDailyService;
import com.winsafe.service.DcRealtimeService;
import com.winsafe.service.FactoryDailyService;
import com.winsafe.service.FactoryRealtimeService;
import com.winsafe.utils.AjaxUtil;
import com.winsafe.utils.DatatablePage;
import com.winsafe.utils.DatatablePageHelper;
import com.winsafe.utils.DatatableViewPage;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private FactoryRealtimeService fcRealtimeService;
	
	@Autowired
	private FactoryDailyService fcDailyService;
	
	@Autowired
	private DcRealtimeService dcRealtimeService;
	
	@Autowired
	private DcDailyService dcDailyService;
	
	/**
	 * 工厂实时状态表格
	 * @return
	 */
	@RequestMapping("/fcRealtimeGrid")
	public String toFcRealtimeGrid() {
		return "report/fcRealtimeGrid"; 
	}
	
	/**
	 * 获取工厂实时状态表格数据
	 * @param record
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxGetFcRealtimeGrid")
	public void ajaxGetFcRealtimeGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bu", request.getParameter("bu"));
		map.put("status", request.getParameter("status"));
		map.put("linecode", request.getParameter("linecode"));
		List<Map<String, Object>> list = fcRealtimeService.getFcRealtimeData(map, new DataSourceName("db3"));
		
		String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue);
		
		AjaxUtil.ajaxReturn(val, response);
	}
	
	/**
	 * 工厂每日状态表格
	 * @return
	 */
	@RequestMapping("/fcDailyGrid")
	public String toFcDailyGrid() {
		return "report/fcDailyGrid"; 
	}
	
	/**
	 * 获取工厂每日状态表格数据
	 * @param record
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxGetFcDailyGrid")
	public void ajaxGetFcDailyGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bu", request.getParameter("bu"));
		map.put("date", request.getParameter("date"));
		map.put("lineCode", request.getParameter("lineCode"));
		map.put("batchNo", request.getParameter("batchNo"));
		map.put("plantCode", request.getParameter("plantCode"));
		map.put("mCode", request.getParameter("mCode"));
		List<Map<String, Object>> list = fcDailyService.getFcDailyData(map, new DataSourceName("db3"));
		
		String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue);
		
		AjaxUtil.ajaxReturn(val, response);
	}
	
	/**
	 * DC实时状态表格
	 * @return
	 */
	@RequestMapping("/dcRealtimeGrid")
	public String toDcRealtimeGrid() {
		return "report/dcRealtimeGrid"; 
	}
	
	/**
	 * 获取DC实时状态表格数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxGetDcRealtimeGrid")
	public void ajaxGetDcRealtimeGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = dcRealtimeService.getDcRealtimeData(map, new DataSourceName("db2"));
		
		String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue);
		
		AjaxUtil.ajaxReturn(val, response);
	}
	
	/**
	 * DC每日状态表格
	 * @return
	 */
	@RequestMapping("/dcDailyGrid")
	public String toDcDailyGrid() {
		return "report/dcDailyGrid"; 
	}
	
	/**
	 * 获取DC每日状态表格数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxGetDcDailyGrid")
	public void ajaxGetDcDailyGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ncCode", request.getParameter("ncCode"));
		map.put("startDate", request.getParameter("startDate"));
		map.put("endDate", request.getParameter("endDate"));
		map.put("isAudit", request.getParameter("isAudit"));
		map.put("oid", request.getParameter("oid"));
		map.put("busNo", request.getParameter("busNo"));
		map.put("isBlankout", request.getParameter("isBlankout"));
		map.put("receiveNo", request.getParameter("receiveNo"));
		map.put("deliveryNo", request.getParameter("deliveryNo"));
		map.put("needScan", request.getParameter("needScan"));
		map.put("keyWord", request.getParameter("keyWord"));
		List<Map<String, Object>> list = dcDailyService.getDcDailyData(map, new DataSourceName("db2"));
		
		SerializeConfig serializeConfig = new SerializeConfig();
		serializeConfig.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		serializeConfig.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		
		String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), serializeConfig, SerializerFeature.WriteMapNullValue);
		
		AjaxUtil.ajaxReturn(val, response);
	}
	
	/**
	 * UID查询表格
	 * @return
	 */
	@RequestMapping("/uidGrid")
	public String toUidGrid() {
		return "report/uidGrid"; 
	}
	
	/**
	 * 获取UID查询表格数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxGetUidGrid")
	public void ajaxGetUidGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
		Map<String, Object> map = new HashMap<String, Object>();
		
		String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue);
		
		AjaxUtil.ajaxReturn(val, response);
	}
	
	/**
	 * 包材厂数据统计表格
	 * @return
	 */
	@RequestMapping("/ppStatisticsGrid")
	public String toPpStatisticsGrid() {
		return "report/ppStatisticsGrid"; 
	}
	
	/**
	 * 获取包材厂数据统计表格数据
	 * @param record
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxGetPpStatisticsGrid")
	public void ajaxGetPpStatisticsGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
		Map<String, Object> map = new HashMap<String, Object>();
		
		String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue);
		
		AjaxUtil.ajaxReturn(val, response);
	}
}
