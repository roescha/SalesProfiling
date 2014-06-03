<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Off Sales menu</title>
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

	<h1 class="wrTitle">Select off sale heat map options</h1>
	<form action="heatMap" method="post">
		<table id="enterOptionsTable" class="detailTable">
			<tr>
				<th>From:</th>
				<td><input type="text" id="fromDate" name="fromDate"placeholder="dd/mm/yy" /></td>
			</tr>
			<tr>
				<th>To:</th>
				<td><input type="text" id="toDate" name="toDate" placeholder="dd/mm/yy" /></td>
			</tr>
			
			<tr>
				<th>Display by:</th>
				<td><select name="displayBy" id="displayBy">
					<option value="byrdc">By RDC</option>
					<option value="byBranch">By Branch</option>
				</select></td>
			</tr>
			
			<tr>
				<th>&nbsp;</th>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form>
</body>
</html>