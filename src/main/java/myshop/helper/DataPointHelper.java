package myshop.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import myshop.domain.DataPoint;
import myshop.domain.DataPointView;
import myshop.domain.TimeScale;

public class DataPointHelper {
	public static List<DataPointView> convertToDataPointView(	List<DataPoint> dataPoints,
																														TimeScale timeScale) {
		List<DataPointView> viewPoints = new ArrayList<>();
		for (DataPoint point : dataPoints) {
			DataPointView pointForView = new DataPointView(point.getPointInTime(), point.getSum(),
					point.getAverage(), TimeScaleLabelTranslator.getLabel(
							timeScale, point.getPointInTime()));
			viewPoints.add(pointForView);
		}
		Collections.sort(viewPoints);
		return viewPoints;

	}

}
