package myshop.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import myshop.domain.CompareOptions;
import myshop.domain.DateRange;
import myshop.domain.DirectionOptions;
import myshop.domain.RangeOptions;
import myshop.domain.TimeScale;

public class DatesUtil {

	public static DateRange validateAndGetDateRange(String from, RangeOptions range) throws ParseException {
		Date fromDate;

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		fromDate = format.parse(from);			

		if (range.forDay()) return new DateRange(fromDate);		
				
		Calendar end = Calendar.getInstance();
		end.setTime(fromDate);
		
		switch(range){
		case WEEK:
			end.add(Calendar.DAY_OF_WEEK, 6);
			break;
		case MONTH:
			end.add(Calendar.MONTH, 1);
			end.add(Calendar.DAY_OF_MONTH, -1);
			break;
		case SEMESTER:
			end.add(Calendar.MONTH, 6);
			end.add(Calendar.DAY_OF_MONTH, -1);
			break;
		case YEAR:
			end.add(Calendar.YEAR, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);
			break;
		default:
			return new DateRange(fromDate);			
		}
		
		return new DateRange(fromDate, end.getTime());
	}

	
	public static String validateAndShiftDate(String from, RangeOptions range, DirectionOptions direction) throws ParseException {
		Date fromDate;

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		fromDate = format.parse(from);			
				
		Calendar date = Calendar.getInstance();
		date.setTime(fromDate);
		
		switch(range){
		case WEEK:
			date.add(Calendar.DAY_OF_WEEK, 7 * direction.shift());
			break;
		case MONTH:
			date.add(Calendar.MONTH, 1 * direction.shift());
			break;
		case SEMESTER:
			date.add(Calendar.MONTH, 6 * direction.shift());
			break;
		case YEAR:
			date.add(Calendar.YEAR, 1 * direction.shift());
			break;
		default:
			date.add(Calendar.DAY_OF_WEEK, 1 * direction.shift());			
		}
		
		return format.format(date.getTime());
	}


	public static DateRange findComparisonDate(CompareOptions compare, DateRange dates, TimeScale scale, RangeOptions range) {

		Calendar start = Calendar.getInstance();
		start.setTime(dates.getStart());

		Map<Integer, Integer> valuesStart = getDateBreakDown(start);
		Map<Integer, Integer> valuesEnd = null;

		Calendar end = null;
		if (dates.forDateRange()) {
			end = Calendar.getInstance();
			end.setTime(dates.getEnd());
			valuesEnd = getDateBreakDown(end);
		}

		switch (compare) {
		case PREVIOUS_WEEK:
			start.add(Calendar.DAY_OF_WEEK, -7);
			if (dates.forDateRange()) {
				end.add(Calendar.DAY_OF_WEEK, -7);
			}
			break;
		case PREVIOUS_MONTH:
			start.add(Calendar.MONTH, -1);
			start = adjustDateToSameWeekDayAndWeekInMonth(start, range, valuesStart);
			if (dates.forDateRange()) {
				end.add(Calendar.MONTH, -1);
				end = adjustDateToSameWeekDayAndWeekInMonth(end, range, valuesEnd);
			}
			
			break;
		case PREVIOUS_YEAR:
			start.add(Calendar.MONTH, -12);
			start = adjustDateToSameWeekDayAndWeekInMonth(start, range, valuesStart);
			if (dates.forDateRange()) {
				end.add(Calendar.MONTH, -12);
				end = adjustDateToSameWeekDayAndWeekInMonth(end, range, valuesEnd);
			}

			break;
		case AVERAGE_DAY:
		case AVERAGE_ALL:
			start.add(Calendar.YEAR, -5);
			end = Calendar.getInstance();
			end.clear(Calendar.HOUR);
			end.clear(Calendar.MINUTE);
			end.clear(Calendar.SECOND);
			end.clear(Calendar.MILLISECOND);
			break;
		}

		return new DateRange(start, end);
	}
	
	public static String getDatesAsString(DateRange dates) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		if (dates.forOneDay()) {
			return format.format(dates.getStart());
		} else {
			return format.format(dates.getStart()) + " - " + format.format(dates.getEnd());
		}
	}
	 
	private static  Calendar adjustDateToSameWeekDayAndWeekInMonth(Calendar date, RangeOptions range,  Map<Integer, Integer> dateValues){
		
		if (range.forDay() || range.forWeek()){
		date.set(Calendar.DAY_OF_WEEK, dateValues.get(Calendar.DAY_OF_WEEK));
		date.set(Calendar.DAY_OF_WEEK_IN_MONTH, dateValues.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		}else{
			//date.set(Calendar.DAY_OF_MONTH, dateValues.get(Calendar.DAY_OF_MONTH));
		}
		return date;
	}

	private static Map<Integer, Integer> getDateBreakDown(Calendar date) {
		Map<Integer, Integer> dates = new HashMap<>();
		dates.put(Calendar.DAY_OF_WEEK, date.get(Calendar.DAY_OF_WEEK));
		dates.put(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		dates.put(Calendar.DAY_OF_WEEK_IN_MONTH, date.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		dates.put(Calendar.DAY_OF_YEAR, date.get(Calendar.DAY_OF_YEAR));
		dates.put(Calendar.MONTH, date.get(Calendar.MONTH));
		dates.put(Calendar.YEAR, date.get(Calendar.YEAR));
		return dates;
	}
}
