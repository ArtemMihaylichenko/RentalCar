 <%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Settings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
	<table id="container">
			
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
		<tr>
			<td class="items">
		

				<h2 class="tl"><fmt:message key="login.Registration"/></h2>
				
				<%-- Registration error messages --%>
				<c:if test="${not empty answer}">
					<h4 class="error">${answer}</h4>
				</c:if>
				
				
				<form action="controller" method="post">
				<input type="hidden" name="command" value="userRegistration"/>
				<table>	
					<tr>
						<td class="invis"><fmt:message key="login.Login"/></td>
						<td class="invis"><input name="login"></td>
						<td class="invis"><fmt:message key="registration.loginExam"/></td>
					</tr>
					<tr>
						<td class="invis"><fmt:message key="registration.name"/></td>
						<td class="invis"><input name="name"></td>
					</tr>
					<tr>
						<td class="invis"><fmt:message key="registration.surName"/></td>
						<td class="invis"><input name="surName"></td>
					</tr>	
					<tr>
						<td class="invis">E-mail</td>
						<td class="invis"><input name="mail"></td>
					</tr>
					<tr>
					<td class="invis"><fmt:message key="registration.language"/></td>
					<td class="invis"><select name="language">
						<option value="en"><fmt:message key="login.language.en"/></option>
						<option value="ru"><fmt:message	key="login.language.ru"/></option>
					</select></td>
				</tr>	
				<tr>
					<tr>
						<td class="invis"><fmt:message key="registration.birthDay"/></td>
						<td class="invis"><input name="birthDay"></td>
						<td class="invis"><fmt:message key="createOrder.date"/>. 
											<fmt:message key="registration.birth"/>
						</td>
					</tr>
					<tr>
						<td class="invis"><fmt:message key="registration.password"/></td>
						<td class="invis"><input type="password" name="pass"></td>
						<td class="invis"><fmt:message key="registration.passExam"/></td>
					</tr>
					<tr>
						<td class="invis"><fmt:message key="registration.repeatPassword"/></td>
						<td class="invis"><input type="password" name="repeatPass"></td>
						<td class="invis"><fmt:message key="registration.passExam"/></td>
					</tr>
					<tr>
						<td class="invis"><input type="submit" value="<fmt:message key="registration.submit"/>"></td>
					</tr>
					</table>
				</form>
			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</table>
</body>
</html>

</body>
</html>