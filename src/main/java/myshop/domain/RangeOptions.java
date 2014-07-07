package myshop.domain;

public enum RangeOptions {
	DAY, WEEK, MONTH, SEMESTER, YEAR;
	
	public boolean forDay(){
		return this == DAY;
	}

	public boolean forWeek() {
		return this == WEEK;
	}

}
