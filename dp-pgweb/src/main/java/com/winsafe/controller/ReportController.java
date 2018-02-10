package com.winsafe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winsafe.service.FactoryRealtimeService;
import com.winsafe.utils.AjaxUtil;
import com.winsafe.utils.DatatablePage;
import com.winsafe.utils.DatatablePageHelper;
import com.winsafe.utils.DatatableViewPage;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private FactoryRealtimeService fcRealtimeService;
	
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
		//fcRealtimeService.getFcRealtimeData(null, "db2");
		JSONObject jsonObject = JSONObject.fromObject(new DatatableViewPage(true, "数据查询成功！", dPage));
		
		/*JSONArray columns = new JSONArray();
		JSONObject c = new JSONObject();
		c.put("data", "name");
		c.put("title", "姓名");
		columns.add(c);
		
		c = new JSONObject();
		c.put("data", "age");
		c.put("title", "年龄");
		columns.add(c);
		
		jsonObject.put("columns", columns);
		
		JSONArray rows = new JSONArray();
		c = new JSONObject();
		c.put("name", "张三");
		c.put("age", 13);
		rows.add(c);
		
		c = new JSONObject();
		c.put("name", "李四");
		c.put("age", 14);
		rows.add(c);
		
		jsonObject.put("rows", rows);*/
		
		AjaxUtil.ajaxReturn(jsonObject, response);
	}
}
