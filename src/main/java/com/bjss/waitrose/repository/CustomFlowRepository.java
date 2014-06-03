package com.bjss.waitrose.repository;

import java.util.Date;
import java.util.Set;

import com.bjss.waitrose.domain.persistence.Flow;

public interface CustomFlowRepository {

	Set<Flow> findByLineNumberAndStoreNumberAndDateBetween(int lineNumber, int storeNumber, Date from, Date to);
	
	Flow findByLineNumberAndStoreNumberAndDate(int lineNumber, int storeNumber, Date date);

	Set<Flow> findByLineNumberAndDateBetween(int lineNumber, Date from, Date to, int pageNumber);
}
