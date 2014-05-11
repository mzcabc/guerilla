package com.mindy.guerilla.util;

import java.util.Calendar;
import java.util.TimeZone;

public class TjuDateTime {

	/*
	 * 返回TJU当前周次(String)
	 */
	static public String getTjuWeekNumberStr() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 设置偏移量
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR) - 8;

		String weekOfTJU = String.valueOf(weekOfYear);
		return weekOfTJU;
	}

	/*
	 * 返回TJU当前周次(int)
	 */
	static public int getTjuWeekNumberInt() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		// 设置偏移量
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR) - 8;
		return weekOfYear;
	}

	/*
	 * 返回星期几(String)这个错了 已经作废
	 */
	static public String getCurrentDayOfWeekStr() {
		Calendar calendar = Calendar.getInstance();

		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		String dayOfWeek = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);

		return dayOfWeek;
	}

	/*
	 * 返回星期几(int)
	 */
	static public int getCurrentDayOfWeekInt() {
		Calendar calendar = Calendar.getInstance();

		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		if (dayOfWeek == 1) {
			return 7;
		} else {
			return dayOfWeek - 1;
		}

	}
}
