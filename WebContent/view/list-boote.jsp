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
				<c:if test="${edit}">
					<col class="buttoncell"/>
					<col class="buttoncell"/>
				</c:if>
				<col/>
				<col/>
				<col/>
			</colgroup>
			<tbody>
				<tr>
					<c:if test="${edit}">
						<th></th>
						<th></th>
					</c:if>
					<th>Name</th>
					<th>Sitze</th>
					<th>Bootsklasse</th>
				</tr>
				<c:forEach items="${boote}" var="boot">
					<tr>
						<c:if test="${edit}">
							<td>
								<a href="edit?id=<c:out value="${boot.id}"/>">Bearbeite Boot</a>
							</td>
							<td>
								<a href="delete?id=<c:out value="${boot.id}"/>">Lösche Boot</a>
							</td>
						</c:if>
						<td>
							<c:choose>
								<c:when test="${edit}">
									<a href="edit?id=<c:out value="${boot.id}"/>">
										<c:out value="${boot.name}"/>
									</a>
								</c:when>
								<c:otherwise>
									<a href="/FahrtenbuchS4/svc/fahrt/beginneFahrt?id=<c:out value="${boot.id}"/>">
										<c:out value="${boot.name}"/>
									</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td><c:out value="${boot.sitze}"/></td>
						<td><c:out value="${boot.klasse}"/></td>
					</tr>
				</c:forEach>  
			</tbody>
		</table>
	</div>
	<jsp:include page="foot.html"/>
</body>
</html>  