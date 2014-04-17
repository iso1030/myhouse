package com.jerrylin.myhouse.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
		return sdf.format(new Date(date));
	}
	
	public static String format(long date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(date));
	}
	
	public static String getTodayStr() {
		return format(new Date().getTime(), "yyyyMMdd");
	}
	
	public static void main(String[] args) {
		System.out.println("hello world!");
		System.out.println(getTodayStr());
	}
}
