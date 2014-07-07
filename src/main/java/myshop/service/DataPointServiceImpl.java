package myshop.service;

import java.util.Collections;
import java.util.List;

import myshop.domain.DataPoint;
import myshop.domain.DateRange;
import myshop.domain.StoreAndProduct;
import myshop.domain.TimeScale;
import myshop.domain.persistence.Flow;
import myshop.helper.calculator.DataPointCalculator;
import myshop.helper.calculator.DataPointCalculatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPointServiceImpl implements DataPointService {

	private final FlowService flowService;

	@Autowired
	public DataPointServiceImpl(FlowService flowService) {
		this.flowService = flowService;
	}

	@Override
	public List<DataPoint> buildProfile(StoreAndProduct storeAndProduct,
			DateRange dates,
			TimeScale timeScale) {
		if (dates.forDateRange() && storeAndProduct.forAllStores()){
			return buildProfileForPagedFlows(storeAndProduct, dates, timeScale);
		}else{
			return buildProfileForOneLoadOfFlows(storeAndProduct, dates, timeScale);
		}		
	}

	@Override
	public List<DataPoint> buildProfileFromAggregation(	StoreAndProduct storeAndProduct,
			TimeScale timeScale,
			boolean isDayComparison,
			String day) {

		Flow flow = isDayComparison ? flowService.getAggregatedDayFlow(storeAndProduct, day) : flowService
				.getAggregatedFlow(storeAndProduct);

		if (flow == null)
			flow = new Flow();

		// hack as for averages, repository returns the total aggregated in the
		// total field, so it has different meaning in here
		DataPointCalculator calculator = DataPointCalculatorFactory.getCalculator(timeScale, flow.getTotal());
		return calculator.calculate(Collections.singletonList(flow));
	}


	private List<DataPoint> buildProfileForPagedFlows(StoreAndProduct storeAndProduct,
			DateRange dates,
			TimeScale timeScale) {

		DataPointCalculator calculator = DataPointCalculatorFactory.getCalculator(timeScale);

		List<DataPoint> pagePoints;
		List<DataPoint> allPoints = null;

		//Page<Flow> page = null;
		List<Flow> flows = null;
		boolean morePagesLeft = true;
		int pageNumber = 0;

		while (morePagesLeft) {			
			flows = getPagedFlows(storeAndProduct, dates, pageNumber);
			if (!flows.isEmpty()){
				pagePoints = calculator.calculate(flows);
				allPoints = calculator.merge(allPoints, pagePoints);	
				morePagesLeft = true;
				++pageNumber;			
			}else{
				morePagesLeft = false;
			}			
		}

		if (allPoints == null) allPoints = Collections.emptyList();

		return allPoints;
	}


	private List<DataPoint> buildProfileForOneLoadOfFlows(StoreAndProduct storeAndProduct, DateRange dates, TimeScale timeScale){
		List<Flow> flows = getFlows(storeAndProduct, dates);
		DataPointCalculator calculator = DataPointCalculatorFactory.getCalculator(timeScale);
		return calculator.calculate(flows);
	}

	private List<Flow> getPagedFlows(StoreAndProduct storeAndProduct, DateRange dates, int pageNumber){
		List<Flow> flows = flowService.getHourlyFlowsOnADateRangeForAllStores(
				storeAndProduct, dates, pageNumber);
		return flows;
	}

	private List<Flow> getFlows(StoreAndProduct storeAndProduct, DateRange dates) {
		return dates.forOneDay() ? flowService.getHourlyFlowsOnAGivenDate(storeAndProduct, dates.getStart())
				: flowService.getHourlyFlowsOnADateRangeForSingleStore(storeAndProduct, dates);
	}
}
