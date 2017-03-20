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
			
			
				<h3><fmt:message key="admin.cars.ChangeCar"/></h3>
				<form action="controller" method="post">
				<input type="hidden" name="command" value="changeCar"/>
				<input type="hidden" name="carId" value="${car.id}"/>
				
				<table>	
				<tr>
					<td class="invis"><fmt:message key="car.Mark"/></td>
					<td class="invis"><input name="mark" value="${car.mark}"></td>
				</tr>	
				<tr>
					<td class="invis"><fmt:message key="car.Model"/></td>
					<td class="invis"><input name="model" value="${car.model}"></td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Engine"/></td>
					<td class="invis"><input name="engine" value="${car.engine}"></td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Color"/></td>
					<td class="invis"><select name="carColor">
							<c:set var="k" value="0"/>
							<c:forEach var="String" items="${carColors}">
							<c:set var="k" value="${k+1}"/> 
							<c:if test="${carColor == String}">
								<option selected>${String}</option>
							</c:if>
							<c:if test="${carColor != String}">
								<option>${String}</option>
							</c:if>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Year"/></td>
					<td class="invis"><input name="year" value="${car.year}"></td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Class"/></td>
					<td class="invis">
						<select name="carClass">
							<c:set var="k" value="0"/>
							<c:forEach var="String" items="${carClasses}">
							<c:set var="k" value="${k+1}"/> 
							<c:if test="${carClass == String}">
								<option selected>${String}</option>
							</c:if>
							<c:if test="${carClass != String}">
								<option>${String}</option>
							</c:if>
							</c:forEach>
						</select>
						</td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="car.Price"/></td>
					<td class="invis"><input name="price" value="${car.price}"></td>
				</tr>
				<tr>
					<td class="invis"></td>
					<td class="invis">
					<input type="hidden" name="type" value="edit"/>
					<input type="submit" value="<fmt:message key="settings.confirm"/>"></td>
				</tr>
				</table>
			</form>
			
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>