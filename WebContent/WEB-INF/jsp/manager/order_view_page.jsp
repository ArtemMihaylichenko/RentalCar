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
			
				<table id="table">
						<tr>
							<td class="invis"><fmt:message key="order.User"/></td>
							<td>${out:userFullName(orderb.user)}</td>
						</tr>
						<tr>
						<td class="invis"><fmt:message key="order.car"/></td>
							<td>${out:carName(orderb.car)}</td>
						</tr>
						<tr>
							<td class="invis"><fmt:message key="order.StartDate"/></td>
							<td>${out:dateToString(orderb.order.dateOfStart)}</td>
						</tr>
						<tr>
							<td class="invis"><fmt:message key="order.EndDate"/></td>
							<td>${out:dateToString(orderb.order.dateOfEnd)}</td>
						</tr>
						<tr>
							<td class="invis"><fmt:message key="order.Status"/></td>
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
						</tr>
						
						<tr>
							<td class="invis"><fmt:message key="bill.rentPrice"/></td>
							<td>${orderb.bill.rent}</td>
						</tr>
						<tr>
							<td class="invis"><fmt:message key="bill.rentStatus"/></td>
							<c:if test="${orderb.bill.rentStatus == 'PAID'}">
									<td class="conf"><fmt:message key="order.billStatus.paid"/></td>
								</c:if>
								<c:if test="${orderb.bill.rentStatus == 'NOPAID'}">
									<td class="no"><fmt:message key="order.billStatus.nopaid"/></td>
								</c:if>
						</tr>
						
						<tr>
							<td class="invis"><fmt:message key="bill.repairPrice"/></td>
							<td>${orderb.bill.repair}</td>
						</tr>
						<tr>
							<td class="invis"><fmt:message key="bill.repairStatus"/></td>
							<c:if test="${orderb.bill.repairStatus == 'PAID'}">
									<td class="conf"><fmt:message key="order.billStatus.paid"/></td>
								</c:if>
								<c:if test="${orderb.bill.repairStatus == 'NOPAID'}">
									<td class="no"><fmt:message key="order.billStatus.nopaid"/></td>
								</c:if>
						</tr>
							
						<tr>
							<td class="invis"><fmt:message key="order.Date"/></td>
							<td>${out:dateToString(orderb.order.orderDate)}</td>
						</tr>
						<tr>
							<td class="invis"><fmt:message key="order.Manager"/></td>
							<c:if test="${orderb.order.managerName == 'nobody'}">
							<td><fmt:message key="order.manager.nobody"/></td>
							</c:if>
							<c:if test="${orderb.order.managerName != 'nobody'}">
							<td>${orderb.order.managerName}</td>
							</c:if>
						</tr>
				</table>	
				
				<table>
					<c:if test="${orderb.order.orderStatus == 'OPENED'}">
						<tr>
							<td  class="invis">
							<form action="controller" method="POST">
								<input type="hidden" name="command" value="changeOrder"/>
								<input type="hidden" name="billId" value="${orderb.bill.id}"/>
								<input type="hidden" name="orderId" value="${orderb.order.id}"/>
								<input type="hidden" name="userId" value="${orderb.user.id}"/>
								<input type="hidden" name="carId" value="${orderb.car.id}"/>
								<input type="hidden" name="type" value="confirm"/>
								<input value="<fmt:message key="order.toConfirm"/>" type="submit"/>
							</form>
							</td>
							<td  class="invis">
							<form action="controller" method="POST">
								<input type="hidden" name="command" value="changeOrder"/>
								<input type="hidden" name="billId" value="${orderb.bill.id}"/>
								<input type="hidden" name="orderId" value="${orderb.order.id}"/>
								<input type="hidden" name="userId" value="${orderb.user.id}"/>
								<input type="hidden" name="carId" value="${orderb.car.id}"/>
								<input type="hidden" name="type" value="decline"/>
								<input value="<fmt:message key="order.toDecline"/>" type="submit"/>
							</form>
							</td>
						</tr>
					</c:if>
					
					<c:if test="${orderb.order.orderStatus == 'CLOSED' or orderb.order.orderStatus == 'ABORTED'}">
						<tr>
							<td  class="invis">
							<form action="controller" method="POST">
								<input type="hidden" name="command" value="deleteOrder"/>
								<input type="hidden" name="orderId" value="${orderb.order.id}"/>
								<input value="<fmt:message key="order.delete.button"/>" type="submit"/>
							</form>
							</td>
						</tr>
					</c:if>
					
					
					
					<c:if test="${orderb.order.orderStatus == 'CONFIRMED'}">
						<tr>
							<td class="invis">
							<form action="controller" method="POST">
								<input type="hidden" name="command" value="changeOrder"/>
								<input type="hidden" name="billId" value="${orderb.bill.id}"/>
								<input type="hidden" name="orderId" value="${orderb.order.id}"/>
								<input type="hidden" name="userId" value="${orderb.user.id}"/>
								<input type="hidden" name="carId" value="${orderb.car.id}"/>
								<input type="hidden" name="type" value="close"/>
								<input value="<fmt:message key="order.toClose"/>" type="submit"/>
							</form>
							</td>
							</tr>
					</c:if>
											
				</table>
				
					<c:if test="${orderb.order.orderStatus == 'CONFIRMED'}">
					<form action="controller" method="POST">
								<input type="hidden" name="command" value="changeOrder"/>
								<input type="hidden" name="billId" value="${orderb.bill.id}"/>
								<input type="hidden" name="orderId" value="${orderb.order.id}"/>
								<input type="hidden" name="userId" value="${orderb.user.id}"/>
								<input type="hidden" name="carId" value="${orderb.car.id}"/>
								<input type="hidden" name="type" value="repair"/>
					<table>
						<tr>
							<td class="invis"><fmt:message key="order.addRepairPrice"/></td>
							<td class="invis"><input name="repairPrice"></td>
							<td class="invis"><input value="<fmt:message key="order.acceptChanges"/>" type="submit"/></td>
						</tr>
					</table>
					</form>
					</c:if>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>