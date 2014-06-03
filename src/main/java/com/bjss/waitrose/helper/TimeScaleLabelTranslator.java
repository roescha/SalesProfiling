package com.bjss.waitrose.helper;

import java.util.Calendar;
import java.util.Date;

import com.bjss.waitrose.domain.TimeScale;

public class TimeScaleLabelTranslator {

	private enum DAY_WEEK {
		Sun, Mon, Tue, Wed, Thu, Fri, Sat;
		public static DAY_WEEK getLabel(int day) {
			return values()[day - Calendar.SUNDAY];
		}
	}

	private enum MONTH {
		Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sept, Oct, Nov, Dec;
		public static MONTH getLabel(int month) {
			return values()[month - Calendar.JANUARY];
		}
	}

	public static String getLabel(TimeScale category, int dayOfWeek) {
		switch (category) {
		case DAY_WEEK:
			return DAY_WEEK.getLabel(dayOfWeek).toString();
		case MONTH:
			return MONTH.getLabel(dayOfWeek).toString();
		default:
			return String.valueOf(dayOfWeek);
		}
	}
	
	public static String getLabelFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return getLabel(TimeScale.DAY_WEEK, dayOfWeek);	
	}
	

}
