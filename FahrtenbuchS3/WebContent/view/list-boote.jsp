<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.htm"/>
</head>
<body>
	<div id="head"><h1>Fahrtenbuch</h1></div>
	<jsp:include page="menu.htm"/>
	<div id="content">
		<h2><c:out value="${title}"/></h2>
		<p><c:out value="${message}"/></p>
		<table class="listing">
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
								<a href="EditBoot.html?id=<c:out value="${boot.id}"/>">
									<img src="images/button_edit.png" alt="Icon - Bearbeite Boot"/>
								</a>
							</td>
							<td>
								<a href="LoescheBoot.html?id=<c:out value="${boot.id}"/>">
									<img src="images/button_drop.png" alt="Icon - Lösche Boot"/>
								</a>
							</td>
						</c:if>
						<td>
							<c:choose>
								<c:when test="${edit}">
									<a href="EditBoot.html?id=<c:out value="${boot.id}"/>">
										<c:out value="${boot.name}"/>
									</a>
								</c:when>
								<c:otherwise>
									<a href="BeginneFahrt.html?id=<c:out value="${boot.id}"/>">
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
</body>
</html>  