package com.bjss.waitrose.helper.calculator;

import com.bjss.waitrose.domain.TimeScale;

public class DataPointCalculatorFactory {

	public static DataPointCalculator getCalculator(TimeScale timeScale) {
		switch (timeScale) {
		case HOUR:
			return new Calculators.HourlyCalculator();
		case DAY_WEEK:
			return new Calculators.DayWeekCalculator();
		case DAY_MONTH:
			return new Calculators.DayMonthCalculator();
		case DAY_YEAR:
			return new Calculators.DayYearCalculator();
		case MONTH:
			return new Calculators.MonthCalculator();
		}
		return null;
	}

	public static DataPointCalculator getCalculator(TimeScale timeScale, int count) {
		return new Calculators.AggregatedCalculator(getCalculator(timeScale), count);
	}

}
