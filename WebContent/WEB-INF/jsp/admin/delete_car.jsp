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
			
			<c:if test="${messageDelete =='success'}">
				<h3 class="success"><fmt:message key="admin.deletecar.success"/></h3>
			</c:if>
			<c:if test="${messageDelete =='error'}">
				<h3 class="error"><fmt:message key="admin.deletecar.error"/></h3>
			</c:if>
			
			
			</td>
		</tr>
		
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>