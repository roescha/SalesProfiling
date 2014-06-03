<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.wrTitle {
	color: #79ac36;
	text-align: center;
	position: relative;
	top: 200px;
}

.detailTable {
	margin-left: auto;
	margin-right: auto;
	border: solid;
	border-color: black;
	padding: 20px;
	align: center;
	position: relative;
	top: 200px;
}
</style>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="js/jquery.corner.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Top Sellers</title>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="js/jquery.corner.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#fromDate").datepicker({
			dateFormat : 'dd/mm/yy'
		});

		$("#toDate").datepicker({
			dateFormat : 'dd/mm/yy'
		});
		
		$('#enterOptionsTable').corner();
	});
	
</script>
</head>
<body>
	<h1 class="wrTitle">Waitrose Top Sellers</h1>

	<form:form method="post" action="topSeller" modelAttribute="topSeller">
		<table id="enterOptionsTable" class="detailTable">
			<tr>
				<td>Select Store</td>
				<td><form:select id="selectedStore" path="selectedStore"
						items="${branches}" itemValue="number" itemLabel="name" /></td>
			</tr>
			<tr>
				<td>From:</td>
				<td><form:input type="text" path="fromDate" /></td>
			</tr>
			<tr>
				<td>To:</td>
				<td><form:input type="text" path="toDate" /></td>
			</tr>
			<tr>
				<td colspan="2"><form:button type="submit">Start</form:button>
				</td>
			</tr>
		</table>
	</form:form>

</body>
</html>