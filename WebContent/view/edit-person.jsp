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
				<input type="hidden" name="id" value='<c:out value="${person.id}"/>'/>
				<label for="inputVorname">Vorname des Vereinsmitglieds</label>
				<input type="text" class="form-control" id="inputVorname" placeholder="Vorname" name="vorname" value='<c:out value="${person.vorname}"/>'>
			</div>
			<div class="form-group">
				<label for="inputNachname">Nachname des Vereinsmitglieds</label>
				<input type="text" class="form-control" id="inputNachname" placeholder="Nachname" name="nachname" value='<c:out value="${person.nachname}"/>'>
			</div>
			<div class="form-group">
				<label for="inputTelefon">Telefonnummer</label>
				<input type="text" class="form-control" id="inputTelefon" placeholder="+49 (30) ..." name="telefon" value='<c:out value="${person.telefon}"/>'>
			</div>
			<button type="submit" class="btn btn-default">Änderungen übernehmen</button>
		</form>
	</div>
	<jsp:include page="foot.html"/>
</body>
</html>  