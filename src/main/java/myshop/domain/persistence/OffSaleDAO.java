package myshop.domain.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class OffSaleDAO {

	private JdbcOperations jdbcTemplate;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public OffSaleDAO(JdbcOperations jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Map<String, Object>> getListOfOffSales(int storeId, Date from, Date to) {
		String sql = "select ld.line_num, p.long_name, ld.date, ld.day, ld.store, br.name AS storeName, br.rdc_num, r.name AS rdcName, ld.total ";
		sql += "FROM offsale_candidates oc ";
		sql += "INNER JOIN linedata ld ON ld.id = oc.candidate_id ";
		sql += "INNER JOIN products p ON p.line_num = ld.line_num ";
		sql += "INNER JOIN branches br ON br.id = ld.store ";
		sql += "INNER JOIN rdc r ON r.id = br.rdc_num ";
		sql += "WHERE br.id = " + storeId;
		sql += " AND date >= '" + df.format(from) + "' AND date <= '" + df.format(to) + "' order by ld.total desc;";

		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> getListOfOffSalesForLineItem(int storeId,
			Date from, Date to, int lineNum) {
		String sql = "select ld.line_num, p.long_name, ld.date, ld.day, ld.store, br.name AS storeName, br.rdc_num, r.name AS rdcName, ld.total ";
		sql += "FROM offsale_candidates oc ";
		sql += "INNER JOIN linedata ld ON ld.id = oc.candidate_id ";
		sql += "INNER JOIN products p ON p.line_num = ld.line_num ";
		sql += "INNER JOIN branches br ON br.id = ld.store ";
		sql += "INNER JOIN rdc r ON r.id = br.rdc_num ";
		sql += "WHERE br.id = " + storeId;
		sql += " AND ld.line_num = " + lineNum;
		sql += " AND date >= '" + df.format(from) + "' AND date <= '" + df.format(to) + "' order by ld.total desc;";

		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> getHeatMapData(Date from, Date to) {

		String sql = "select store.id, count(ld.line_num) AS num_offsale, store.name, store.lat, store.lng from offsale_candidates oc ";
		sql += "INNER JOIN linedata ld ON ld.id = oc.candidate_id ";
		sql += "INNER JOIN branches store ON store.id = ld.store ";
		sql += "INNER JOIN products p ON p.line_num = ld.line_num ";
		sql += "WHERE ld.date >= '" + df.format(from) + "' AND date <= '" + df.format(to) + "' ";
		sql += "GROUP BY store.name;";

		return jdbcTemplate.queryForList(sql);

	}

	public List<Map<String, Object>> getHeatMapDataByRDC(Date from, Date to) {

		String sql = "select rdc.id, count(ld.line_num) AS num_offsale, rdc.name, rdc.lat, rdc.lng from offsale_candidates oc ";
		sql += "INNER JOIN linedata ld ON ld.id = oc.candidate_id ";
		sql += "INNER JOIN branches store ON store.id = ld.store ";
		sql += "INNER JOIN rdc ON rdc.id = store.rdc_num ";
		sql += "INNER JOIN products p ON p.line_num = ld.line_num "; 
		sql += "WHERE ld.date >= '" + df.format(from) + "' AND date <= '" + df.format(to) + "' ";
		sql += "GROUP BY rdc.id; ";

		return jdbcTemplate.queryForList(sql);
	}

	public long countOffSaleCandidates(Date fromDate, Date toDate, int storeId, long lineNum) {
		String sql = "SELECT count(*) AS num from offsale_candidates ";
		sql += "WHERE candidate_id IN ( "; 
		sql += "SELECT id FROM linedata ";
		sql += "		WHERE date between '" + df.format(fromDate) + "' AND '" + df.format(toDate) + "' ";
		sql += "AND store = " + storeId;
		sql += " AND line_num = " + lineNum + ");";
		
		return (long)jdbcTemplate.queryForList(sql).get(0).get("num");
	}

}
