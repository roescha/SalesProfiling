package com.bjss.waitrose.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bjss.waitrose.domain.OffSaleDetailTo;
import com.bjss.waitrose.domain.OffSaleDisplayTO;
import com.bjss.waitrose.service.OffSalesService;

import flexjson.JSONSerializer;

@Controller
public class OffSaleController {
	
	@Inject
	private OffSalesService offsaleService;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
	private flexjson.JSONSerializer jsonSerializer = new JSONSerializer();
	
	@RequestMapping(value = "heatMap", method = RequestMethod.GET)
	public ModelAndView getHeatMap() {
		ModelMap model = new ModelMap();
		return new ModelAndView("offsales", model);
	}
	
	@RequestMapping(value = "tableView", method = RequestMethod.GET)
	public ModelAndView getTableView(@RequestParam int storeId, @RequestParam String fromDate, @RequestParam String toDate) {
		ModelMap model = new ModelMap();
		
		Date startDate;
		Date endDate;
		try {
			startDate = df.parse(fromDate);
			endDate = df.parse(toDate);
		} catch (ParseException pe) {
			model.addAttribute("message", "Dates are invalid. You entered " + fromDate + " to " + toDate);
			return new ModelAndView("error_page", model);
		}
		List<OffSaleDetailTo> tos = offsaleService.getOffSaleDetails(storeId, startDate, endDate);
		model.addAttribute("offSales",tos);
		model.addAttribute("storeId", storeId);
		if (tos != null && tos.size() > 0) {
			model.addAttribute("storeName", tos.get(0).getStoreName());
			model.addAttribute("rdcName", tos.get(0).getRdcName());
		}
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		return new ModelAndView("offsale_table_view", model);
	}
	
	@RequestMapping(value = "heatMap", method = RequestMethod.POST)
	public ModelAndView getHeatMapResponse(@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String displayBy) {
		ModelMap model = new ModelMap();
		
		Date startDate;
		Date endDate;
		try {
			startDate = df.parse(fromDate);
			endDate = df.parse(toDate);
		} catch (ParseException pe) {
			model.addAttribute("message", "Dates are invalid. You entered " + fromDate + " to " + toDate);
			return new ModelAndView("error_page", model);
		}
		
		List<OffSaleDisplayTO> offSales;
		if (displayBy.equalsIgnoreCase("byBranch")) {
			offSales = offsaleService.getOffSaleStats(startDate, endDate);
		} else {
			offSales = offsaleService.getOffSaleStatsForRDC(startDate, endDate);
		}
		model.addAttribute("displayRDC", displayBy.equalsIgnoreCase("byrdc"));
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		
		model.addAttribute("offSales", jsonSerializer.serialize(offSales));
		
		return new ModelAndView("offsale_heat_map", model);
	}
}
