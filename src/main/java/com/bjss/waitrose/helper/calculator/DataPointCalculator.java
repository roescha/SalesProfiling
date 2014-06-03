package com.bjss.waitrose.helper.calculator;

import java.util.List;

import com.bjss.waitrose.domain.DataPoint;
import com.bjss.waitrose.domain.persistence.Flow;

public interface DataPointCalculator {
  List<DataPoint> calculate(List<Flow> flows);
	List<DataPoint> merge(List<DataPoint> points, List<DataPoint> pagePoints);
}
