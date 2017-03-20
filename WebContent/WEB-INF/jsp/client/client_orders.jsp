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
			
			<c:if test="${not empty answer}">
				<h4 class="success">${answer}</h4>
			</c:if>
			
			<%-- All client orders --%>
			<c:if test="${not empty orderBeans}">
				<table id="table">
						<tr>
							<td>№</td>
							<td class="invis"><fmt:message key="order.Passport"/></td>
							<td class="invis"><fmt:message key="car.Mark"/></td>
							<td class="invis"><fmt:message key="car.Model"/></td>
							<td class="invis"><fmt:message key="order.Driver"/></td>
							<td class="invis"><fmt:message key="order.StartDate"/></td>
							<td class="invis"><fmt:message key="order.EndDate"/></td>
							<td class="invis"><fmt:message key="order.Price"/></td>
							<td class="invis"><fmt:message key="order.Status"/></td>
							<td class="invis"><fmt:message key="order.Date"/></td>
							<td class="invis"><fmt:message key="order.BillStatus"/></td>
							<td class="invis"><fmt:message key="order.Manager"/></td>
						</tr>
					<c:set var="k" value="0"/> 
					<c:forEach var="orderb" items="${orderBeans}">
						<c:set var="k" value="${k+1}"/> 
						<tr>
							<td><c:out value="${k}"/></td>
							<td class="aqua">${orderb.order.passport}</td>
							<td class="aqua">${orderb.car.mark}</td>
							<td class="aqua">${orderb.car.model}</td>
							<td class="aqua">
							<c:if test="${orderb.order.driver == 1}">
									<fmt:message key="order.Yes"/>
								</c:if>
								<c:if test="${orderb.order.driver == 0}">
									<fmt:message key="order.No"/>
								</c:if>
							</td>
							<td class="aqua">${out:dateToString(orderb.order.dateOfStart)}</td>
							<td class="aqua">${out:dateToString(orderb.order.dateOfEnd)}</td>
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
							<td class="aqua">${out:dateToString(orderb.order.orderDate)}</td>
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
							<c:if test="${orderb.order.orderStatus == 'CONFIRMED'}">
								<td  class="invis">
									<form action="controller" method="GET">
									<input type="hidden" name="command" value="seeOrder"/>
									<input type="hidden" name="billId" value="${orderb.bill.id}"/>
									<input type="hidden" name="carId" value="${orderb.car.id}"/>
									<input type="hidden" name="orderId" value="${orderb.order.id}"/>
									<input value="<fmt:message key="order.seeBill"/>" type="submit"/>
								</form>
								</td>
							</c:if>
							<c:if test="${orderb.order.orderStatus == 'CLOSED'}">
								<td  class="invis">
									<form id="make_order" action="controller" method="GET">
									<input type="hidden" name="command" value="seeOrder"/>
									<input type="hidden" name="billId" value="${orderb.bill.id}"/>
									<input type="hidden" name="carId" value="${orderb.car.id}"/>
									<input type="hidden" name="orderId" value="${orderb.order.id}"/>
									<input value="<fmt:message key="order.seeBill"/>" type="submit"/>
								</form>
								</td>
							</c:if>
						</tr>
				</c:forEach>
				</table>
				</c:if>
		
				<c:if test="${empty orderBeans}">
					<h3><fmt:message key="order.empty"/></h3>
				</c:if>
			
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>