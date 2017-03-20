<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="User" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
		<tr>
			<td class="items">	
			
			
			<table>
						<tr>
							<td class="aqua"><fmt:message key="user.Login"/></td>
							<td class="aqua">${user.login}</td>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.Name"/></td>
							<td class="aqua">${user.name}</td>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.Surname"/></td>
							<td class="aqua">${user.surName}</td>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.dateBirth"/></td>
							<td class="aqua">${out:dateToString(user.birthDay)}</td>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.Mail"/></td>
							<td class="aqua">${user.mail}</td>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.Language"/></td>
							<c:if test="${user.language =='RUSSIAN'}">
								<td class="aqua"><fmt:message key="login.language.ru"/></td>
							</c:if>
							<c:if test="${user.language =='ENGLISH'}">
								<td class="aqua"><fmt:message key="login.language.en"/></td>
							</c:if>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.Role"/></td>
							<c:if test="${user.roleId =='3'}">
								<td class="aqua"><fmt:message key="user.Client"/></td>
							</c:if>	
							<c:if test="${user.roleId =='2'}">
								<td class="aqua"><fmt:message key="user.Manager"/></td>
							</c:if>
						</tr>
						<tr>	
							<td class="aqua"><fmt:message key="user.Status"/></td>
							<c:if test="${user.userStatus =='ACTIVE'}">
								<td class="conf"><fmt:message key="user.Active"/></td>
							</c:if>
							<c:if test="${user.userStatus =='BLOCK'}">
								<td class="no"><fmt:message key="user.Block"/></td>
							</c:if>	
						</tr>
						<tr>
							<c:if test="${user.userStatus =='ACTIVE'}">
							<td class="invis">
								<form action="controller" method="post">
									<input type="hidden" name="command" value="changeUser"/>
									<input type="hidden" name="userId" value="${user.id}"/>
									<input type="hidden" name="type" value="block"/>
									<input value="<fmt:message key="admin.userToBlock"/>" type="submit"/>
								</form>	
							</td>
							</c:if>
							
							<c:if test="${user.userStatus =='BLOCK'}">
							<td class="invis">
								<form action="controller" method="post">
									<input type="hidden" name="command" value="changeUser"/>
									<input type="hidden" name="userId" value="${user.id}"/>
									<input type="hidden" name="type" value="unblock"/>
									<input value="<fmt:message key="admin.userToUnblock"/>" type="submit"/>
								</form>	
							</td>
							</c:if>
							
							
							<c:if test="${user.userStatus =='ACTIVE'}">
								
								<c:if test="${user.roleId =='2'}">
								<td class="invis">
									<form action="controller" method="post">
									<input type="hidden" name="command" value="changeUser"/>
									<input type="hidden" name="userId" value="${user.id}"/>
									<input type="hidden" name="type" value="toClient"/>
									<input value="<fmt:message key="admin.userToClient"/>" type="submit"/>
									</form>	
								</td>
								</c:if>
								
								<c:if test="${user.roleId =='3'}">
								<td class="invis">
									<form action="controller" method="post">
									<input type="hidden" name="command" value="changeUser"/>
									<input type="hidden" name="userId" value="${user.id}"/>
									<input type="hidden" name="type" value="toManager"/>
									<input value="<fmt:message key="admin.userToManager"/>" type="submit"/>
									</form>	
								</td>
								</c:if>
							</c:if>
						</tr>
			</table>
			
			</td>
		</tr>
		
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>