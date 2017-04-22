<%@ page session="false" %>
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
		<table class="table">
			<colgroup>
				<col class="buttoncell"/>
				<col class="buttoncell"/>
				<col/>
				<col/>
			</colgroup>
			<tbody>
				<tr>
					<th></th>
					<th></th>
					<th>Name</th>
					<th>Telefon</th>
				</tr>
				<c:forEach items="${personen}" var="person">
					<tr>
						<td>
							<a href="edit?id=<c:out value="${person.id}"/>">Bearbeite Person</a>
						</td>
						<td>
							<a href="delete?id=<c:out value="${person.id}"/>">Lösche Person</a>
						</td>
						<td>
							<a href="edit?id=<c:out value="${person.id}"/>">
								<c:out value="${person.vorname}"/> <c:out value="${person.nachname}"/>
							</a>
						</td>
						<td><c:out value="${person.telefon}"/></td>
					</tr>
				</c:forEach>  
			</tbody>
		</table>
	</div>
	<jsp:include page="foot.html"/>
</body>
</html>  