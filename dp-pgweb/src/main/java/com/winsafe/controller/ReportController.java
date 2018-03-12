package com.winsafe.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.DcDailyService;
import com.winsafe.service.DcQrCodeTimeConsumingService;
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
	
	@Autowired
	private DcQrCodeTimeConsumingService dcQrCodeTimeConsumingService;
	
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
		List<Map<String, Object>> list = fcRealtimeService.getFcRealtimeData(map, new DataSourceName("primary"));
		
		String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue);
		
		AjaxUtil.ajaxReturn(val, response);
	}
	
	@RequestMapping(value="/ajaxExportFcRealtimeGrid")
	public void ajaxExportFcRealtimeGrid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bu", request.getParameter("bu"));
		map.put("status", request.getParameter("status"));
		map.put("linecode", request.getParameter("linecode"));
		List<Map<String, Object>> list = fcRealtimeService.getFcRealtimeData(map, new DataSourceName("primary"));
		
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bu", request.getParameter("bu"));
		map.put("date", request.getParameter("date"));
		map.put("lineCode", request.getParameter("lineCode"));
		map.put("batchNo", request.getParameter("batchNo"));
		map.put("plantCode", request.getParameter("plantCode"));
		map.put("mCode", request.getParameter("mCode"));
		map.put("show", request.getParameter("show"));
		DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
		List<Map<String, Object>> list = fcDailyService.getFcDailyData(map, new DataSourceName("primary"));
		
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
		map.put("show", request.getParameter("show"));
		List<Map<String, Object>> list = fcDailyService.getFcDailyData(map, new DataSourceName("primary"));
		
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
		List<Map<String, Object>> list = dcRealtimeService.getDcRealtimeData(map, new DataSourceName("pgdc"));
		
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
		List<Map<String, Object>> list = dcDailyService.getDcDailyData(map, new DataSourceName("pgdc"));
		
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
		List<Map<String, Object>> list = dcDailyService.getDcDailyData(map, new DataSourceName("pgdc"));
		
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
	 * DC耗时查询
	 * @return
	 */
	@RequestMapping("/dcQrCodeTimeConsuming")
	public String toDcQrCodeTimeConsuming() {
		return "report/dcQrCodeTimeConsuming"; 
	}
	
	/**
	 * 获取DC码申请的文件数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxGetDcAppliedFileInfo")
	public void ajaxGetDcAppliedFileInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String qrCode = request.getParameter("qrCode");
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("qrCode", qrCode);
		Map<String, Object> map = dcQrCodeTimeConsumingService.getAppliedFileInfo(filter, new DataSourceName("pgdc"));
		
		BaseResult br = new BaseResult(true, "查询成功", map);
		AjaxUtil.ajaxReturn(JSON.toJSONString(br, SerializerFeature.WriteMapNullValue), response);
		return;
	}
	/**
	 * 获取DC生产数据上传数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxGetDcUploadProduceReportInfo")
	public void ajaxGetDcUploadProduceReportInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = request.getParameter("fileName");
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("fileName", fileName);
		Map<String, Object> map = dcQrCodeTimeConsumingService.getUploadProduceReportInfo(filter, new DataSourceName("pgdc"));
		
		BaseResult br = new BaseResult(true, "查询成功", map);
		AjaxUtil.ajaxReturn(JSON.toJSONString(br, SerializerFeature.WriteMapNullValue), response);
		return;
	}
	/**
	 * 获取DC出库数量数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxGetDcOutInfo")
	public void ajaxGetDcOutInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = request.getParameter("fileName");
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("fileName", fileName);
		Map<String, Object> map = dcQrCodeTimeConsumingService.getOutInfo(filter, new DataSourceName("pgdc"));
		
		BaseResult br = new BaseResult(true, "查询成功", map);
		AjaxUtil.ajaxReturn(JSON.toJSONString(br, SerializerFeature.WriteMapNullValue), response);
		return;
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
		
		DataSourceName pgdc = new DataSourceName("pgdc");
		DataSourceName mysql = new DataSourceName("primary");
		
		if(StringUtils.isNotBlank(request.getParameter("cartonUid"))){
			DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
			List<Map<String, Object>> list = uidService.getCartonUidBaseData(map, pgdc);
			if(list != null  && list.size()>0){
				if(list.get(0).get("BNO") != null){
					String bno = String.valueOf(list.get(0).get("BNO"));
					Map<String, Object> filter = new HashMap<String, Object>();
					filter.put("ttid", bno);
					List<Map<String,Object>> data = uidService.getCartonUidDetailData(filter, pgdc);
					if(data != null && data.size()>0){
						list.get(0).putAll(data.get(0));
					}
					else{
						dPage.getPage().setTotal(0);
						list.clear();
					}
				}
			}
			
			String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue);
			AjaxUtil.ajaxReturn(val, response);
			return;
		}
		else if(StringUtils.isNotBlank(request.getParameter("itemUid"))){
			DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
			List<Map<String,Object>> list = uidService.getItemUidBaseData(map, pgdc);
			if(list != null && list.size()>0){
				Map<String,Object> map1 = list.get(0);
				//查询bno TT开头的那种
				List<Map<String,Object>> bnoList = uidService.getBnoList(String.valueOf(map1.get("BATCHNO")), mysql);
				if(bnoList !=null && bnoList.size()>0){
					String bnos = "";
					for(int i=0;i<bnoList.size();i++){
						bnos = bnos +"'"+ bnoList.get(i).get("BNO")+"',";
					}
					bnos = bnos.substring(0,bnos.length()-1);
					Map<String, Object> filter = new HashMap<String, Object>();
					filter.put("bnos", bnos);
					filter.put("productId", bnos);
					List<Map<String,Object>> data2 = uidService.getItemUidDetailData(filter, pgdc);
					if(data2 != null && data2.size() >0){
						for(int i=0;i<data2.size();i++){
							data2.get(i).putAll(map);
						}
					}else{
						dPage.getPage().setTotal(0);
						list.clear();
					}
				}
				else{
					dPage.getPage().setTotal(0);
					list.clear();
				}
			}
			String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue);
			AjaxUtil.ajaxReturn(val, response);
			return;
		}
		else if(StringUtils.isNotBlank(request.getParameter("batchNo"))){
			DatatablePage dPage = DatatablePageHelper.getDatatableViewPageNoOrder(request);
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("batchNo", request.getParameter("batchNo"));
			List<Map<String,Object>> list = uidService.getBatchDetail(filter, pgdc);
			if(list !=null && list.size()>0){
				Map<String,Object> map1 = list.get(0);
				//查询bno TT开头的那种
				List<Map<String,Object>> bnoList = uidService.getBnoList(request.getParameter("batchNo"), mysql);
				if(bnoList !=null && bnoList.size()>0){
					String bnos = "";
					for(int i=0;i<bnoList.size();i++){
						bnos = bnos +"'"+ bnoList.get(i).get("bno")+"',";
					}
					bnos = bnos.substring(0,bnos.length()-1);
					
					filter.clear();
					filter.put("bnos", bnos);
					List<Map<String,Object>> data2 = uidService.getItemUidDetailData(filter, pgdc);
					if(data2 !=null && data2.size()>0){
						for(int i=0; i<data2.size(); i++){
							data2.get(i).putAll(map1);
						}
					}else{
						dPage.getPage().setTotal(0);
						list.clear();
					}
				}
				else{
					dPage.getPage().setTotal(0);
					list.clear();
				}
			}
			String val = JSON.toJSONString(new DatatableViewPage(true, "数据查询成功！", dPage), SerializerFeature.WriteMapNullValue);
			AjaxUtil.ajaxReturn(val, response);
			return;
		}
		else{
			com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
			obj.put("draw", StringUtils.isBlank(request.getParameter("draw"))? 1: (Integer.valueOf(request.getParameter("draw"))+1));
			obj.put("legal", true);
			obj.put("message", "数据查询成功！");
			obj.put("notLegal", false);
			obj.put("recordsFiltered", 0);
			obj.put("recordsTotal", 0);
			obj.put("result", new JSONArray());
			AjaxUtil.ajaxReturn(obj.toJSONString(), response);
			return;
		}
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
		String currentDate = DateUtil.formatDate(DateUtil.now());
		String d14 = DateUtil.formatDate(DateUtil.addDay(DateUtil.now(), 14));
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("currentDate", currentDate);
		filter.put("d14", d14);
		Map<String, Integer> map1 = ppStatisticsService.getPlannedDataUploadToAimia(filter, new DataSourceName("pgbc"));
		Map<String, Integer> map2 = ppStatisticsService.getActualDataUploadToAimia(filter, new DataSourceName("pgbc"));
		Map<String, Integer> map3 = ppStatisticsService.getPDUTA14Days(filter, new DataSourceName("pgbc"));
		List<Map<String, Object>> list = ppStatisticsService.getIpcUploadData(filter, new DataSourceName("pgbc"));
		
		Map<String, Object> map = null;
		for(int i = 0; i< list.size(); i++){
			map = list.get(i);
			map.put("date", currentDate);
			map.put("c1", "");
			map.put("c2", "");
			map.put("c3", "");
			map.put("c4", "");
			map.put("rowspan", i == 0? list.size(): 0);
			map.putAll(map1);
			map.putAll(map2);
			map.putAll(map3);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("part1", list);
		
		BaseResult br = new BaseResult(true, "查询成功", result);
		AjaxUtil.ajaxReturn(JSON.toJSONString(br), response);
		return;
	}
}
