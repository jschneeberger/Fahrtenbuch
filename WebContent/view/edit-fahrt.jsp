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
				<p class="form-control-static"><c:out value="${boot.name}"/></p>
			</div>
			<div class="form-group">
				<label for="inputName">Belegung der Sitze</label>
				<p class="form-control-static">
					<c:forEach var="i" begin="1" end="${boot.sitze}" step="1" varStatus ="status">
						<select name="sitz">
							<c:forEach items="${personen}" var="person">
								<option value="${person.id}">${person.vorname} ${person.nachname}</option>
							</c:forEach>
						</select>
					</c:forEach>
				</p>
			</div>
			<button type="submit" class="btn btn-default">Änderungen übernehmen</button>
		</form>
	</div>
	<jsp:include page="foot.html"/>
</body>
</html>  