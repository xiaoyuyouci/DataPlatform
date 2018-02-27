package com.winsafe.controller;

import java.io.OutputStream;
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
import com.winsafe.service.PpStatisticsService;
import com.winsafe.service.UidService;
import com.winsafe.utils.AjaxUtil;
import com.winsafe.utils.BaseResult;
import com.winsafe.utils.DatatablePage;
import com.winsafe.utils.DatatablePageHelper;
import com.winsafe.utils.DatatableViewPage;
import com.winsafe.utils.DateUtil;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

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
	
	@Autowired
	private UidService uidService;
	
	@Autowired
	private PpStatisticsService ppStatisticsService;
	
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
	
	@RequestMapping(value="/ajaxExportFcRealtimeGrid")
	public void ajaxExportFcRealtimeGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bu", request.getParameter("bu"));
		map.put("status", request.getParameter("status"));
		map.put("linecode", request.getParameter("linecode"));
		List<Map<String, Object>> list = fcRealtimeService.getFcRealtimeData(map, new DataSourceName("db3"));
		
		try {
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="+new String("工厂实时状态.xls".getBytes(),"ISO8859-1"));
			response.setContentType("application/msexcel");
			writeFcRealtimeGridXls(list, os);
			os.flush();
			os.close();
		} catch (Exception e) {
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "文件导出失败"));
			AjaxUtil.ajaxReturn(jsonObject,response);
			return;
		}
		
	}
	
	private void writeFcRealtimeGridXls(List<Map<String, Object>> list, OutputStream os) throws Exception {

		WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		wcfFC.setBackground(Colour.GREY_25_PERCENT);
		
		WritableFont wfct = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wchT = new WritableCellFormat(wfct);
		wchT.setAlignment(Alignment.CENTRE);
		
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;
			
			sheets[j].mergeCells(0, start, 24, start);
			sheets[j].addCell(new Label(0, start, "工厂实时状态",wchT)); 
			int rowIndex = 0;
			sheets[j].addCell(new Label(rowIndex++, start+1, "事业部",wcfFC)); 
			sheets[j].addCell(new Label(rowIndex++, start+1, "工厂代码", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "线号", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "当前批次", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "FPC", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "产品名称", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "当前状态", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "上 传 时 间", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "箱瓶是否关联", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case Count", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 打印机回传数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 扫描数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 剔除数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case IPC本地数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 后台系统数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case Context接收数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 生产合格率", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 相机扫描数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 相机回传数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 剔除数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item IPC本地数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 后台系统数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item Context接收数量", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 生产合格率", wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "是否符合Case Count", wcfFC));
			
			int row = 0;
			Map<String, Object> lineMap = null;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 2;
				lineMap = list.get(i);
				rowIndex = 0;
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("bu"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("plantcode"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("linecode"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("batchno"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("mcode"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("productname"))));
				sheets[j].addCell(new Label(rowIndex++, row, "1".equals(getVal(lineMap.get("status")))? "生 产":"暂 停"));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("up_time"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("case_item"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("case_package"))));
				sheets[j].addCell(new Label(rowIndex++, row, ""));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("ckqrnum2"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("elqrnum2"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("scannum2"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("count2"))));
				sheets[j].addCell(new Label(rowIndex++, row, ""));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("case_percent"))));
				sheets[j].addCell(new Label(rowIndex++, row, ""));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("ckqrnum1"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("elqrnum1"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("scannum1"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("count1"))));
				sheets[j].addCell(new Label(rowIndex++, row, ""));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("item_percent"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("istrue"))));
			}
		}
		
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private String getVal(Object obj){
		String val = null;
		if(obj == null){
			return null;
		}
		else if(obj instanceof Date){
			val = DateUtil.formatDate((Date)obj);
		}
		else{
			val = String.valueOf(obj);
		}
		return val;
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
	
	@RequestMapping(value="/ajaxExportFcDailyGrid")
	public void ajaxExportFcDailyGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bu", request.getParameter("bu"));
		map.put("date", request.getParameter("date"));
		map.put("lineCode", request.getParameter("lineCode"));
		map.put("batchNo", request.getParameter("batchNo"));
		map.put("plantCode", request.getParameter("plantCode"));
		map.put("mCode", request.getParameter("mCode"));
		List<Map<String, Object>> list = fcDailyService.getFcDailyData(map, new DataSourceName("db3"));
		
		try {
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="+new String("工厂每日状态.xls".getBytes(),"ISO8859-1"));
			response.setContentType("application/msexcel");
			writeFcDailyGridXls(list, os);
			os.flush();
			os.close();
		} catch (Exception e) {
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "文件导出失败"));
			AjaxUtil.ajaxReturn(jsonObject,response);
			return;
		}
		
	}
	
	private void writeFcDailyGridXls(List<Map<String, Object>> list, OutputStream os) throws Exception {

		WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		wcfFC.setBackground(Colour.GREY_25_PERCENT);
		
		WritableFont wfct = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wchT = new WritableCellFormat(wfct);
		wchT.setAlignment(Alignment.CENTRE);
		
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;
			
			sheets[j].mergeCells(0, start, 24, start);
			sheets[j].addCell(new Label(0, start, "工厂实时状态",wchT)); 
			int rowIndex = 0;
			sheets[j].addCell(new Label(rowIndex++, start+1, "事业部",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "工厂代码",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "线号",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "当前批次",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "FPC",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "产品名称",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "箱瓶是否关联",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case Count",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 打印机回传数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 扫描数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 剔除数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case IPC本地数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 后台系统数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case Context接收数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Case 生产合格率",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 相机扫描数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 相机回传数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 剔除数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item IPC本地数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 后台系统数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item Context接收数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Item 生产合格率",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "是否符合Case Count",wcfFC));
						
			int row = 0;
			Map<String, Object> lineMap = null;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 2;
				lineMap = list.get(i);
				rowIndex = 0;
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("bu"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("plantcode"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("linecode"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("batchno"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("mcode"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("productname"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("case_item"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("case_package"))));
				sheets[j].addCell(new Label(rowIndex++, row, ""));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("ckqrnum2"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("elqrnum2"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("scannum2"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("count2"))));
				sheets[j].addCell(new Label(rowIndex++, row, ""));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("case_percent"))));
				sheets[j].addCell(new Label(rowIndex++, row, ""));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("ckqrnum1"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("elqrnum1"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("scannum1"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("count1"))));
				sheets[j].addCell(new Label(rowIndex++, row, ""));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("item_percent"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("istrue"))));
			}
		}
		
		workbook.write();
		workbook.close();
		os.close();
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
		
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
		List<Map<String, Object>> list = dcDailyService.getDcDailyData(map, new DataSourceName("db2"));
		
		SerializeConfig serializeConfig = new SerializeConfig();
		serializeConfig.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		serializeConfig.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		
		String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), serializeConfig, SerializerFeature.WriteMapNullValue);
		
		AjaxUtil.ajaxReturn(val, response);
	}
	
	@RequestMapping(value="/ajaxExportDcDailyGrid")
	public void ajaxExportDcDailyGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bu", request.getParameter("bu"));
		map.put("date", request.getParameter("date"));
		map.put("lineCode", request.getParameter("lineCode"));
		map.put("batchNo", request.getParameter("batchNo"));
		map.put("plantCode", request.getParameter("plantCode"));
		map.put("mCode", request.getParameter("mCode"));
		List<Map<String, Object>> list = dcDailyService.getDcDailyData(map, new DataSourceName("db2"));
		
		try {
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="+new String("DC每日状态.xls".getBytes(),"ISO8859-1"));
			response.setContentType("application/msexcel");
			writeDcDailyGridXls(list, os);
			os.flush();
			os.close();
		} catch (Exception e) {
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "文件导出失败"));
			AjaxUtil.ajaxReturn(jsonObject,response);
			return;
		}
		
	}
	
	private void writeDcDailyGridXls(List<Map<String, Object>> list, OutputStream os) throws Exception {

		WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		wcfFC.setBackground(Colour.GREY_25_PERCENT);
		
		WritableFont wfct = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wchT = new WritableCellFormat(wfct);
		wchT.setAlignment(Alignment.CENTRE);
		
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;
			
			sheets[j].mergeCells(0, start, 24, start);
			sheets[j].addCell(new Label(0, start, "DC每日状态",wchT)); 
			int rowIndex = 0;
			sheets[j].addCell(new Label(rowIndex++, start+1, "业务单",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "发货方",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "发货方名称",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "车牌号",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "Ship-to",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "单据总量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "需扫数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "已扫数量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "甩货总量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "短提总量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "无效码量",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "扫描率（%）",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "制单日期",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "操作人员",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "是否出库",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "是否作废",wcfFC));
			sheets[j].addCell(new Label(rowIndex++, start+1, "是否延迟",wcfFC));
			
			int row = 0;
			Map<String, Object> lineMap = null;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 2;
				lineMap = list.get(i);
				rowIndex = 0;
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("NCCODE"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("OID"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("ONAME"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("BUS_NO"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("RECEIVENO"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("SUMQUANTITY"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("SUMSARTONSCANNING"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("SUMIDCODE"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("SCANRECARGONUM"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("SCANSHORTNUM"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("SIGNSCANNUM"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("PERCENTS"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("MAKEDATE"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("SCANUSER"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("ISAUDIT"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("ISBLANKOUT"))));
				sheets[j].addCell(new Label(rowIndex++, row, getVal(lineMap.get("ISDELAY"))));
			}
		}
		
		workbook.write();
		workbook.close();
		os.close();
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
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cartonUid", request.getParameter("cartonUid"));
		map.put("itemUid", request.getParameter("itemUid"));
		map.put("batchNo", request.getParameter("batchNo"));
		
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
		List<Map<String, Object>> list = uidService.getUidData(map, new DataSourceName("db2"));
		
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
