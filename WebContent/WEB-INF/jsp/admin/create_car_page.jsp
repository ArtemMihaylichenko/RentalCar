<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Edit car" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
	<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
		<tr>
			<td class="items">
			
		<%-- MESSAGES FROM COMMAND ABOUT SUCCESS OR ERROR --%>
				<c:if test="${message =='success'}">
					<h3 class="success"><fmt:message key="admin.cars.success.add"/></h3>
				</c:if>
				<c:if test="${message =='error'}">
					<h3 class="error"><fmt:message key="admin.cars.error.noadd"/></h3>
				</c:if>
				<c:if test="${not empty answer}">
					<h4 class="error">${answer}</h4>
				</c:if>
			<%-- MESSAGES END --%>
			
			
				<h3><fmt:message key="admin.cars.addCar"/></h3>
				<form action="controller" method="post">
				<input type="hidden" name="command" value="addCar"/>
				<input type="hidden" name="carId" value="${car.id}"/>
				
				<table>	
				<tr>
					<td class="invis"><fmt:message key="car.Mark"/></td>
					<td class="invis"><input name="mark"></td>
				</tr>	
				<tr>
					<td class="invis"><fmt:message key="car.Model"/></td>
					<td  class="invis"><input name="model"></td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Engine"/></td>
					<td  class="invis"><input name="engine"></td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Color"/></td>
					<td class="invis"><select name="carColor">
							<c:set var="k" value="0"/>
							<c:forEach var="String" items="${carColors}">
							<c:set var="k" value="${k+1}"/> 
	
								<option>${String}</option>
								
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Year"/></td>
					<td class="invis"><input name="year"></td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Class"/></td>
					<td class="invis">
						<select name="carClass">
							<c:set var="k" value="0"/>
							<c:forEach var="String" items="${carClasses}">
							<c:set var="k" value="${k+1}"/> 
							
								<option>${String}</option>
								
							</c:forEach>
						</select>
						</td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Price"/></td>
					<td class="invis"><input name="price"></td>
				</tr>
				<tr>
					<td class="invis"></td>
					<td class="invis">
					<input type="hidden" name="type" value="add"/>
					<input type="submit" value="<fmt:message key="admin.cars.add"/>"></td>
				</tr>
				</table>
			</form>
			
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>