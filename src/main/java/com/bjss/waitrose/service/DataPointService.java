package com.bjss.waitrose.service;

import java.util.List;

import com.bjss.waitrose.domain.DataPoint;
import com.bjss.waitrose.domain.DateRange;
import com.bjss.waitrose.domain.StoreAndProduct;
import com.bjss.waitrose.domain.TimeScale;

public interface DataPointService {

	List<DataPoint> buildProfile(	StoreAndProduct storeAndProduct,
																											DateRange dates,
																											TimeScale timeScale);

	List<DataPoint> buildProfileFromAggregation(StoreAndProduct storeAndProduct,
																																		TimeScale timeScale,
																																		boolean isDayCalculation, String day);

}