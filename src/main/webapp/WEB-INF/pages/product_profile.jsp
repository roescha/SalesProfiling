<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Product Profile Page</title>
<style>
div h2 {
	text-align: center;
	color: navy;
	font-weight: bold;
	font-family: cursive;
}

th {
	text-align: left;
}

div#wrapper {
	margin-left: auto;
	margin-right: auto; 
	height: 480pt;
	width: 980pt;
	border: 1px double navy;
	padding-bottom: 5pt;
	background-color: #F5F5DC;
	padding-left: 5pt;
	padding-right: 5pt;
	font-size: 9pt;
}

div#leftwrapper,div#rightwrapper {
	height: 250pt;
	width: auto;
	border: 1px double navy;
	padding-bottom: 20pt;
	backgroun-color: #F5F5DC;
	float: left;
}

div#leftwrapper{
	margin-left: auto;
	margin-right: auto; 
	padding-left: 5pt;
	padding-right: 5pt;
	width: 240pt;
}

div#rightwrapper {
  margin-left: 5pt; 
	width: 700pt;
	height: 400pt;
	padding-left: 5pt;
	padding-right: 5pt;
}

div#chartDiv {
	backgroundColor: #F5F5DC;
	width: 630pt;
}

div#scaleToHide, div#compareToHide, div#averageToHide, div#weekToHide, div#monthToHide{
display:inline;
}

div#graphValueViewOptionsDiv, div#profileScaleDiv{
	font-size:7pt;
	width:auto;
	display:inline;
	float:left;
}

div#homeLink, div#logout{
	margin-top: 5pt; 
	margin-left: 5pt;
	font-size:9pt;
	width:auto;
  color: green;
	font-weight: bold;
	font-family: cursive;
}

div#comparisonDiv{
	font-size:7pt;
	width:auto;
  display:inline;
	float:right;
}

div#progressMessageDiv{
	width:auto;
  display:inline;
	float:right;
	border:thin;
	text-align:center;
}

h3#progressMessage{
	font-size:12pt;
	color:red;	
}

input[name="compareTo"]{
 font-size:7pt;
	margin-right: 3pt; 
}

table#flowsTable {
	border-collapse: collapse;
	width: 630pt;
}

table#flowsTable th {
	border: 1px solid navy;
	color: blue;
	text-align: center;
}

table#flowsTable td {
	border: 1px dotted navy;
	color: red;
	font-family: Verdana;
	text-align: center;
}

legend {
	text-align: center;
	color: navy;
	font-weight: bold;
	font-family: cursive;
}

input#dateFrom{
	width:90pt;
	margin-right: 5pt;
	margin-bottom: 5pt;
}

input[type="label"] {
	display: inline;
	}
	
	input[type=radio]{
	display:inline;
	 }
 
legend#profileDetailsLegend {
	text-align: left;
}

fieldset {
	width: auto;
	border: 1px solid navy;
	font-family: cursive;
	overflow:hidden;
}

fieldset#profileDetailsFieldset {
	border: none;
}

#ui-datepicker-div { font-size:11px; }


