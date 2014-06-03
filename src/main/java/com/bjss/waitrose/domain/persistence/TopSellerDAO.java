package com.bjss.waitrose.domain.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class TopSellerDAO {
	
	private JdbcOperations jdbcTemplate;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public TopSellerDAO(JdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Map<String, Object>> getTopSellers(Date from, Date to, int store) {
		String sql = "SELECT ld.line_num,p.long_name,sum(total) AS sales FROM linedata ld ";
		sql += "INNER JOIN products p ON p.line_num = ld.line_num ";
		sql += "WHERE date between '" + df.format(from) + "' AND '" + df.format(to) + "' ";
		sql += "AND store = " + store;
		sql += " GROUP BY line_num ";
		sql += "ORDER BY sales DESC ";
		sql += "LIMIT 0, 50; ";

		return jdbcTemplate.queryForList(sql);
	}
}
