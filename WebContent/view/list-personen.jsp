<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.html"/>
</head>
<body>
	<div id="head"><h1>Fahrtenbuch</h1></div>
	<jsp:include page="menu.html"/>
	<div id="content">
		<h2><c:out value="${title}"/></h2>
		<p><c:out value="${message}"/></p>
		<table class="listing">
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
							<a href="EditPerson.s?id=<c:out value="${person.id}"/>">
								<img src="images/button_edit.png" alt="Icon - Bearbeite Boot"/>
							</a>
						</td>
						<td>
							<a href="LoeschePerson.s?id=<c:out value="${person.id}"/>">
								<img src="images/button_drop.png" alt="Icon - Lösche Boot"/>
							</a>
						</td>
						<td>
							<a href="EditPerson.s?id=<c:out value="${person.id}"/>">
								<c:out value="${person.vorname}"/> <c:out value="${person.nachname}"/>
							</a>
						</td>
						<td><c:out value="${person.telefon}"/></td>
					</tr>
				</c:forEach>  
			</tbody>
		</table>
	</div>
</body>
</html>  