package com.bjss.waitrose.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bjss.waitrose.domain.OffSaleDetailTo;
import com.bjss.waitrose.domain.TopSellerDisplayTO;
import com.bjss.waitrose.domain.persistence.OffSaleDAO;
import com.bjss.waitrose.domain.persistence.Store;
import com.bjss.waitrose.domain.persistence.TopSellerDAO;
import com.bjss.waitrose.service.OffSalesService;
import com.bjss.waitrose.service.StoreService;

@Controller
public class TopSellerController {
	
	@Inject private StoreService storeService;
	@Inject private TopSellerDAO topSellerDAO;
	@Inject private OffSaleDAO offSaleDAO;
	@Inject OffSalesService offsaleService;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@ModelAttribute("topSeller")
	public Result getResult() {
		return new Result();
	}
	
	@RequestMapping(value = "topSeller", method = RequestMethod.GET)
	public ModelAndView getOffSaleMainPage() {
		ModelMap model = new ModelMap();
		
		List<Store> stores = storeService.getAllStores();
		Collections.sort(stores);
		model.addAttribute("branches", stores);
		return new ModelAndView("topSellerMain", model);
	}
	
	@RequestMapping(value = "topSeller", method = RequestMethod.POST)
	public ModelAndView processOffSaleMainPage(@ModelAttribute Result result) {
		ModelMap model = new ModelMap();
		Store s = storeService.getStoreByStoreNumber(result.getSelectedStore());
		model.addAttribute("branch", s.getName());
		model.addAttribute("branchId", result.getSelectedStore());
		model.addAttribute("from", result.getFromDate());
		model.addAttribute("to", result.getToDate());
		
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = df.parse(result.getFromDate());
			toDate = df.parse(result.getToDate());
		} catch (ParseException pe) {}
		
		List<TopSellerDisplayTO> outputList = new ArrayList<>();
		List<Map<String, Object>> topSellers = topSellerDAO.getTopSellers(fromDate, toDate, result.getSelectedStore());
		
		for (Map<String, Object> obj : topSellers) {
			long lineNum = (int)obj.get("line_num");
			String name = (String)obj.get("long_name");
			BigDecimal salesForPeriod = (BigDecimal)obj.get("sales");
			int numOffSale = (int)offSaleDAO.countOffSaleCandidates(fromDate, toDate, result.getSelectedStore(), lineNum);
			outputList.add(new TopSellerDisplayTO(lineNum, name, salesForPeriod.intValue(), numOffSale));
		}
		
		model.addAttribute("topSellers" , outputList);
		return new ModelAndView("viewTopSellers", model);
	}
	
	@RequestMapping(value="viewOffSales", method = RequestMethod.GET)
	public ModelAndView getOffSaleList(@RequestParam int lineNum, 
			@RequestParam int storeId,
			@RequestParam String dateFrom, 
			@RequestParam String dateTo) {
		
		ModelMap model = new ModelMap();
		Date startDate;
		Date endDate;
		try {
			startDate = df.parse(dateFrom);
			endDate = df.parse(dateTo);
		} catch (ParseException pe) {
			model.addAttribute("message", "Dates are invalid. You entered " + dateFrom + " to " + dateTo);
			return new ModelAndView("error_page", model);
		}
		List<OffSaleDetailTo> tos = offsaleService.getOffSaleDetails(storeId, startDate, endDate, lineNum);
		
		model.addAttribute("offSales",tos);
		model.addAttribute("storeId", storeId);
		if (tos != null && tos.size() > 0) {
			model.addAttribute("storeName", tos.get(0).getStoreName());
			model.addAttribute("rdcName", tos.get(0).getRdcName());
		}
		model.addAttribute("fromDate", dateFrom);
		model.addAttribute("toDate", dateTo);
		return new ModelAndView("offsale_table_view", model);
		
	}
}

class Result {
	private int selectedStore;
	private String fromDate;
	private String toDate;

	public int getSelectedStore() {
		return selectedStore;
	}

	public void setSelectedStore(int selectedStore) {
		this.selectedStore = selectedStore;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
}

