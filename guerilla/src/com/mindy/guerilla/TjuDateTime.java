package com.mindy.guerilla;

import java.util.Calendar;
import java.util.TimeZone;

public class TjuDateTime {

	/*
	 * 返回TJU当前周次
	 */
	static public String getTjuWeekNumber() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 设置偏移量
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR) - 8;

		String weekOfTJU = String.valueOf(weekOfYear);
		return weekOfTJU;
	}

	/*
	 * 返回星期几
	 */
	static public String getCurrentDayOfWeek() {
		Calendar calendar = Calendar.getInstance();

		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		String dayOfWeek = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
		
		return dayOfWeek;
	}
}
