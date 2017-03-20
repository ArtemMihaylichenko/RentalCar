<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
	<c:set var="title" value="Messages" scope="page" />
	<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
			
		<tr>
			<td class="centercontent">			
			
				<h3 class="lesstitle"><fmt:message key="messages.messages"/></h3>
				
				<c:if test="${not empty answerMessage}">
					<c:if test="${typeAnswer =='success'}">
						<h4 class="success">${answerMessage}</h4>
					</c:if>
					<c:if test="${typeAnswerSend =='error'}">
						<h4 class="error">${answerMessage}</h4>
					</c:if>
				</c:if>
			
				<c:if test="${not empty messages}">
				<table id="table">
						<tr>
							<td>№</td>
							<td class="invis"><fmt:message key="message.subject"/></td>
							<td class="invis"><fmt:message key="message.text"/></td>
						</tr>
	
					<c:set var="i" value="0"/>
					<c:forEach var="message" items="${messages}">
						<c:set var="i" value="${i+1}"/> 
						<tr>
							<td><c:out value="${i}"/></td>
							<td class="aqua">${message.subject}</td>
							<td class="aqua">${message.message}</td>
							<td class="aqua">
							<form action="controller" method="GET">
									<input type="hidden" name="command" value="changeMessage"/>
									<input type="hidden" name="messageId" value="${message.id}"/>
									<input value="<fmt:message key="message.open"/>" type="submit"/>
							</form>
							</td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
			<c:if test="${empty messages}">
				<h3><fmt:message key="messages.noMes"/></h3>
			</c:if>
			
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>