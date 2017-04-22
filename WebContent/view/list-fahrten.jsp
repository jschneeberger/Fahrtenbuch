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
							<a href="StopFahrt.html?id=<c:out value="${fahrt.id}"/>">
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
</body>
</html>  