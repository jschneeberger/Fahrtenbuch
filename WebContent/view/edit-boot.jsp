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
		<form method="post" action="SaveBoot.html">
			<table class="editing">
				<colgroup>
					<col class="attribute"/>
					<col class="value"/>
				</colgroup>
				<tbody>
					<tr>
						<td>Name des Bootes:</td>
						<td>
							<input type="hidden" name="id" value='<c:out value="${boot.id}"/>'/>
							<input type="text" name="name" value='<c:out value="${boot.name}"/>' size="50" />
						</td>
					</tr>
					<tr>
						<td>Anzahl der Sitze:</td>
						<td>
							<input type="text" name="sitze" value='<c:out value="${boot.sitze}"/>' size="2" />
						</td>
					</tr>
					<tr>
						<td>Bootsklasse:</td>
						<td>
							<input type="text" name="klasse" value='<c:out value="${boot.klasse}"/>' size="50"/>
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