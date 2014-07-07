package myshop.helper.calculator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import myshop.domain.DataPoint;
import myshop.domain.MonthDataPoint;
import myshop.domain.persistence.Flow;

import com.google.common.collect.Lists;

public class Calculators {
	public static abstract class Calculator implements DataPointCalculator {
		@Override
		public List<DataPoint> merge(List<DataPoint> points, List<DataPoint> pagePoints) {
			
			if (pagePoints == null || pagePoints.isEmpty()) {
				return points;
			}
			if (points == null) {
				return pagePoints;
			}
			List<DataPoint> toAdd = null;
			boolean exists;
			for (DataPoint pagePoint : pagePoints) {
				exists = false;
				for (DataPoint point : points) {
					if (point.getPointInTime() == pagePoint.getPointInTime()) {
						point.addValueAndCount(pagePoint.getSum(), pagePoint.getPointsCount());
						exists = true;
						break;
					}
				}
				if (!exists) {
					if (toAdd == null)
						toAdd = new ArrayList<>();
					toAdd.add(pagePoint);
				}
			}

			if (toAdd != null)
				points.addAll(toAdd);
			return points;
		}
	}

	public static class HourlyCalculator extends Calculator implements DataPointCalculator {

		@Override
		public List<DataPoint> calculate(List<Flow> flows) {
			Map<Integer, DataPoint> resultFlows = new HashMap<>(24);

			for (Flow singleFlow : flows) {

				for (DataPoint singleFlowPerHour : singleFlow.getHourflyFlow()) {

					int hour = singleFlowPerHour.getPointInTime();
					int flowValue = singleFlowPerHour.getSum();
					DataPoint resultPointPerHour = resultFlows.get(hour);

					if (resultPointPerHour == null) {
						resultPointPerHour = new DataPoint(hour);
						resultFlows.put(hour, resultPointPerHour);
					}

					resultPointPerHour.addValue(flowValue);
				}
			}

			return Lists.newArrayList(resultFlows.values());
		}

	}

	public static class DayWeekCalculator extends Calculator implements DataPointCalculator {

		@Override
		public List<DataPoint> calculate(List<Flow> flows) {
			Map<Integer, DataPoint> resultFlows = new HashMap<>(7);

			Calendar calendar = Calendar.getInstance();

			for (int day = Calendar.SUNDAY; day <= Calendar.SATURDAY; day++) {
				DataPoint point = new DataPoint(day);
				resultFlows.put(day, point);
			}

			for (Flow singleFlow : flows) {
				calendar.setTime(singleFlow.getDate());
				int day = calendar.get(Calendar.DAY_OF_WEEK);
				int totalForDay = singleFlow.getTotal();
				DataPoint resultPointPerDay = resultFlows.get(day);
				resultPointPerDay.addValue(totalForDay);
			}

			return Lists.newArrayList(resultFlows.values());
		}

	}

	public static class DayMonthCalculator extends Calculator implements DataPointCalculator {

		@Override
		public List<DataPoint> calculate(List<Flow> flows) {
			Map<Integer, DataPoint> resultFlows = new HashMap<>();

			Calendar calendar = Calendar.getInstance();

			for (int day = 1; day <= 31; day++) {
				DataPoint point = new DataPoint(day);
				resultFlows.put(day, point);
			}

			for (Flow singleFlow : flows) {
				calendar.setTime(singleFlow.getDate());
				int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				int totalForDay = singleFlow.getTotal();
				DataPoint resultPointPerMonth = resultFlows.get(dayOfMonth);
				resultPointPerMonth.addValue(totalForDay);
			}

			return Lists.newArrayList(resultFlows.values());
		}
	}

	public static class DayYearCalculator extends Calculator implements DataPointCalculator {

		@Override
		public List<DataPoint> calculate(List<Flow> flows) {
			Map<Integer, DataPoint> resultFlows = new HashMap<>();

			Calendar calendar = Calendar.getInstance();

			for (Flow singleFlow : flows) {
				calendar.setTime(singleFlow.getDate());
				int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
				int totalForDay = singleFlow.getTotal();
				DataPoint resultPointPerDay = resultFlows.get(dayOfYear);
				if (resultPointPerDay == null) {
					resultPointPerDay = new DataPoint(dayOfYear);
					resultFlows.put(dayOfYear, resultPointPerDay);
				}
				resultPointPerDay.addValue(totalForDay);
			}

			return Lists.newArrayList(resultFlows.values());
		}

	}

