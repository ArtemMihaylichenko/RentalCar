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
			
				<%-- Settings error and success messages --%>

					<c:if test="${not empty answerSet}">
						<c:if test="${typeSet =='success'}">
						<h3 class="success">${answerSet}</h3>
						</c:if>
					</c:if>
					<c:if test="${typeSet == 'error'}">
						<h3 class="error">${answerSet}</h3>
					</c:if>	
			
				<h3 class="titles"><fmt:message key="settings.CreatePassword"/></h3>
				<form action="controller" method="post">
				<input type="hidden" name="command" value="editSettings"/>
				<input type="hidden" name="settings" value="password"/>
		
				<table>	
				<tr>
					<td class="invis"><fmt:message key="settings.oldPassword"/></td>
					<td class="invis"><input type="password" name="oldPass"></td>
				</tr>	
				<tr>
					<td class="invis"><fmt:message key="settings.newPassword"/></td>
					<td class="invis"><input type="password" name="newPass"></td>
				</tr>
				<tr>
					<td class="invis"><fmt:message key="settings.repeatNewPassword"/></td>
					<td class="invis"><input type="password" name="repeatNewPass"></td>
				</tr>
				<tr>
					<td class="invis"><input type="submit" value="<fmt:message key="settings.confirm"/>"></td>
				</tr>
				</table>
			</form>
			<h3 class="titles"><fmt:message key="settings.editEmail"/></h3>
			
			<form action="controller" method="post">
				<input type="hidden" name="command" value="editSettings"/>
				<input type="hidden" name="settings" value="mail"/>
				
				<table>	
				<tr>
					<td class="invis"><fmt:message key="settings.oldMail"/></td>
					<td class="invis"><input name="oldMail"></td>
				</tr>	
				<tr>
					<td class="invis"><fmt:message key="settings.newMail"/></td>
					<td class="invis"><input name="newMail"></td>
				</tr>	
				<tr>
					<td class="invis"><input type="submit" value="<fmt:message key="settings.confirm"/>"></td>
				</tr>
				</table>
			</form>
	
			<h3 class="titles"><fmt:message key="settings.editLanguage"/></h3>
			
			<form action="controller" method="post">
				<input type="hidden" name="command" value="editSettings"/>
				<input type="hidden" name="settings" value="language"/>
				<table>	
				<tr>
					<td class="invis"><select name="selectLanguage">
						<option value="en"><fmt:message key="login.language.en"/></option>
						<option value="ru"><fmt:message	key="login.language.ru"/></option>
					</select></td>
				</tr>	
				<tr>
					<td class="invis"><input type="submit" value="<fmt:message key="settings.confirm"/>"></td>
				</tr>
				</table>
			</form>
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>