<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Bill" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
			
		<tr>
			<td class="items">		
			
						<c:if test="${not empty answer}">
							<h4 class="success">${answer}</h4>
						</c:if>
						
				<table id="table">
				<tr>
					<td class="aqua"><fmt:message key="car.Mark"/></td>
					<td class="aqua"><fmt:message key="car.Model"/></td>
					<td class="aqua"><fmt:message key="order.Driver"/></td>
					<td class="aqua"><fmt:message key="bill.days"/></td>
					<td class="aqua"><fmt:message key="bill.DriverPrice"/></td>
					<td class="aqua"><fmt:message key="bill.AutoPrice"/></td>
					<td class="aqua"><fmt:message key="bill.TotalPrice"/></td>
					<td class="aqua"><fmt:message key="order.Status"/></td>
				</tr>
					<tr>
						<c:set var="k" value="0"/>
						<c:forEach var="String" items="${answerBillRent}">
						<c:set var="k" value="${k+1}"/>
								<c:if test="${String == 'yes'}">
									<td class="aqua"><fmt:message key="order.Yes"/></td>
								</c:if>
								<c:if test="${String == 'no'}">
									<td class="aqua"><fmt:message key="order.No"/></td>
								</c:if>
								<c:if test="${String == 'PAID'}">
									<td class="conf"><fmt:message key="order.billStatus.paid"/></td>
								</c:if>
								<c:if test="${String == 'NOPAID'}">
									<td class="no"><fmt:message key="order.billStatus.nopaid"/></td>
								</c:if>
									<c:if test="${String != 'yes'}">
										<c:if test="${String != 'no'}">
											<c:if test="${String != 'PAID'}">
												<c:if test="${String != 'NOPAID'}">
												
										<td class="aqua">${String}</td>
												
												</c:if>
											</c:if>
										</c:if>
									</c:if>
							</c:forEach>
								<c:if test="${billRentPay == false}">
							<td  class="invis">
								<form action="controller" method="POST">
									<input type="hidden" name="command" value="payBill"/>
									<input type="hidden" name="billId" value="${billId}"/>
									<input type="hidden" name="carId" value="${carId}"/>
									<input type="hidden" name="orderId" value="${orderId}"/>
									<input type="hidden" name="billType" value="billRent"/>
									<input value="<fmt:message key="bill.pay"/>" type="submit"/>
								</form>
							</td>
								</c:if>
					</tr>
				</table>
				
			<table id="table">		
			<c:if test="${not empty answerBillRepair}">
				<tr>
					<td class="aqua"><fmt:message key="car.Mark"/></td>
					<td class="aqua"><fmt:message key="car.Model"/></td>
					<td class="aqua"><fmt:message key="bill.days"/></td>
					<td class="aqua"><fmt:message key="bill.repairPrice"/></td>
					<td class="aqua"><fmt:message key="order.Status"/></td>
				</tr>
					<tr>
						<c:set var="k" value="0"/>
						<c:forEach var="String" items="${answerBillRepair}">
						<c:set var="k" value="${k+1}"/>
							<c:if test="${String == 'PAID'}">
								<td class="conf"><fmt:message key="order.billStatus.paid"/></td>
							</c:if>
							<c:if test="${String == 'NOPAID'}">
								<td class="no"><fmt:message key="order.billStatus.nopaid"/></td>
							</c:if>
								<c:if test="${String != 'PAID'}">
									<c:if test="${String != 'NOPAID'}">
												
									<td class="aqua">${String}</td>
												
									</c:if>
								</c:if>
							</c:forEach>
								<c:if test="${billRepairPay == false}">
							<td class="invis">
								<form action="controller" method="POST">
									<input type="hidden" name="command" value="payBill"/>
									<input type="hidden" name="billId" value="${billId}"/>
									<input type="hidden" name="carId" value="${carId}"/>
									<input type="hidden" name="orderId" value="${orderId}"/>
									<input type="hidden" name="billType" value="billRepair"/>
									<input value="<fmt:message key="bill.pay"/>" type="submit"/>
								</form>
							</td>
								</c:if>
					</tr>
					</c:if>
						
				</table>
		
			
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>