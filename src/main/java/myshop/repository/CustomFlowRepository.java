package myshop.repository;

import java.util.Date;
import java.util.Set;

import myshop.domain.persistence.Flow;

public interface CustomFlowRepository {

	Set<Flow> findByLineNumberAndStoreNumberAndDateBetween(int lineNumber, int storeNumber, Date from, Date to);
	
	Flow findByLineNumberAndStoreNumberAndDate(int lineNumber, int storeNumber, Date date);

	Set<Flow> findByLineNumberAndDateBetween(int lineNumber, Date from, Date to, int pageNumber);
}
