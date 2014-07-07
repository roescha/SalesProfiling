package myshop.service;

import java.util.Date;
import java.util.List;

import myshop.domain.DateRange;
import myshop.domain.StoreAndProduct;
import myshop.domain.persistence.Flow;
import myshop.repository.FlowRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class FlowServiceImpl implements FlowService {

	private final FlowRepository flowRepository;
	
	@Autowired
	public FlowServiceImpl(FlowRepository flowRepository){
		this.flowRepository = flowRepository;
	}
	
	@Override
	public List<Flow> getHourlyFlowsOnAGivenDate(StoreAndProduct storeAndProduct, Date date) {
		List<Flow> flows;
		if (storeAndProduct.forAllStores()){
			flows = Lists.newArrayList(flowRepository.findByLineNumberAndDate(storeAndProduct.getLineNumber(), date));
		}else{
			flows = Lists.newArrayList(flowRepository.findByLineNumberAndStoreNumberAndDate(storeAndProduct.getLineNumber(),  storeAndProduct.getStoreNumber(), date));		
		}
		if (flows.isEmpty()) flows.add(new Flow());
		return flows;
	}

	@Override
	public List<Flow> getHourlyFlowsOnADateRangeForSingleStore(StoreAndProduct storeAndProduct, DateRange dates) {
		return Lists.newArrayList(flowRepository.findByLineNumberAndStoreNumberAndDateBetween(storeAndProduct.getLineNumber(), storeAndProduct.getStoreNumber(), dates.getStart(), dates.getEnd()));	
	}
	

	@Override
	public Page<Flow> getHourlyFlowsOnADateRangeForAllStores(StoreAndProduct storeAndProduct, DateRange dates, Pageable pageable) {
		// hacky set return to handle duplicates
		return flowRepository.findByLineNumberAndDateBetween(storeAndProduct.getLineNumber(), dates.getStart(), dates.getEnd(), pageable);
		
	}
	
	@Override
	public List<Flow> getHourlyFlowsOnADateRangeForAllStores(StoreAndProduct storeAndProduct, DateRange dates, int page) {
	// hacky set return to handle duplicates
		return Lists.newArrayList(flowRepository.findByLineNumberAndDateBetween(storeAndProduct.getLineNumber(), dates.getStart(), dates.getEnd(), page));		
	}


	@Override
	public Flow getAggregatedFlow(StoreAndProduct storeAndProduct) {
		if (storeAndProduct.forAllStores()){
			return flowRepository.findHourAggregationByLineNumber(storeAndProduct.getLineNumber());
		}else{
		return flowRepository.findHourAggregationByLineNumberAndStoreNumber(storeAndProduct.getLineNumber(), storeAndProduct.getStoreNumber());	
		}
	}

	@Override
	public Flow getAggregatedDayFlow(StoreAndProduct storeAndProduct, String day) {
		if (storeAndProduct.forAllStores()){
			return flowRepository.findHourAggregationByLineNumberAndDay(storeAndProduct.getLineNumber(), day);
		}else{
		return flowRepository.findHourAggregationByLineNumberAndStoreNumberAndDay(storeAndProduct.getLineNumber(), storeAndProduct.getStoreNumber(), day);	
		}
	}

}
