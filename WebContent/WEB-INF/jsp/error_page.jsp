<%@ page isErrorPage="true" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>

	<table id="container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr >
			<td class="items">
				
				<h2 class="error">
					The following error occurred
				</h2>
			
				<%-- this way we obtain an information about an exception (if it has been occurred) --%>
				<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
				<c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
				<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
				
				<c:if test="${not empty code}">
					<h3 class="error">Error code: ${code}</h3>
				</c:if>			
				
				<c:if test="${not empty message}">
					<h3 class="error">${message}</h3>
				</c:if>
				
				<c:if test="${not empty requestScope.errorMessage}">
					<h3 class="error">${requestScope.errorMessage}</h3>
				</c:if>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
	</table>
</body>
</html>