</style>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script type="text/javascript">
	$(document).ready(
			function() {
				
				var graphData = "";
				var graphDataAvg = "";
			 	var graphDataMax = "";
				var graphDataMin = ""; 		
				var labels = {};
				var range = "";
				var tableHeaders = "";
				var tableContent = "";
				
				var compareGraphData = "";
				var compareGraphDataAvg = "";
				var profileDatesLabel = "";
				var comparisonDatesLabel = "";
				var fromDate = "";
							
						$("#dateFrom").datepicker({
							dateFormat : 'dd/mm/yy'
						});

						$('#rightwrapper').hide();
						$('#prevButton').hide();
						$('#nextButton').hide();
						
						$('#prevButton').click(function() {
							event.preventDefault();
							if (fromDate){							
								$.when(findAndChangeDate($('#prevButton').val())).done(function(a1){				
									$('#profileOptionsForm').submit();	
								});
							}
						});
						
						$('#nextButton').click(function() {
							event.preventDefault();
							if (fromDate){							
								$.when(findAndChangeDate($('#nextButton').val())).done(function(a1){				
									$('#profileOptionsForm').submit();	
								});								
							}
						});


						$('input[name=graphView]').click(function() {

							setupAndDrawChart();
						});

						$('input[name=profileScale]').click(function() {
							$('#profileScaleForm').submit();
						});

						$('#profileScaleForm').submit(function(event) {
							event.preventDefault();
							submitProfileForm();
						});

						$('input[name=compareTo]').click(function() {
							$('#compareToForm').submit();
						});

						$('#compareToForm').submit(function(event) {
							event.preventDefault();
							submitCompareForm();
						});

						$('#profileOptionsForm').submit(function(event) {
							event.preventDefault();
							$('#doButton').val("Retrieving....");
							$('#doButton').attr("disabled", true);
							/* $('input[name=profileScale]:nth(0)').prop('checked', true); */
							range =  $('input[name=rangeOptions]:checked').val();
							if (range == 'DAY'){
								$('input[name=profileScale]:nth(0)').prop('checked', true);	
							}else if (range == 'WEEK'){
								$('input[name=profileScale]:nth(1)').prop('checked', true);	
							}else if (range == 'MONTH'){
								$('input[name=profileScale]:nth(2)').prop('checked', true);	
							}
							else if (range == 'SEMESTER'){
								$('input[name=profileScale]:nth(4)').prop('checked', true);	
							} 
							fromDate = $('#dateFrom').val();
							$('#prevButton').show();
							$('#nextButton').show();
							$('#prevButton').attr("disabled", true);
							$('#nextButton').attr("disabled", true);
							submitProfileForm();
						});
						
						function findAndChangeDate(direction){
							var dateFrom = $('#dateFrom').val();
							if (dateFrom){
								var json = {
										"dateFrom" : dateFrom,
										"dateTo" : $('input[name=rangeOptions]:checked').val(),
										"direction" : direction
								};
								
								return $.ajax({
									url : "${pageContext.request.contextPath}/profile/findShiftedDate",
									data : json,
									timeout : 300000,
								}).done(function(date) {
									$('#dateFrom').val(date);	
									fromDate = date;
								}).fail(function(jqXHR, textStatus) {
									alert("error setting date: " + textStatus);
								}).always(function() {				
								});							
							}							
						}
						
						function findProfileDatesLabel(){
							var dateFrom = fromDate;
							if (dateFrom){
								var json = {
										"dateFrom" : dateFrom,
										"dateTo" : range
								};
								
								return $.ajax({
									url : "${pageContext.request.contextPath}/profile/findEndDate",
									data : json,
									timeout : 300000,
								}).done(function(datesLabel) {
									profileDatesLabel = datesLabel;		
								}).fail(function(jqXHR, textStatus) {
									alert("error setting date: " + textStatus);
								}).always(function() {				
								});							
							}							
						}
						
						function findComparisonDatesLabel(){
								var json = {
										"dateFrom" : fromDate,
										"dateTo" : range,
										"profileScale" : $('input[name=profileScale]:checked').val(),
										"compare" : $('input[name=compareTo]:checked').val()
									};
								
								return $.ajax({
									url : "${pageContext.request.contextPath}/profile/findComparisonDates",
									data : json,
									timeout : 300000,
								}).done(function(dateLabel) {
									comparisonDatesLabel = 	dateLabel;
								}).fail(function(jqXHR, textStatus) {
									alert("error setting date: " + textStatus);
								}).always(function() {				
								});													
						}


						function submitProfileForm() {

							hideOrShowScaleAndComparisonOptions();
							
							var json = {
								"dateFrom" : fromDate,
								"dateTo" : range,
								"storeNumber" : $('#storeNumber').val(),
								"profileScale" : $('input[name=profileScale]:checked').val()
							};

							$.ajax({
								url : $('#profileOptionsForm').attr("action"),
								data : json,
								timeout : 300000,
							}).done(function(flows) {

								$('input[name=compareTo]:checked').prop('checked', false);
								
								var totalNumeric = 0;

								tableContent = "";
								graphData = "";
								graphDataAvg = "";
								graphDataMax = "";
								graphDataMin = "";
								totalNumeric = 0;
								labelStr = "";

								compareGraphData = "";
								compareGraphDataAvg = "";

								$.each($(flows), function(index, value) {
									tableContent += '<td>' + value.sum + '</td>';
									graphData += value.sum + ',';
									graphDataAvg += value.average + ',';
									graphDataMax += value.max + ',';
									graphDataMin += value.min + ',';
									labelStr += JSON.stringify(value.label) + ',';
									totalNumeric += parseInt(value.sum);
								});
								var profileScale = getprofileScale();
								var total = totalNumeric.toString();

								if (profileScale == 'DAY_YEAR') {
									tableContent = '<td>' + total + '</td>';
								} else {
									tableContent = '<td>' + total + '</td>' + tableContent;
								}

								graphData = graphData.replace(/,\s*$/, "");
								graphDataAvg = graphDataAvg.replace(/,\s*$/, "");
								graphDataMax = graphDataMax.replace(/,\s*$/, "");
								graphDataMin = graphDataMin.replace(/,\s*$/, "");

								labelStr = labelStr.replace(/,\s*$/, "");
								labels = JSON.parse("[" + labelStr + "]");

								tableHeaders = getTableHeaders(profileScale);
								setFlowsTable();

								$.when(findProfileDatesLabel()).done(function(a1){				
									setupAndDrawChart();
									$('#rightwrapper').show();	
								});
							

							}).fail(function(jqXHR, textStatus) {
								alert("error drawing chart" + textStatus);
							}).always(function() {
								$('#doButton').val("Generate Profile");
								$('#doButton').attr("disabled", false);
								$('#prevButton').attr("disabled", false);
								$('#nextButton').attr("disabled", false);
								$('#comparisonDiv').show();
								$('#progressMessageDiv').hide();
							});
						}

						function submitCompareForm() {

							addProgressIndicator();

							var compareOption = $('input[name=compareTo]:checked').val();
							if (compareOption == 'RESET') {
								submitProfileForm();
								return;
							}

							var json = {
								"dateFrom" : fromDate,
								"dateTo" : range,
								"storeNumber" : $('#storeNumber').val(),
								"profileScale" : $('input[name=profileScale]:checked').val(),
								"compare" : $('input[name=compareTo]:checked').val()
							};

							$.ajax({
								url : $('#compareToForm').attr("action"),
								data : json,
							}).done(function(flows) {
								var compareTableContent = "";
								var compareTotalNumeric = 0;

								compareTableContent = "";
								compareGraphData = "";
								compareGraphDataAvg = "";
								compareTotalNumeric = 0;

								$.each($(flows), function(index, value) {
									compareTableContent += '<td>' + value.sum + '</td>';
									compareGraphData += value.sum + ',';
									compareGraphDataAvg += value.average + ',';
									compareTotalNumeric += parseInt(value.sum);
								});
								var profileScale = getprofileScale();
								var total = compareTotalNumeric.toString();

								if (profileScale == 'DAY_YEAR') {
									compareTableContent = '<td>' + total + '</td>';
								} else {
									compareTableContent = '<td>' + total + '</td>' + compareTableContent;
								}

								compareGraphData = compareGraphData.replace(/,\s*$/, "");
								compareGraphDataAvg = compareGraphDataAvg.replace(/,\s*$/, "");
								
								var compareTo = $('input[name=compareTo]:checked').val();
								if (compareTo && compareTo != 'AVERAGE_ALL' && compareTo != 'AVERAGE_DAY'){
									setFlowsTableWithComparison(compareTableContent);
								}else{
									setFlowsTable();	
								}
								
								$.when(findComparisonDatesLabel()).done(function(a1){								
									setupAndDrawChart();
								});
								

							}).fail(function(jqXHR, textStatus) {
								alert("error drawing chart " + textStatus);
							}).always(function() {
								$('#comparisonDiv').show();
								$('#progressMessageDiv').hide();
							});
						}

						function setupAndDrawChart() {
							var profileScale = getprofileScale();
							var graphView = getGraphView();
							var series = {};
							if (graphView == 'TOTALS') {
								series = getSeriesForTotals(graphData, compareGraphData);
							} else if (graphView == 'STATS') {
								series = getSeriesForStats(graphDataAvg, graphDataMax, graphDataMin,
										compareGraphDataAvg);
							}

							var titleText = getTitleText(profileScale);
							var yAxisLabel = getYAxisLabel(profileScale);
							var toolTipText = getToolTip(profileScale);
							var storeName = $("#storeNumber option:selected").text();

							drawChart(storeName, labels, titleText, yAxisLabel, toolTipText, series);
						}

						function getprofileScale() {
							return $('input[name=profileScale]:checked').val();
						}

						function getGraphView() {
							return $('input[name=graphView]:checked').val();
						}

						function setFlowsTable() {
							$('#flowsTable thead').html('');
							$('#flowsTable thead').html(tableHeaders);
							$('#flowsTable tbody').html('');
							$('#flowsTable tbody').append("<tr>");
							$('#flowsTable tbody').append(tableContent);
							$('#flowsTable tbody').append("</tr>");
						}
						
						function setFlowsTableWithComparison(compareTableContent) {
							$('#flowsTable thead').html('');
							$('#flowsTable thead').html(tableHeaders);
							$('#flowsTable tbody').html('');
							$('#flowsTable tbody').append("<tr>");
							$('#flowsTable tbody').append(tableContent);
							$('#flowsTable tbody').append("</tr>");
							$('#flowsTable tbody').append("<tr>");
							$('#flowsTable tbody').append(compareTableContent);
							$('#flowsTable tbody').append("</tr>");
						}

						function getSeriesForTotals(graphData, compareGraphData) {
							var series;
							if (!compareGraphData) {
								series = [ {
									name : getMainLabel(),
									data : JSON.parse("[" + graphData + "]")
								} ];
							} else {
								series = [ {
									name : getMainLabel(),
									data : JSON.parse("[" + graphData + "]")
								}, {
									name : getComparisonLabel(),
									data : JSON.parse("[" + compareGraphData + "]")
								} ];

							}
							return series;
						}

						function getSeriesForStats(graphDataAvg, graphDataMax, graphDataMin,
								compareGraphDataAvg) {
							var series;
							if (!compareGraphDataAvg) {
								series = [ {
									name : getMainLabel(),
									data : JSON.parse("[" + graphDataAvg + "]")
								} ];
							} else {
								series = [ {
									name : getMainLabel(),
									data : JSON.parse("[" + graphDataAvg + "]")
								}, {
									name : getComparisonLabel(),
									data : JSON.parse("[" + compareGraphDataAvg + "]")
								} ];
							}
							;

							return series;
						}

						function drawChart(storeName, categoryLabels, titleText, yAxisLabel, toolTipText,
								seriesValues) {

							$('#chartDiv').highcharts({
								title : {
									text : 'Product Sales ( ' + storeName + ')',
									x : -20
								//center
								},
								chart : {
									backgroundColor : '#F5F5DC',
									polar : true,
									type : 'line'
								},

								subtitle : {
									text : 'Source: Waitrose sales data',
									x : -20
								},
								xAxis : {
									categories : categoryLabels,
									title : {
										text : titleText
									}
								},
								yAxis : {
									title : {
										text : yAxisLabel
									},
									plotLines : [ {
										value : 0,
										width : 1,
										color : '#808080'
									} ]
								},
								tooltip : {
									valueSuffix : toolTipText
								},
								legend : {
									layout : 'vertical',
									align : 'center',
									verticalAlign : 'bottom',
									borderWidth : 1
								},
								series : seriesValues
							});
						}

						function getTableHeaders(option) {
							var headers = '';
							if (option == 'HOUR' || option == 'DAY_MONTH') {
								headers = '<th>Total</th>'
										+ '<th>01</th><th>02</th><th>03</th><th>04</th><th>05</th><th>06</th><th>07</th><th>08</th>'
										+ '<th>09</th><th>10</th><th>11</th><th>12</th><th>13</th><th>14</th><th>15</th><th>16</th>'
										+ '<th>17</th><th>18</th><th>19</th><th>20</th><th>21</th><th>22</th><th>23</th><th>24</th>';
							} else if (option == 'DAY_WEEK') {
								headers = '<th>Total</th>'
										+ '<th>Sun</th><th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th>';
							} else if (option == 'MONTH') {
								headers = '<th>Total</th>'
										+ '<th>Jan</th><th>Feb</th><th>Mar</th><th>Apr</th><th>May</th><th>Jun</th><th>Jul</th>'
										+ '<th>Aug</th><th>Sep</th><th>Oct</th><th>Nov</th><th>Dec</th>';
								;
							} else if (option == 'DAY_YEAR') {
								headers = '<th>Total</th>';
							}

							if (option == 'DAY_MONTH') {
								headers += '<th>25</th><th>26</th><th>27</th><th>28</th><th>29</th><th>30</th><th>31</th>';
							}

							return headers;
						}

						function getTitleText(option) {
							var titleText = '';
							if (option == 'HOUR') {
								titleText = 'Hour of the day';
							} else if (option == 'DAY_WEEK') {
								titleText = 'Day of the week';
							} else if (option == 'DAY_MONTH') {
								titleText = 'Day of the month';
							} else if (option == 'DAY_YEAR') {
								titleText = 'Day of the year';
							} else if (option == 'MONTH') {
								titleText = 'Month of the year';
							}
							return titleText;
						}

						function getYAxisLabel(option) {
							return 'Units';
						}

						function getToolTip(option) {
							var tooltip = '';
							if (option == 'HOUR') {
								tooltip = 'Units per hour';
							} else if (option == 'DAY_WEEK') {
								tooltip = 'Units per day';
							} else if (option == 'DAY_MONTH') {
								tooltip = 'Units per day';
							} else if (option == 'DAY_YEAR') {
								tooltip = 'Units per day';
							} else if (option == 'MONTH') {
								tooltip = 'Units per month';
							}
							return tooltip;
						}

						function addProgressIndicator() {
							$('#progressMessageDiv').show();
							$('#comparisonDiv').hide();
						}

						function hideOrShowScaleAndComparisonOptions() {
							if (range == 'DAY') {
								$('#scaleToHide').hide();
								$('#compareToHide').show();
							} else {
								$('#scaleToHide').show();
								$('#compareToHide').hide();
							}
							
							if (range == 'MONTH' || range == 'YEAR' || range == 'SEMESTER'){
								$('#weekToHide').hide();
							}else{
								$('#weekToHide').show();
							}
							
							if ( range == 'YEAR' || range == 'SEMESTER'){
								$('#monthToHide').hide();
							}else{
								$('#monthToHide').show();
							}													
						}

						function getMainLabel() {						 	
							/* var label =  $('#dateFrom').val();
							if (endDate){
								label +=  ' - ' + endDate;	
							}
							return label; */
							return profileDatesLabel;
						}


						function getComparisonLabel() {
							/* var label =  $('input[name=compareTo]:checked').val(); */
							return comparisonDatesLabel;
						}

						doAutoSubmit();
					});
	
	//this is a dodgy hack to display a form if teh parameters are already available
	function doAutoSubmit() {
		
		var autoDisplay = '${autoDisplay}';
		if (autoDisplay) {
			var fromDate = '${dateFrom}';
		/* 	var toDate = '${dateTo}'; */
			var storeNumber = '${storeNumber}';
			$('#dateFrom').val(fromDate);
			$('#dateTo').val('DAY');
			$('#storeNumber').val(storeNumber);
			$('#profileOptionsForm').submit();
		};
		
	};
