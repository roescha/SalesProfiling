package com.bjss.waitrose.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bjss.waitrose.domain.DateRange;
import com.bjss.waitrose.domain.StoreAndProduct;
import com.bjss.waitrose.domain.persistence.Flow;

public interface FlowService {
	List<Flow> getHourlyFlowsOnAGivenDate(StoreAndProduct storeAndProduct, Date date);

	Flow getAggregatedFlow(StoreAndProduct storeAndProduct);

	Flow getAggregatedDayFlow(StoreAndProduct storeAndProduct, String day);

	List<Flow> getHourlyFlowsOnADateRangeForSingleStore(StoreAndProduct storeAndProduct, DateRange dates);

	Page<Flow> getHourlyFlowsOnADateRangeForAllStores(StoreAndProduct storeAndProduct, DateRange dates, Pageable pageable);

	List<Flow> getHourlyFlowsOnADateRangeForAllStores(StoreAndProduct storeAndProduct, DateRange dates, int page);

}
