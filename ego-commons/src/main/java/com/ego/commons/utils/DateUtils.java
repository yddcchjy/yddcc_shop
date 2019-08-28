package com.ego.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String transferdate(Date date,String dateFormatparam){
		DateFormat dateFormat = new SimpleDateFormat(dateFormatparam);
		return dateFormat.format(date);
		
	}
	
	public static Date transferdate(String datastring,String dateFormatparam){
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(dateFormatparam);
		try {
			date = dateFormat.parse(datastring);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
		
	}
	
	//目前定时任务用
		public static String dateToString(Date date) {
			// TODO Auto-generated method stub
			return dateToString(date,"yyyy=MM-dd hh:mm:ss:SSS");
		}
		//目前定时任务用
		public static String dateToString(Date date, String formatstring) {
			DateFormat dateFormat = new SimpleDateFormat(formatstring);
			return dateFormat.format(date);
		}
}