</script>
</head>
<body>
	<div id="wrapper">
		<h2>
			<span>Product Profile</span>
		</h2>
		<div id="leftwrapper">
			<div id="productDetails">
				<fieldset id="productDetailsfieldset">
					<legend id="productDetailsLegend">Product Details</legend>
					<table id="productDetailsTable">
						<tr>
							<th>Line Name:</th>
							<td>${product.lineNumber}</td>
						</tr>
						<tr>
							<th>Long name:</th>
							<td>${product.longname}</td>
						</tr>
						<tr>
							<th>Short name:</th>
							<td id="productShortname">${product.shortname}</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<div id="enterOptionsDiv">
				<form:form method="GET" id="profileOptionsForm"
					action="${pageContext.request.contextPath}/product/profile/${product.lineNumber}">
					<fieldset id="enterOptionsfieldset">
						<legend id="enterOptionsLegend">Profile options</legend>
						<table id="enterOptionsTable">
							<tr>
								<th>Date:</th>
								<td>
								<input type="text" id="dateFrom" placeholder="dd/mm/yy"/>
								<input id="prevButton" type="button" name="changeDateButton" value="Prev" />
								<input id="nextButton" type="button" name="changeDateButton" value="Next" />
								</td>
							</tr>
							<tr>
							  <th>Range:</th>
								<td>
								<label for="rr1"><input type="radio" name="rangeOptions" value="DAY" id="rr1" checked/>1 d</label>
								<label for="rr2"><input type="radio" name="rangeOptions" value="WEEK" id="rr2"/>1 wk</label>		
								<label for="rr3"><input type="radio" name="rangeOptions" value="MONTH" id="rr3"/>1 mt</label>
								<label for="rr4"><input type="radio" name="rangeOptions" value="SEMESTER" id="rr4"/>6 mts</label>
								</td>
							</tr>
							<tr>
								<th>Store:</th>
								<td><select id="storeNumber">
										<option value=0 selected>All</option>
										<c:forEach var="store" items="${stores}">
											<option value="${store.number}">${store.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>						
							<tr></tr>
							<tr>
								<th></th>
								<td><input id="doButton" type="submit" value="Generate profile" /></td>
							</tr>
						</table>
					</fieldset>
				</form:form>
			</div>
			<div id="homeLink">
				<a href="${pageContext.request.contextPath}/">Back to product search</a>
				<%-- <a href="<spring:url value="/" />">Back to product search</a> --%>
	 </div>
		<div id="logout">
		<a href="${pageContext.request.contextPath}\logout">Logout</a>		
		</div>
		</div>

		<div id="rightwrapper">
			<div id="productFlowDiv">
				<fieldset id="profileDetailsFieldset">
					<legend id="profileDetailsLegend">Total Sales:</legend>
					<table id="flowsTable">
						<thead>
							<tr>
								<th>Total</th>
								<th>01</th><th>02</th><th>03</th><th>04</th><th>05</th><th>06</th><th>07</th><th>08</th>
								<th>09</th><th>10</th><th>11</th><th>12</th><th>13</th><th>14</th><th>15</th><th>16</th>
								<th>17</th><th>18</th><th>19</th><th>20</th><th>21</th><th>22</th><th>23</th><th>24</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</fieldset>
			</div>
			<div id="chartDiv">
			</div>
			<div id="graphValueViewOptionsDiv">
			<fieldset>
			<legend>Value View</legend>
			<label for="gvr1"><input type="radio" name="graphView" value="TOTALS" id="gvr1"  />Total</label>
			<label for="gvr2"><input type="radio" name="graphView" value="STATS" id="gvr2" checked/>Avg</label>						
			</fieldset>		
			</div>
			<div id="profileScaleDiv">
			<fieldset>
			<legend>Aggregated Time Scale</legend>
							<form:form method="GET" id="profileScaleForm">
								<label for="tr1"><input type="radio" name="profileScale" value="HOUR" id="tr1" checked/>Day(hour)</label>
								<div id="scaleToHide">
								<label for="tr2"><input type="radio" name="profileScale" value="DAY_WEEK" id="tr2"/>Week</label>		
								<label for="tr3"><input type="radio" name="profileScale" value="DAY_MONTH" id="tr3"/>Month</label>
								<label for="tr4"><input type="radio" name="profileScale" value="DAY_YEAR" id="tr4"/>Year(day)</label>
								<label for="tr5"><input type="radio" name="profileScale" value="MONTH" id="tr5"/>Year(month)</label>
								</div>
						</form:form>
			</fieldset>	
			</div>

		  <div id="comparisonDiv">
		  <fieldset>
			<legend id="compareToLegend">Compare To</legend>
						<form:form method="GET" id="compareToForm"
								action="${pageContext.request.contextPath}/product/profileToCompare/${product.lineNumber}">	
								<div id="weekToHide">
						    <label for="c1"><input type="radio" name="compareTo" value="PREVIOUS_WEEK" id="c1" />Previous Week</label>
						    </div>
						    <div id="monthToHide">
								<label for="c2"><input type="radio" name="compareTo" value="PREVIOUS_MONTH" id="c2"/>Previous Month</label>		
								</div>
								<label for="c3"><input type="radio" name="compareTo" value="PREVIOUS_YEAR" id="c3"/>Previous Year</label>
								<div id="compareToHide">
								<label for="c4"><input type="radio" name="compareTo" value="AVERAGE_DAY" id="c4"/>Average Day</label>
								<label for="c5"><input type="radio" name="compareTo" value="AVERAGE_ALL" id="c5"/>Average</label>
								</div>
								<label for="c6"><input type="radio" name="compareTo" value="RESET" id="c6"/>Reset</label>
						</form:form>
			</fieldset>	
		 </div>
		 <div id="progressMessageDiv">
		 	<h3 id="progressMessage">Processing...it might take up to 2 minutes</h3>
		 </div>
		</div>
	</div>
</body>
</html>
