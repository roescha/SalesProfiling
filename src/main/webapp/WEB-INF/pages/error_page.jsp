<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Login in page</title>
<style type="text/css">
div#wrapper {
	margin-left: auto;
	margin-right: auto;
	height: 300pt;
	width: 700pt;
	border: 1px double navy;
	padding-bottom: 5pt;
	background-color: #F5F5DC;
	padding-left: 5pt;
	padding-right: 5pt;
	font-size: 11pt;
	text-align: center;
	color: navy;
	font-weight: bold;
	font-family: cursive;
}

h2 {
	color: red;
}
</style>
</head>

<body>
	<div id="wrapper">
		<h2>Product search results:</h2>
		<p>${message}</p>
		<a href="${pageContext.request.contextPath}/">Back to product
			search</a>
	</div>
</body>
</html>

