package com.winsafe.utils;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 用于页面上Datatable的分页处理
 * @author ryan.ruan
 *
 */
public class DatatablePageHelper {
	
	/**
	 * 执行当前方法后，下一个数据库查询将会进行分页处理
	 * @param request
	 * @param hasOrder 是否排序
	 * @param orderByCondition 强制排序的字段。如果设置了，则不从Datatables获取排序信息
	 * @param needAppendOrderCondition 是否需要在现有的从Datatables获得的排序字段后拼接排序字段
	 * @param appendOrderCondition 拼接的排序字段
	 * @return
	 * @throws Exception
	 */
	public static DatatablePage getDatatableViewPage(HttpServletRequest request, boolean hasOrder, boolean hasOrderByCondition, String orderByCondition, boolean hasAppendOrderCondition, String appendOrderCondition) throws Exception{
		String drawStr = request.getParameter("draw");  
		//开始的记录数
		String startStr = request.getParameter("start");  
		//pagesize
        String lengthStr = request.getParameter("length"); 
        // For sortable
        String sortOrder = request.getParameter("order[0][column]");
        String sortDir = request.getParameter("order[0][dir]");
        // For search
        String searchValue = request.getParameter("search[value]");
		
        String orderBy = null;
		String orderColumnName = null;
		if(sortOrder != null && sortOrder.length() > 0 && sortDir != null && sortDir.length() > 0){
			orderColumnName = request.getParameter("columns["+sortOrder+"][data]");
			orderBy = (orderColumnName != null && orderColumnName.length() > 0)? (" " + orderColumnName + " "+ sortDir): null;
		}
        
		if(hasOrder){
			//如果手动设置了排序字段，则不再从Datatables获取
			if(hasOrderByCondition){
				orderBy = orderByCondition;
			}
			else{
				if(hasAppendOrderCondition && !isEmpty(appendOrderCondition)){
					orderBy = appendColumnsOrder(orderBy, appendOrderCondition, true);
				}
			}
		}
		else{
			orderBy = null;
		}
		
        Integer draw = isEmpty(drawStr)? 1: (Integer.valueOf(drawStr)+1);
        Integer start = isEmpty(startStr)? 1: Integer.valueOf(startStr);
        Integer length = isEmpty(lengthStr)? Integer.MAX_VALUE: Integer.valueOf(lengthStr);
        Integer currentPage = length == 0? 1: (start/length + 1);
        
        DatatablePage dPage = new DatatablePage();
        dPage.setCurrentPage(currentPage);
        dPage.setDraw(draw);
        dPage.setPageSize(length);
        dPage.setSearchValue(searchValue);
        Page<Object> page = PageHelper.startPage(dPage.getCurrentPage(), dPage.getPageSize());
        page.setOrderBy(orderBy);
        dPage.setPage(page);
        return dPage;
	}
	
	/**
	 * 
	 * @param existsColumnOrder
	 * @param appendColumnsOrder
	 * @param removeDuplicateColumns 是否从appendColumns中移除存在于existsColumn的字段
	 * @return
	 */
	private static String appendColumnsOrder(String existsColumnOrder, String appendColumnsOrder, boolean removeDuplicateColumns){
		if(isEmpty(appendColumnsOrder)){
			return existsColumnOrder;
		}
		String[] appendOrderConditionSplits = appendColumnsOrder.toLowerCase().split(",");
		String[] orderByConditionSplits = (!isEmpty(existsColumnOrder)? existsColumnOrder.toLowerCase(): "").split(",");
		Set<String> existOrderByColumnSet = new HashSet<String>();
		for(String existColumn: orderByConditionSplits){
			existOrderByColumnSet.add(existColumn.split(" ")[0].trim());
		}
		
		//移除重复的列
		String[] columnNameSort = null;
		String appendColumnOrderStr = "";
		for(String appendColumn: appendOrderConditionSplits){
			columnNameSort = appendColumn.trim().split(" ");
			if(!removeDuplicateColumns || !existOrderByColumnSet.contains(columnNameSort[0].trim())){
				appendColumnOrderStr = appendColumnOrderStr + (!isEmpty(appendColumnOrderStr)? ",": "") + columnNameSort[0].trim() + " " + (columnNameSort.length==1? "asc": columnNameSort[1].trim());
			}
		}
		existOrderByColumnSet = null;
		existsColumnOrder = isEmpty(existsColumnOrder)? "": (existsColumnOrder.toLowerCase().trim() + (isEmpty(appendColumnOrderStr)? "": ("," + appendColumnOrderStr)));
		return existsColumnOrder;
	}
	
	/**
	 * 执行当前方法后，下一个数据库查询将会进行分页处理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static DatatablePage getDatatableViewPage(HttpServletRequest request) throws Exception{
		return getDatatableViewPageAppendOrder(request, "id ASC");
	}
	
	/**
	 * 分页查询，在Datatables传递过来的分页参数后拼接新的列
	 * @param request
	 * @param appendOrderCondition
	 * @return
	 * @throws Exception
	 */
	public static DatatablePage getDatatableViewPageAppendOrder(HttpServletRequest request, String appendOrderCondition) throws Exception{
		return getDatatableViewPage(request, true, false, null, true, appendOrderCondition);
	}
	
	/**
	 * 完成按照Datatables传递过来的排序参数进行排序。无任何修改。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static DatatablePage getDatatableViewPageOriginal(HttpServletRequest request) throws Exception{
		return getDatatableViewPage(request, true, false, null, false, null);
	}
	
	/**
	 * 分页查询，无序。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static DatatablePage getDatatableViewPageNoOrder(HttpServletRequest request) throws Exception{
		return getDatatableViewPage(request, false, false, null, false, null);
	}
	
	/**
	 * 分页查询，按照给定的orderByCondition进行排序
	 * @param request
	 * @param orderByCondition
	 * @return
	 * @throws Exception
	 */
	public static DatatablePage getDatatableViewPage(HttpServletRequest request, String orderByCondition) throws Exception{
		return getDatatableViewPage(request, true, true, orderByCondition, false, null);
	}
	
	public static Page<?> getPage(HttpServletRequest request) throws Exception{
		DatatablePage dPage = getDatatableViewPage(request);
		return PageHelper.startPage(dPage.getCurrentPage(), dPage.getPageSize());
	}
	
	private static boolean isEmpty(String str){
		return str == null || str.trim().length()==0;
	}
}
