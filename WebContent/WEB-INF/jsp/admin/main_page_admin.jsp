<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Users" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
		<tr>
			<td class="items">	
			
				<c:if test="${message =='role'}">
				<h3 class="success"><fmt:message key="admin.message.role"/></h3>
			</c:if>
			<c:if test="${message =='roleErr'}">
				<h3 class="error"><fmt:message key="admin.message.roleErr"/></h3>
			</c:if>
			<c:if test="${message =='roleErrManager'}">
				<h3 class="error"><fmt:message key="admin.message.roleErrManager"/></h3>
			</c:if>
			<c:if test="${message =='blockErrorManager'}">
				<h3 class="error"><fmt:message key="admin.message.blockManager"/></h3>
			</c:if>
			<c:if test="${message =='statusBlock'}">
				<h3 class="success"><fmt:message key="admin.message.block"/></h3>
			</c:if>
			<c:if test="${message =='statusUnBlock'}">
				<h3 class="success"><fmt:message key="admin.message.unblock"/></h3>
			</c:if>
			<c:if test="${message =='error'}">
				<h3 class="error"><fmt:message key="admin.error"/></h3>
			</c:if>
			
				<%-- Fields with sort and choose parameters  --%>
				<%@ include file="/WEB-INF/jspf/user_chooser.jspf" %>
			
				<c:if test="${not empty users}">
				<table id="table">
						<tr>
							<td>№</td>
							<td class="invis"><fmt:message key="user.Login"/></td>
							<td class="invis"><fmt:message key="user.User"/></td>
							<td class="invis"><fmt:message key="user.dateBirth"/></td>
							<td class="invis"><fmt:message key="user.Role"/></td>
							<td class="invis"><fmt:message key="user.Status"/></td>
						</tr>
							<c:set var="k" value="0"/>
						<c:forEach var="user" items="${users}">
							<c:set var="k" value="${k+1}"/>
						<tr>
								<td><c:out value="${k}"/></td>
								<td class="aqua">${user.login}</td>
								<td class="aqua">${out:userFullName(user)}</td>
								<td class="aqua">${out:dateToString(user.birthDay)}</td>
							<c:if test="${user.roleId =='3'}">
								<td class="aqua"><fmt:message key="user.Client"/></td>
							</c:if>	
							<c:if test="${user.roleId =='2'}">
								<td class="aqua"><fmt:message key="user.Manager"/></td>
							</c:if>	
							<c:if test="${user.userStatus =='ACTIVE'}">
								<td class="conf"><fmt:message key="user.Active"/></td>
							</c:if>
							<c:if test="${user.userStatus =='BLOCK'}">
								<td class="no"><fmt:message key="user.Block"/></td>
							</c:if>	 
							
							
							<td class="invis">
								<form action="controller">
									<input type="hidden" name="command" value="openUser"/>
									<input type="hidden" name="userId" value="${user.id}"/>
									<input value="<fmt:message key="user.Open"/>" type="submit"/>
								</form>		
							</td>
								
						</c:forEach> 
						<tr>
				</table>
				</c:if>

		<c:if test="${empty users}">
			<h3><fmt:message key="user.NoUsers"/></h3>
		</c:if>

			</td>
		</tr>
		
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>