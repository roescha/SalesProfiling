package myshop.service;

import java.util.Date;
import java.util.List;

import myshop.domain.DateRange;
import myshop.domain.StoreAndProduct;
import myshop.domain.persistence.Flow;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlowService {
	List<Flow> getHourlyFlowsOnAGivenDate(StoreAndProduct storeAndProduct, Date date);

	Flow getAggregatedFlow(StoreAndProduct storeAndProduct);

	Flow getAggregatedDayFlow(StoreAndProduct storeAndProduct, String day);

	List<Flow> getHourlyFlowsOnADateRangeForSingleStore(StoreAndProduct storeAndProduct, DateRange dates);

	Page<Flow> getHourlyFlowsOnADateRangeForAllStores(StoreAndProduct storeAndProduct, DateRange dates, Pageable pageable);

	List<Flow> getHourlyFlowsOnADateRangeForAllStores(StoreAndProduct storeAndProduct, DateRange dates, int page);

}
