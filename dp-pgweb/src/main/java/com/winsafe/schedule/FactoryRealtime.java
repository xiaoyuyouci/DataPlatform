package com.winsafe.schedule;

public class FactoryRealtime {
	
	private String bu;
	private String plantcode;
	private String linecode;
	private String status;//当前状态
	private String batchno;
	private String case_item;
//	private String protype;
	
	private String fpc;//(又叫mcode)
	private String productname;
	private String case_package;//箱瓶比例
	private String real_package;//箱瓶比例
	private String backnum;//回传数量
	
	//1 代表item
	private String ckqrnum1; //扫描数量
	private String elqrnum1;//剔除数量
	private String scannum1;//本地数量
	private String count1;//后台系统总数;
	private String context1;//context接收数量
	private String item_percent;//合格率
	//2代表case
	private String ckqrnum2; //扫描数量
	private String elqrnum2;//剔除数量
	private String scannum2;//本地数量
	private String count2;//后台系统总数;
	private String context2;//context接收数量
	private String case_percent;//合格率
	
	private String is_true;//是否符合箱瓶比例
	private String up_time;//更新时间
	
	public String getCase_item() {
		return case_item;
	}
	public void setCase_item(String case_item) {
		this.case_item = case_item;
	}
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	public String getPlantcode() {
		return plantcode;
	}
	public void setPlantcode(String plantcode) {
		this.plantcode = plantcode;
	}
	public String getLinecode() {
		return linecode;
	}
	public void setLinecode(String linecode) {
		this.linecode = linecode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getFpc() {
		return fpc;
	}
	public void setFpc(String fpc) {
		this.fpc = fpc;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getCase_package() {
		return case_package;
	}
	public void setCase_package(String case_package) {
		this.case_package = case_package;
	}
	public String getReal_package() {
		return real_package;
	}
	public void setReal_package(String real_package) {
		this.real_package = real_package;
	}
	public String getBacknum() {
		return backnum;
	}
	public void setBacknum(String backnum) {
		this.backnum = backnum;
	}
	public String getCkqrnum1() {
		return ckqrnum1;
	}
	public void setCkqrnum1(String ckqrnum1) {
		this.ckqrnum1 = ckqrnum1;
	}
	public String getElqrnum1() {
		return elqrnum1;
	}
	public void setElqrnum1(String elqrnum1) {
		this.elqrnum1 = elqrnum1;
	}
	public String getScannum1() {
		return scannum1;
	}
	public void setScannum1(String scannum1) {
		this.scannum1 = scannum1;
	}
	public String getCount1() {
		return count1;
	}
	public void setCount1(String count1) {
		this.count1 = count1;
	}
	public String getContext1() {
		return context1;
	}
	public void setContext1(String context1) {
		this.context1 = context1;
	}
	public String getItem_percent() {
		return item_percent;
	}
	public void setItem_percent(String item_percent) {
		this.item_percent = item_percent;
	}
	public String getCkqrnum2() {
		return ckqrnum2;
	}
	public void setCkqrnum2(String ckqrnum2) {
		this.ckqrnum2 = ckqrnum2;
	}
	public String getElqrnum2() {
		return elqrnum2;
	}
	public void setElqrnum2(String elqrnum2) {
		this.elqrnum2 = elqrnum2;
	}
	public String getScannum2() {
		return scannum2;
	}
	public void setScannum2(String scannum2) {
		this.scannum2 = scannum2;
	}
	public String getCount2() {
		return count2;
	}
	public void setCount2(String count2) {
		this.count2 = count2;
	}
	public String getContext2() {
		return context2;
	}
	public void setContext2(String context2) {
		this.context2 = context2;
	}
	public String getCase_percent() {
		return case_percent;
	}
	public void setCase_percent(String case_percent) {
		this.case_percent = case_percent;
	}
	public String getIs_true() {
		return is_true;
	}
	public void setIs_true(String is_true) {
		this.is_true = is_true;
	}
	public String getUp_time() {
		return up_time;
	}
	public void setUp_time(String up_time) {
		this.up_time = up_time;
	}
	
}
