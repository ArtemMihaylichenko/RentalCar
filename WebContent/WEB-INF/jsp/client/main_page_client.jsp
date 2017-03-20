<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Cars" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
		<tr>
			<td class="items">			
			
			<%-- Fields with sort and choose parameters  --%>
			<%@ include file="/WEB-INF/jspf/car_sort.jspf" %>
			
			<%-- Main page client contains all avaible cars --%>
			
				<c:if test="${not empty cars}">
				<table id="table">
					<thead>
						<tr>
							<td>№</td>
							<td class="invis"><fmt:message key="car.Mark"/></td>
							<td class="invis"><fmt:message key="car.Model"/></td>
							<td class="invis"><fmt:message key="car.Engine"/></td>
							<td class="invis"><fmt:message key="car.Color"/></td>
							<td class="invis"><fmt:message key="car.Year"/></td>
							<td class="invis"><fmt:message key="car.Class"/></td>
							<td class="invis"><fmt:message key="car.Price"/></td>
							<td class="invis"></td>
							<td class="invis"></td>
						</tr>
					</thead>
	
					<c:set var="k" value="0"/>
					<c:forEach var="car" items="${cars}">
						<c:set var="k" value="${k+1}"/> 
						<tr>
							<td><c:out value="${k}"/></td>
							<td class="aqua">${car.mark}</td>
							<td class="aqua">${car.model}</td>
							<td class="aqua">${car.engine}</td>
							<td class="aqua">${car.color}</td>
							<td class="aqua">${car.year}</td>
							<td class="aqua">${car.carClass}</td>
							<td class="aqua">${car.price}</td>
							<td class="invis">
								<form action="controller">
									<input type="hidden" name="command" value="orderCreate"/>
									<input type="hidden" name="carId" value="${car.id}"/>
									<input value="<fmt:message key="car.MakeAnOrder"/>" type="submit"/>
								</form>
							</td>
							<td class="invis">
								<form action="controller">
									<input type="hidden" name="command" value="getBusyDate"/>
									<input type="hidden" name="carId" value="${car.id}"/>
									<input value="<fmt:message key="car.getBusyDate"/>" type="submit"/>
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
				<c:if test="${empty cars}">
					<h2><fmt:message key="car.NoCars"/></h2>
				</c:if>
				<c:if test="${not empty error}">
					<h2><fmt:message key="car.NoCars"/></h2>
				</c:if>
		
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>