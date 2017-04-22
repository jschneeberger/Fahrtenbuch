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
				<col/>
				<col/>
				<col/>
				<col/>
			</colgroup>
			<thead>
				<tr>
					<th>Boot</th>
					<th>Abfahrt</th>
					<th>Ankunft</th>
					<th>Mannschaft</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${fahrten}" var="fahrt">
					<tr>
						<td>
							<a href="beende?id=<c:out value="${fahrt.id}"/>">
								<c:out value="${fahrt.bootsname}"/>
							</a>
						</td>
						<td><c:out value="${fahrt.abfahrt}"/></td>
						<td><c:out value="${fahrt.ankunft}"/></td>
						<td><c:out value="${fahrt.mannschaft}"/></td>
					</tr>
				</c:forEach>  
			</tbody>
		</table>
	</div>
	<jsp:include page="foot.html"/>
</body>
</html>  