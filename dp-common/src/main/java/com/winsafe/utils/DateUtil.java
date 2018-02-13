package com.winsafe.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private DateUtil() {
	}
	
	private final static String date_pattern = "yyyy-MM-dd";
	private final static String datetime_pattern = "yyyy-MM-dd HH:mm:ss";
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(date_pattern);
	private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(datetime_pattern);
	
	public static String format(Date date, String pattern){
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	
	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		return dateFormat.format(date);
	}
	
	public static String formatDatetime(Date date) {
		if (date == null) {
			return null;
		}
		return datetimeFormat.format(date);
	}
	
	public static Date parseDate(String strDate) {
		return parse(strDate, date_pattern);
	}
	
	public static Date parseDatetime(String strDatetime) {
		return parse(strDatetime, datetime_pattern);
	}
	
	public static Date parse(String strDate, String dateFormat) {
		DateFormat tempformat = new SimpleDateFormat(dateFormat);
		Date date = new Date();
		try {
			if (strDate == null || strDate.equals("null") || strDate.equals("")) {
				return null;
			}
			date = tempformat.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date now(){
		return new Date();
	}
}