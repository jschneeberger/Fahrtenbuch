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
		<form method="post" action="SavePerson.html">
			<table class="editing">
				<colgroup>
					<col class="attribute"/>
					<col class="value"/>
				</colgroup>
				<tbody>
					<tr>
						<td>Vorname des Vereinsmitglieds:</td>
						<td>
							<input type="hidden" name="id" value='<c:out value="${person.id}"/>'/>
							<input type="text" name="vorname" value='<c:out value="${person.vorname}"/>' size="50" />
						</td>
					</tr>
					<tr>
						<td>Nachname des Vereinsmitglieds:</td>
						<td>
							<input type="text" name="nachname" value='<c:out value="${person.nachname}"/>' size="50" />
						</td>
					</tr>
					<tr>
						<td>Telefonnummer:</td>
						<td>
							<input type="text" name="telefon" value='<c:out value="${person.telefon}"/>' size="50"/>
						</td>
					</tr>
					<tr class="submit">
						<td colspan="2">
								<input type="submit" value=" Änderungen übernehmen "/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>  