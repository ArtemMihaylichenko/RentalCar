<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Orders" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
			
		<tr>
			<td class="items">	
			
			<%--  MESSAGES FROM CHANGE ORDER COMMAND --%>
				<c:if test="${not empty message}">
				<c:if test="${message =='error'}">
					<h3 class="error"><fmt:message key="order.changeNoConfirm"/></h3>
				</c:if>
				<c:if test="${message =='nopaid'}">
					<h3 class="error"><fmt:message key="order.cannotClose"/></h3>
				</c:if>
				<c:if test="${message =='success'}">
					<h3 class="success"><fmt:message key="order.SuccessAccept"/></h3>
				</c:if>
			</c:if>
			<%--  MESSAGES FROM DELETE ORDER COMMAND --%>
				<c:if test="${not empty messageDelete}">
					<h3 class="success"><fmt:message key="order.delete"/></h3>
				</c:if>		
			
				<table id="table">
						<tr>
							<td>№</td>
							<td class="invis"><fmt:message key="order.Passport"/></td>
							<td class="invis"><fmt:message key="order.User"/></td>
							<td class="invis"><fmt:message key="order.car"/></td>
							<td class="invis"><fmt:message key="order.Price"/></td>
							<td class="invis"><fmt:message key="order.Status"/></td>
							<td class="invis"><fmt:message key="order.Date"/></td>
							<td class="invis"><fmt:message key="order.BillStatus"/></td>
							<td class="invis"><fmt:message key="order.Manager"/></td>
						</tr>
					<c:set var="k" value="0"/> 
					<c:forEach var="orderb" items="${orderb}">
						<c:set var="k" value="${k+1}"/> 
						<tr>
							<td><c:out value="${k}"/></td>
							<td class="aqua">${orderb.order.passport}</td>
							<td class="aqua">${out:userFullName(orderb.user)}</td>
							<td class="aqua">${out:carName(orderb.car)}</td>
							<td class="aqua">${orderb.bill.rent + bill.repair}</td>
							<c:if test="${orderb.order.orderStatus == 'OPENED'}">
								<td class="open"><fmt:message key="order.status.open"/></td>
							</c:if>
							<c:if test="${orderb.order.orderStatus == 'CONFIRMED'}">
								<td class="conf"><fmt:message key="order.status.confirmed"/></td>
							</c:if>
							<c:if test="${orderb.order.orderStatus == 'ABORTED'}">
								<td class="no"><fmt:message key="order.status.aborted"/></td>
							</c:if>
							<c:if test="${orderb.order.orderStatus == 'CLOSED'}">
								<td class="close"><fmt:message key="order.status.closed"/></td>
							</c:if>
							<td>${out:dateToString(orderb.order.orderDate)}</td>
							<c:if test="${orderb.billStatus == 'PAID'}">
								<td class="conf"><fmt:message key="order.billStatus.paid"/></td>
							</c:if>
							<c:if test="${orderb.billStatus == 'NOPAID'}">
								<td class="no"><fmt:message key="order.billStatus.nopaid"/></td>
							</c:if>
							
							<c:if test="${orderb.order.managerName == 'nobody'}">
							<td class="aqua"><fmt:message key="order.manager.nobody"/></td>
							</c:if>
							<c:if test="${orderb.order.managerName != 'nobody'}">
							<td class="aqua">${orderb.order.managerName}</td>
							</c:if>
								<td class="invis">
									<form action="controller" method="GET">
									<input type="hidden" name="command" value="openOrder"/>
									<input type="hidden" name="billId" value="${orderb.bill.id}"/>
									<input type="hidden" name="carId" value="${orderb.car.id}"/>
									<input type="hidden" name="orderId" value="${orderb.order.id}"/>
									<input type="hidden" name="clientId" value="${orderb.user.id}"/>
									<input value="<fmt:message key="order.openOrder"/>" type="submit"/>
								</form>
								</td>
						</tr>
				</c:forEach>
				</table>
		
			
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>