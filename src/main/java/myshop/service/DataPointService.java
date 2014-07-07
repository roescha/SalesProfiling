package myshop.service;

import java.util.List;

import myshop.domain.DataPoint;
import myshop.domain.DateRange;
import myshop.domain.StoreAndProduct;
import myshop.domain.TimeScale;

public interface DataPointService {

	List<DataPoint> buildProfile(	StoreAndProduct storeAndProduct,
																											DateRange dates,
																											TimeScale timeScale);

	List<DataPoint> buildProfileFromAggregation(StoreAndProduct storeAndProduct,
																																		TimeScale timeScale,
																																		boolean isDayCalculation, String day);

}