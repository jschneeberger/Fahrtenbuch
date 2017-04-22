<%@ page session="false" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="de">
<head>
<jsp:include page="head.html"/>
</head>
<body>
	<div class="container-fluid"><h1>Fahrtenbuch</h1></div>
	<jsp:include page="menu.html"/>
	<div class="container-fluid">
		<h2><c:out value="${title}"/></h2>
		<p><c:out value="${message}"/></p>
		<form method="post" action="save">
			<div class="form-group">
				<input type="hidden" name="id" value='<c:out value="${boot.id}"/>'/>
				<label for="inputName">Name des Bootes</label>
				<input type="text" class="form-control" id="inputName" placeholder="Name" name="name" value='<c:out value="${boot.name}"/>'>
			</div>
			<div class="form-group">
				<label for="inputSitze">Anzahl der Sitze</label>
				<input type="number" class="form-control" id="inputSitze" placeholder="1" name="sitze" value='<c:out value="${boot.sitze}"/>'>
			</div>
			<div class="form-group">
				<label for="inputKlasse">Bootsklasse</label>
				<input type="text" class="form-control" id="inputKlasse" placeholder="Einer etc." name="klasse" value='<c:out value="${boot.klasse}"/>'>
			</div>
			<button type="submit" class="btn btn-default">Änderungen übernehmen</button>
		</form>
	</div>
	<jsp:include page="foot.html"/>
</body>
</html>  