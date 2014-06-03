package com.bjss.waitrose.domain;

public enum CompareOptions {
	PREVIOUS_WEEK, PREVIOUS_MONTH, PREVIOUS_YEAR, AVERAGE_DAY, AVERAGE_ALL;
	
	public boolean forAverage() {
		if (this == AVERAGE_ALL || this == AVERAGE_DAY) {
			return true;
		}
		return false;
	}

	public boolean forAverageDay() {
		return this == AVERAGE_DAY;
	}
}
