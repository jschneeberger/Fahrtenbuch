<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="de">
<head>
<jsp:include page="head.html"/>
</head>
<body>
	<div class="container-fluid"><h1>Fahrtenbuch S3</h1></div>
	<jsp:include page="menu.html"/>
	<div class="container-fluid">
		<h2>Fehler</h2>
		<p>Ein Problem ist aufgetreten. Die folgende Fehlermeldung kann Ihnen vielleicht weiter helfen:</p>
		<p><c:out value="${message}"/></p>
	</div>
	<jsp:include page="foot.html"/>
</body>
</html>  