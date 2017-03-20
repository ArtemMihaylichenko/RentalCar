<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Message" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
		<tr>
			<td class="centercontent">	
			
					<c:if test="${not empty messageSend}">
						<h4 class="error">${messageSend}</h4>
					</c:if>
			
				<form action="controller" method="POST">
					<input type="hidden" name="command" value="sendAnswer"/>
					<input type="hidden" name="userId" value="${client.id}"/>
					<input type="hidden" name="messageId" value="${message.id}"/>
					<input type="hidden" name="type" value="send"/>
				
				<table>
						<tr>
							<td class="aqua"><fmt:message key="user.Login"/></td>
							<td class="aqua">${client.login}</td>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.Name"/></td>
							<td class="aqua">${client.name}</td>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.Surname"/></td>
							<td class="aqua">${client.surName}</td>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.Mail"/></td>
							<td class="aqua">${client.mail}</td>
						</tr>
						<tr>
							<td class="aqua"><fmt:message key="message.subject"/></td>
							<td class="aqua">${message.subject}</td>
						</tr>
						<tr>
							<td class="aqua"><fmt:message key="message.text"/></td>
							<td class="aqua">${message.message}</td>
						</tr>
						<tr>
							<td class="invis"><fmt:message key="message.answer"/></td>
							<td class="invis"><textarea name="answerMessage"></textarea></td>
						</tr>
						<tr>
							<td class="invis"></td>
							<td class="invis">
							<input value="<fmt:message key="message.send"/>" type="submit"/>
							</td>
						</tr>
				</table>
				
				</form>
			
			</td>
		</tr>
		
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>