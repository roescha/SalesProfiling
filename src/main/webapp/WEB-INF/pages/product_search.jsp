<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<title>Product search page</title>
<style>
div#wrapper {
	margin-left: auto;
	margin-right: auto;
	height: auto;
	width: 430pt;
	border: 1px double navy;
	padding-bottom: 20pt;
	background-color: #F5F5DC;
}

 div#logout{
margin-top: 5pt; 
margin-left: 5pt;
font-size:9pt;
	width:auto;
  color: green;
	font-weight: bold;
	font-family: cursive;
}

legend#enterNameLegend {
	text-align: center;
	color: navy;
	font-weight: bold;
	font-family : cursive;
}

fieldset#enterNamefieldset {
	width: 370pt;
	border: 1px solid navy;
	font-family : cursive;
	text-align: left;
}

input#nameText {
	width: 200pt;
	float: left;
}

input#submitNameButton {
	width: 70pt;
	float: center;
}

div#enterdiv {
	width: 350pt;
	margin-left: 20pt;
	margin-top: 30pt;
	border: 0;
}

div#productListDiv {
	width: 375pt;
	margin-left: 25pt;
	margin-top: 20pt;
	overflow: auto;
	height: 250pt;
	padding: 0pt;
}

table#productNamesTable {
	border-collapse: collapse;
	font-family : cursive;
}

table#productNamesTable td {
	border: 1px dotted navy;
	padding: 0px;
}

table#productNamesTable th {
	border: 1px solid navy;
	padding: 0px;
	background: blue;
	color: white;
}

a#productNamesTable {
	display: block;
	width: 95%;
	height: 95%;
	text-decoration: none !important;
}

#productNamesTable tbody:hover th {
	background: #0F0;
}

#productNamesTable tbody tr:hover td {
	background: #FF0;
}
</style>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		 
		$('#productNamesTable').delegate('tbody tr', 'click', function() {
	        var lineNumber = $(this).closest('tr').children().first().html();
	        var url = 'product/' + lineNumber;
	        window.location = url;
	    });		
		
		$('#searchNamesForm').submit(function(event) {
			event.preventDefault();
			var json = {
				"lineNumber" : $('#idText').val(),
				"nameLike" : $('#nameText').val()
			};
			$.ajax({
				url : $("#searchNamesForm").attr("action"),
				data : json,
			}).done(function(products) {
			
	 			$('#productNamesTable tbody').remove();
	 			$('#productNamesTable thead').remove();
	 			
	 			$('#productNamesTable').
					append("<thead><tr><th>Line Number</th><th>Long Name</th><th>Short Name</th></tr></thead>");
				
				$('#productNamesTable').append("<tbody>");
				
				var tableContent = "";
				$(products).each(function(index, product){  				
					tableContent += 
						'<tr><td> ' + 
						product.lineNumber +
						' </td><td> ' +  
					product.longname+' </td><td> ' + 
					product.shortname+' </td></tr>';       
				});

		    $('#productNamesTable').append(tableContent);
				$('#productNamesTable').append("</tbody>");

			}).fail(function(text) {
				alert("error: " + text);
			});
		});
	});
	
</script>
</head>
<body>

	<div id="wrapper">
	
	
		<div id="enterdiv">
		<p align="right"><a href="${pageContext.request.contextPath}/heatMap">Availability heat map</a> | <a href="${pageContext.request.contextPath}/topSeller">Top Sellers</a></p>
			<form:form id="searchNamesForm" method="GET" action="products">
				<fieldset id="enterNamefieldset">
					<legend id="enterNameLegend">Product Search</legend>
					<table id="productNameSearchTable">					
						<tr>
							<th>Product Number:</th>
						  <td><input id="idText" type="text" name="lineNumber" /></td>
						</tr>
						<tr>
							<th>Product Name:</th>
							<td><input id="nameText" type="text" name="nameLike" /></td>
						</tr>
							<tr>
							<th></th>
							<td><input id="submitNameButton" type="submit" value="Search" /></td>
						 </tr>
						<tr></tr>
					</table>
				</fieldset>
			</form:form>
		</div>

		<div id="productListDiv">
			<table id="productNamesTable">
			</table>
		</div>
		<div id="logout">
		<a href="${pageContext.request.contextPath}\logout">Logout</a>
		</div>
	</div>
</body>
</html>