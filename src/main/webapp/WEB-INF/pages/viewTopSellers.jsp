<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
.waitroseTable th {
	background-color: #cccccc;
}
.waitroseTable td {
	background-color: #66FF66;
	
}

.wrTitle {
     	font-size:22px;
		color: #79ac36;
		text-align: center;
		font-weight: bold;
	}
</style>
<title>Top Sellers</title>
</head>
<body>
	<h1 class="wrTitle">Top sellers</h1>
	<h1 class="wrTitle">${branch} from ${from} to ${to}.</h1>

	<table class="waitroseTable">
		<tr>
			<th>Line number</th>
			<th>Description</th>
			<th>Sales for period</th>
			<th>Off-sale periods</th>
		</tr>
		<c:forEach var="ts"  items="${topSellers}">
			<tr>
				<td>${ts.lineNumber}</td>
				<td>${ts.name}</td>
				<td>${ts.salesForPeriod}</td>
				<td>${ts.numOffSale} <c:if test="${ts.numOffSale > 0}"> (<a href="viewOffSales?lineNum=${ts.lineNumber}&storeId=${branchId}&dateFrom=${from}&dateTo=${to}">View</a>)</c:if></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>