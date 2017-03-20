<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>
	<table id="container">

		
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr >
			<td class="items center">
			<h2 class="title"><fmt:message key="login.projectname"/></h2>
			
			
				<c:if test="${empty user}">
				
				<c:if test="${not empty confirmRegistration}">
					<h2 class="success"><fmt:message key="registration.confirmed"/></h2>
				</c:if>
				<c:if test="${errorMessage =='incorrect'}">
					<h3 class="error"><fmt:message key="login.error"/></h3>
				</c:if>
				<c:if test="${errorMessage =='block'}">
					<h3 class="error"><fmt:message key="login.block"/></h3>
				</c:if>
				
				<form id="login" action="controller" method="post">

					<input type="hidden" name="command" value="login"/>

					<fieldset>
						<legend><fmt:message key="login.Login"/></legend>
						<input name="login"/><br/>
					</fieldset><br/>
					<fieldset>
						<legend><fmt:message key="login.Password"/></legend>
						<input type="password" name="password"/>
					</fieldset><br/>
					
					<input id="login" type="submit" value=<fmt:message key="login.ToLogin"/>>							
				</form>
				<form id="login" action="controller" method="GET">
				<input type="hidden" name="command" value="registration"/>
				<input type="submit" value="<fmt:message key="login.Registration"/>">
				</form>
			
			</c:if>
			</td>
			<c:if test="${not empty role}">
				<c:if test="${role =='CLIENT'}">
					<c:redirect url ="controller?command=mainClient"></c:redirect>
				</c:if>
				<c:if test="${role =='MANAGER'}">
					<c:redirect url ="controller?command=mainManager"></c:redirect>
				</c:if>
				<c:if test="${role =='ADMIN'}">
					<c:redirect url ="controller?command=mainAdmin"></c:redirect>
				</c:if>
			</c:if>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
	</table>
</body>
</html>