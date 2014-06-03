package com.bjss.waitrose.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import com.bjss.waitrose.domain.OffSaleDetailTo;
import com.bjss.waitrose.domain.OffSaleDisplayTO;
import com.bjss.waitrose.domain.persistence.OffSaleDAO;

@Service
public class OffSaleServiceImpl implements OffSalesService {

	private OffSaleDAO dao;
	private Logger logger = Logger.getLogger(this.getClass());
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	public OffSaleServiceImpl(OffSaleDAO dao){
		this.dao = dao;
	}

	@Override
	public List<OffSaleDisplayTO> getOffSaleStats(Date startDate, Date endDate) {

		List<OffSaleDisplayTO> offSales = new ArrayList<>();
		List<Map<String, Object>> obs = dao.getHeatMapData(startDate, endDate);

		for (Map<String, Object> map : obs) {
			int id = (int)map.get("id");
			long noOffsales = (long)map.get("num_offsale");
			String name = (String)map.get("name");
			BigDecimal latitude = (BigDecimal)map.get("lat");
			BigDecimal longitude = (BigDecimal)map.get("lng");
			offSales.add(new OffSaleDisplayTO(id, name, latitude.doubleValue(), longitude.doubleValue(), (int)noOffsales));
		}

		return offSales;
	}

	@Override
	public List<OffSaleDetailTo> getOffSaleDetails(int storeId, Date startDate, Date endDate) {

		List<OffSaleDetailTo> offSalesDetails = new ArrayList<>();
		List<Map<String, Object>> obs = dao.getListOfOffSales(storeId, startDate, endDate);

		for (Map<String, Object> map : obs) {

			String lineName = (String)map.get("long_name");
			int lineNum = (int)map.get("line_num");
			String storeName = (String)map.get("storename");
			String rdcName = (String)map.get("rdcName");
			String date = df.format((Date)map.get("date"));
			String day = (String)map.get("day");
			
			offSalesDetails.add(new OffSaleDetailTo(storeId, lineNum, lineName, date, day, storeName, rdcName));
		}

		return offSalesDetails;
	}
	
	@Override
	public List<OffSaleDetailTo> getOffSaleDetails(int storeId, Date startDate,
			Date endDate, int lineNum) {
		List<OffSaleDetailTo> offSalesDetails = new ArrayList<>();
		List<Map<String, Object>> obs = dao.getListOfOffSalesForLineItem(storeId, startDate, endDate, lineNum);

		for (Map<String, Object> map : obs) {

			String lineName = (String)map.get("long_name");
			int lineNumber = (int)map.get("line_num");
			String storeName = (String)map.get("storename");
			String rdcName = (String)map.get("rdcName");
			String date = df.format((Date)map.get("date"));
			String day = (String)map.get("day");
			
			offSalesDetails.add(new OffSaleDetailTo(storeId, lineNumber, lineName, date, day, storeName, rdcName));
		}
		
		return offSalesDetails;
	}

	@Override
	public List<OffSaleDisplayTO> getOffSaleStatsForRDC(Date startDate,
			Date endDate) {
		List<OffSaleDisplayTO> offSales = new ArrayList<>();
		List<Map<String, Object>> obs = dao.getHeatMapDataByRDC(startDate, endDate);

		for (Map<String, Object> map : obs) {
			int id = (int)map.get("id");
			long noOffsales = (long)map.get("num_offsale");
			String name = (String)map.get("name");
			BigDecimal latitude = (BigDecimal)map.get("lat");
			BigDecimal longitude = (BigDecimal)map.get("lng");
			offSales.add(new OffSaleDisplayTO(id, name, latitude.doubleValue(), longitude.doubleValue(), (int)noOffsales));
		}

		return offSales;
	}

}
