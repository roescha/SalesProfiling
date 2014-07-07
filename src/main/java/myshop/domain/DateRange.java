package myshop.domain;

import java.util.Calendar;
import java.util.Date;

public class DateRange {
	private final Date start;
	private final Date end;

	public DateRange(Date start) {
		this.start = start;
		this.end = null;
	}
		
	public DateRange(Calendar from, Calendar to) {
		this.start = from.getTime();
		this.end = to != null ? to.getTime() : null;
	}

	public DateRange(Date from, Date to) {
		this.start = from;
		this.end = to;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
	
	public boolean forOneDay(){
		return end == null;
	}
	
	public boolean forDateRange(){
		return end != null;
	}

	@Override
	public String toString() {
		return "DateRange [start=" + start + ", end=" + end + "]";
	}

	
}
