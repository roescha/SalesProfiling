package myshop.service;

import java.util.Date;
import java.util.List;

import myshop.domain.OffSaleDetailTo;
import myshop.domain.OffSaleDisplayTO;

public interface OffSalesService {
	
	public List<OffSaleDisplayTO> getOffSaleStats(Date startDate, Date endDate);
	public List<OffSaleDetailTo> getOffSaleDetails(int storeId, Date startDate, Date endDate);
	public List<OffSaleDisplayTO> getOffSaleStatsForRDC(Date startDate, Date endDate);
	public List<OffSaleDetailTo> getOffSaleDetails(int storeId, Date startDate,
			Date endDate, int lineNum);
	
}
