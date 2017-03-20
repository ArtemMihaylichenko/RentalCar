<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Create order" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>
	<table id="container">
	
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr >
			<td class="items">
			
				<form action="controller" method="post">
					<input type="hidden" name="command" value="createOrder"/>
					
					<table>
					
						<c:if test="${not empty answerCreateOrder}">
							<h4 class="error">${answerCreateOrder}</h4>
						</c:if>
					
					<tr>
						<td class="invis"><fmt:message key="order.Passport"/></td>
						<td class="invis"><input name="passport"></td>
						<td class="invis"><fmt:message key="createOrder.passport"/></td>
					</tr>
					<tr>
						<td class="invis"><fmt:message key="order.Driver"/></td>
						<td class="invis">
							<select name="driver">
							<option value="Yes"><fmt:message key="order.Yes"/></option>
							<option value="No"><fmt:message key="order.No"/></option>
						</select>
						</td>
					</tr>
					<tr>
						<td class="invis"><fmt:message key="order.StartDate"/></td>
						<td class="invis"><input name="startDate"></td>
						<td class="invis"><fmt:message key="createOrder.date"/></td>
					</tr>
					<tr>
						<td class="invis"><fmt:message key="order.EndDate"/></td>
						<td class="invis"><input name="endDate"></td>
						<td class="invis"><fmt:message key="createOrder.date"/></td>
					</tr>
					<tr>
						<td class="invis"></td>
						<td class="invis"><input type="submit" value="<fmt:message key="car.MakeAnOrder"/>"></td>
					</tr>
					</table>
					</form>
						<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
	</table>

</body>
</html>