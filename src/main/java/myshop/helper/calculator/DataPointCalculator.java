package myshop.helper.calculator;

import java.util.List;

import myshop.domain.DataPoint;
import myshop.domain.persistence.Flow;

public interface DataPointCalculator {
  List<DataPoint> calculate(List<Flow> flows);
	List<DataPoint> merge(List<DataPoint> points, List<DataPoint> pagePoints);
}
