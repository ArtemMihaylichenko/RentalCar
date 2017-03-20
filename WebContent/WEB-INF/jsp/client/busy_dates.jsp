<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
	<c:set var="title" value="Busy dates" scope="page" />
	<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
			
		<tr>
			<td class="items">			
			<%-- Busy dates for car --%>
			
				<c:if test="${not empty dates}">
				<table id="table">
						<tr>
							<td>№</td>
							<td class="invis"><fmt:message key="busyDate.dateStart"/></td>
							<td class="invis"><fmt:message key="busyDate.dateEnd"/></td>
						</tr>
	
					<c:set var="i" value="0"/>
					<c:forEach var="date" items="${dates}">
						<c:set var="i" value="${i+1}"/> 
						<tr>
							<td><c:out value="${i}"/></td>
							<td class="aqua">${out:dateToString(date.startDate)}</td>
							<td class="aqua">${out:dateToString(date.endDate)}</td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
			<c:if test="${empty dates}">
				<h3><fmt:message key="busyDate.carFree"/></h3>
			</c:if>
			
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>