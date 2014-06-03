package com.bjss.waitrose.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bjss.waitrose.domain.persistence.Flow;

@Transactional(readOnly=true)
public interface FlowRepository extends JpaRepository<Flow, String>, CustomFlowRepository {
	
	@Cacheable("flows")
	Set<Flow> findByLineNumberAndDate(int lineNumber, Date from);

	@Cacheable("range")
	Page<Flow> findByLineNumberAndDateBetween(int lineNumber, Date from, Date to, Pageable pageable);

	@Cacheable("aggregation")
	@Query(value = "select 0 as id, '2013-01-01' as date, 'All' as day, line_num, 0 as store, sum(hour0) as hour0, sum(hour1) as hour1, sum(hour2) as hour2, sum(hour3) as hour3, sum(hour4) as hour4, sum(hour5) as hour5, sum(hour6) as hour6, sum(hour7) as hour7, sum(hour8) as hour8, sum(hour9) as hour9, sum(hour10) as hour10, sum(hour11) as hour11, sum(hour12) as hour12, sum(hour13) as hour13, sum(hour14) as hour14, sum(hour15) as hour15, sum(hour16) as hour16, sum(hour17) as hour17, sum(hour18) as hour18, sum(hour19) as hour19, sum(hour20) as hour20, sum(hour21) as hour21, sum(hour22) as hour22, sum(hour23) as hour23, count(total) as total from linedata where line_num = ?1", nativeQuery = true)
	Flow findHourAggregationByLineNumber(int lineNumber);

	@Cacheable("aggregation")
	@Query(value = "select 0 as id, '2013-01-01' as date, 'All' as day, line_num, store, sum(hour0) as hour0, sum(hour1) as hour1, sum(hour2) as hour2, sum(hour3) as hour3, sum(hour4) as hour4, sum(hour5) as hour5, sum(hour6) as hour6, sum(hour7) as hour7, sum(hour8) as hour8, sum(hour9) as hour9, sum(hour10) as hour10, sum(hour11) as hour11, sum(hour12) as hour12, sum(hour13) as hour13, sum(hour14) as hour14, sum(hour15) as hour15, sum(hour16) as hour16, sum(hour17) as hour17, sum(hour18) as hour18, sum(hour19) as hour19, sum(hour20) as hour20, sum(hour21) as hour21, sum(hour22) as hour22, sum(hour23) as hour23, count(total) as total from linedata where line_num = ?1 and store = ?2", nativeQuery = true)
	Flow findHourAggregationByLineNumberAndStoreNumber(int lineNumber, int storeNumber);

	@Cacheable("aggregation")
	@Query(value = "select 0 as id, '2013-01-01' as date, day, line_num, 0 as store, sum(hour0) as hour0, sum(hour1) as hour1, sum(hour2) as hour2, sum(hour3) as hour3, sum(hour4) as hour4, sum(hour5) as hour5, sum(hour6) as hour6, sum(hour7) as hour7, sum(hour8) as hour8, sum(hour9) as hour9, sum(hour10) as hour10, sum(hour11) as hour11, sum(hour12) as hour12, sum(hour13) as hour13, sum(hour14) as hour14, sum(hour15) as hour15, sum(hour16) as hour16, sum(hour17) as hour17, sum(hour18) as hour18, sum(hour19) as hour19, sum(hour20) as hour20, sum(hour21) as hour21, sum(hour22) as hour22, sum(hour23) as hour23, count(total) as total from linedata where line_num = ?1 and day = ?2", nativeQuery = true)
	Flow findHourAggregationByLineNumberAndDay(int lineNumber, String day);

	@Cacheable("aggregation")
	@Query(value = "select 0 as id, '2013-01-01' as date, day, line_num, store, sum(hour0) as hour0, sum(hour1) as hour1, sum(hour2) as hour2, sum(hour3) as hour3, sum(hour4) as hour4, sum(hour5) as hour5, sum(hour6) as hour6, sum(hour7) as hour7, sum(hour8) as hour8, sum(hour9) as hour9, sum(hour10) as hour10, sum(hour11) as hour11, sum(hour12) as hour12, sum(hour13) as hour13, sum(hour14) as hour14, sum(hour15) as hour15, sum(hour16) as hour16, sum(hour17) as hour17, sum(hour18) as hour18, sum(hour19) as hour19, sum(hour20) as hour20, sum(hour21) as hour21, sum(hour22) as hour22, sum(hour23) as hour23, count(total) as total from linedata where line_num = ?1 and store = ?2 and day = ?3", nativeQuery = true)
	Flow findHourAggregationByLineNumberAndStoreNumberAndDay(int lineNumber, int storeNumber, String day);
	
	@Cacheable("storelist")
	@Query("select distinct f.storeNumber from Flow f where f.lineNumber = ?1")
	List<Integer> findStoreNumbersForProduct(int lineNumber);
}
