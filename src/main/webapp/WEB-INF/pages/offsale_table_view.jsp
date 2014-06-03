<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Availability Table View</title>
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
</head>
<body>
	<h1 class="wrTitle">Off sale candidates for ${storeName} from ${fromDate} to ${toDate} (${rdcName})</h1>

	<table class="waitroseTable">
		<tr>	
			<th>Line number</th>
			<th>Description</th>
			<th>Date</th>
			<th>&nbsp;</th>
		</tr>
	
		<c:forEach var="os" items="${offSales}">
			<tr>
				<td>${os.lineNum}</td>
				<td>${os.lineName}</td>
				<td>${os.date} (${os.day})</td>
				<td><a href="product/${os.lineNum}?autoDisplay=true&dateFrom=${os.date}&dateTo=${os.date}&storeNumber=${storeId}">View detail</a>
			</tr>
		</c:forEach>
	</table>
</body>
</html>