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

div#invalidDiv{
color:red;
font-size:9pt;
}

table {
	margin: auto;
}
</style>
</head>

<body>
	<div id="wrapper">
		<h1>Waitrose Product Profile Portal</h1>
		<div id="credentialsDiv">
			<h3>Please enter your credentials</h3>
			<div id="invalidDiv">
				<c:if test="${param.error != null}">
            Invalid username and password.
            </c:if>
			</div>

			<form action="${pageContext.request.contextPath}/login" method="post">
				<table id="credentialsTable">
					<tr>
						<th>User Name :</th>
						<td><input type="text" name="username" /></td>
					</tr>
					<tr>
						<th>Password :</th>
						<td><input type="password" name="password" /></td>
					</tr>
					<tr></tr>
					<tr>
						<th></th>
						<td><input type="submit" value="Sign In" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>