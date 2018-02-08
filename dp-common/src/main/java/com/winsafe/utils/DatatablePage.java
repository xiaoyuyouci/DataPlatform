package com.winsafe.utils;

import com.github.pagehelper.Page;

public class DatatablePage {
	private Integer draw;
	private Integer currentPage;
	private Integer pageSize;
	private Page<Object> page;
	private String searchValue;
	
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Page<Object> getPage() {
		return page;
	}
	public void setPage(Page<Object> page) {
		this.page = page;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
}
