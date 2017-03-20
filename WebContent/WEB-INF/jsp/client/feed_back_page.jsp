<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
	<c:set var="title" value="Feed back" scope="page" />
	<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
			
		<tr>
			<td class="centercontent">			
			
				<h3 class="lesstitle"><fmt:message key="message.main"/></h3>
				
				<c:if test="${not empty messageSend}">
					<c:if test="${typeAnswerSend =='success'}">
						<h4 class="success">${messageSend}</h4>
					</c:if>
					<c:if test="${typeAnswerSend =='error'}">
						<h4 class="error">${messageSend}</h4>
					</c:if>
				</c:if>
				
				<form action="controller" method="POST">
					<input type="hidden" name="command" value="feedBack"/>
					<input type="hidden" name="userId" value="${user.id}"/>
					<input type="hidden" name="type" value="send"/>
				
				<table id="table">
						<tr>
							<td class="invis"><fmt:message key="message.subject"/></td>
							<td class="invis"><input name="subject"></td>
							<td class="invis"><fmt:message key="message.len.subject"/></td>
						</tr>
						<tr>
							<td class="invis"><fmt:message key="message.text"/></td>
							<td class="invis"><textarea name="text"></textarea></td>
							<td class="invis"><fmt:message key="message.error.len"/></td>
						</tr>
						<tr>
							<td class="invis"></td>
							<td class="invis"><input value="<fmt:message key="message.send"/>" type="submit"/></td>
						</tr>
				</table>
			</form>
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>