	public static class MonthCalculator extends Calculator implements DataPointCalculator {
		
		@Override
		public List<DataPoint> merge(List<DataPoint> points, List<DataPoint> pagePoints) {
			if (pagePoints == null || pagePoints.isEmpty()) {
				return points;
			}
			if (points == null) {
				return pagePoints;
			}
			List<DataPoint> toAdd = null;
			boolean exists;
			for (DataPoint pagePoint : pagePoints) {
				exists = false;
				MonthDataPoint monthPagePoint = (MonthDataPoint) pagePoint;
				for (DataPoint point : points) {
					if (point.getPointInTime() == pagePoint.getPointInTime()) {
						if (pagePoint.getSum() > 0){
							MonthDataPoint monthPoint = (MonthDataPoint) point;
							Set<Integer> storeIdsForPoint = monthPoint.getStoreIds();
							if (storeIdsForPoint == null){
								storeIdsForPoint = new HashSet<>(monthPagePoint.getStoreIds());
								monthPoint.setStoreIds(storeIdsForPoint);
							}else{
								monthPoint.getStoreIds().addAll(monthPagePoint.getStoreIds());
							}
							monthPagePoint.getStoreIds().clear();
							point.addValueAndFixCount(pagePoint.getSum(), monthPoint.getStoreIds().size());
						}
						exists = true;
						break;
					}
				}
				if (!exists) {
					if (toAdd == null)
						toAdd = new ArrayList<>();
					toAdd.add(pagePoint);
				}
			}

			if (toAdd != null)
				points.addAll(toAdd);
			return points;
		}


		@Override
		public List<DataPoint> calculate(List<Flow> flows) {
			Map<Integer, DataPoint> resultFlows = new HashMap<>();

			Calendar calendar = Calendar.getInstance();

			for (int day = Calendar.JANUARY; day <= Calendar.DECEMBER; day++) {
				DataPoint point = new MonthDataPoint(day);
				resultFlows.put(day, point);
			}

			Map<Integer, Integer> totalPerMonth = new HashMap<>();
			Map<Integer, Set<Integer>> storesPerMonth = new HashMap<>();
			

			for (Flow singleFlow : flows) {
				calendar.setTime(singleFlow.getDate());
				Integer monthOfYear = calendar.get(Calendar.MONTH);
				Integer totalForDay = singleFlow.getTotal();
				Integer store = singleFlow.getStoreNumber();
				Integer currentTotal = totalPerMonth.get(monthOfYear);
				Set<Integer> currentStores = storesPerMonth.get(monthOfYear);
				if (currentStores == null){
					Set<Integer> storeSet = new HashSet<>();
					storeSet.add(store);
					storesPerMonth.put(monthOfYear, storeSet);
				}else{
					currentStores.add(store);
				}
				if (currentTotal == null) {
					totalPerMonth.put(monthOfYear, totalForDay);
				} else {
					totalPerMonth.put(monthOfYear, currentTotal + totalForDay);
				}
			}

			for (Map.Entry<Integer, Integer> perMonth : totalPerMonth.entrySet()) {
				MonthDataPoint resultPointPerMonth = (MonthDataPoint) resultFlows.get(perMonth.getKey());
				resultPointPerMonth.addValue(perMonth.getValue());
				resultPointPerMonth.setPointsCount(storesPerMonth.get(perMonth.getKey()).size());			
				resultPointPerMonth.setStoreIds(storesPerMonth.get(perMonth.getKey()));
			}

			return Lists.newArrayList(resultFlows.values());
		}
	}

	public static class AggregatedCalculator extends Calculator implements DataPointCalculator {
		private final DataPointCalculator calculator;
		private final int count;

		public AggregatedCalculator(DataPointCalculator calculator, int count) {
			this.calculator = calculator;
			this.count = count;
		}

		@Override
		public List<DataPoint> calculate(List<Flow> flows) {
			List<DataPoint> points = calculator.calculate(flows);
			for (DataPoint point : points) {
				point.setPointsCount(count);
			}
			return points;
		}
	}

}
