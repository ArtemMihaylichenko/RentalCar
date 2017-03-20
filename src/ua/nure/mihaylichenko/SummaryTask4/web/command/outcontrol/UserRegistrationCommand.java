 package ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.Language;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.UserStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;
import ua.nure.mihaylichenko.SummaryTask4.web.util.ChooseLanguage;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Mail;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Parser;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Validator;

/**
 * UserRegistrationCommand.
 * This command get parameters from registration page
 * and register a new user by this parameters in application.
 * 
 * @author A.Mihaylichenko
 *
 */
public class UserRegistrationCommand extends Command {

	private static final long serialVersionUID = 246535237669725502L;
	
	private static final Logger LOG = Logger.getLogger(UserRegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ClientMainCommand starts");
		
		User user = null;
		
		String forward = "";
		
		HttpSession session = request.getSession(false); 
		
		/** 
		 * Error messages 
		 **/
		String errLogin = "";
		String errName = "";
		String errPassword = "";
		String errMail = "";
		String errDate = "";
		
		/** Answer about registration state */
		StringBuilder answer = new StringBuilder("");
		
		try {
			String login = "";
			String name = "";
			String surName = "";
			String mail = "";
			String language = request.getParameter("language");
			String birthDay = "";
			String password = "";
			String repeatPass = "";
			
			if (session.getAttribute("errorMessage") != null) {
				session.removeAttribute("errorMessage");
				LOG.info("session remove message");
			}
			 
			/** 
			 * Validate user parameters 
			 * 
			 **/
			
			/** Expected login */
			try {
				login = request.getParameter("login");
				Validator.validateLogin(login);
			}
			catch (Exception ex) {
				LOG.error("invalid login");
				errLogin = ChooseLanguage.getValue("registration.err.login", session);
				answer.append(errLogin + ".");
			}
			
			/** Expected names */
			try {
				name = request.getParameter("name");
				surName = request.getParameter("surName");
				
				Validator.validateNames(name);
				Validator.validateNames(surName);
			}
			catch(Exception ex) {
				LOG.error("invalid name or surname");
				errName = ChooseLanguage.getValue("registration.err.name", session);
				
					answer.append(errName + ". ");
				
			}
			
			/** Expected mail */
			try {
				mail = request.getParameter("mail");
				Validator.validateMail(mail);
			}
			catch (Exception ex) {
				LOG.error("invalid e-mail");
				errMail = ChooseLanguage.getValue("registration.err.mail", session);
					answer.append(errMail + ". ");
			}
			
			/** Expected birth day */
			try {
				birthDay = request.getParameter("birthDay");
				Validator.validateUserDate(birthDay);
			}
			catch (Exception ex) {
				LOG.error("invalid date of birth");
				errDate = ChooseLanguage.getValue("registration.err.date", session);
					answer.append(errDate + ". ");

			}
			
			/** Expected password */
			try {
				password = request.getParameter("pass");
				repeatPass = request.getParameter("repeatPass");
				
				Validator.validatePassword(password);
				Validator.validatePassword(repeatPass);
			
				if (!password.equals(repeatPass)) {
					LOG.error("password not equals repeat password");
					throw new AppException("password not equals repeat password");
				}
			}
			catch (Exception ex) {
				LOG.error("invalid password");
				errPassword = ChooseLanguage.getValue("registration.err.password", session);
					answer.append(errPassword + ". ");
			}
			
			/**
			 * If one or more error messages are not empty forward to catch block 
			 * without creating new user.
			 */
			if (!errLogin.equals("") || !errDate.equals("") || !errName.equals("") || 
					!errPassword.equals("") || !errMail.equals("")) {
				throw new AppException("invalid items");
			}
			
			/** 
			 * Creating User Entity 
			 **/
			user = new User();
			user.setLogin(login);
			user.setName(name);
			user.setSurName(surName);
			user.setPassword(password);
			if (language.equals("en")) {
				user.setLanguage(Language.ENGLISH);
			}
			else if (language.equals("ru")) {
				user.setLanguage(Language.RUSSIAN);
			}
			Date birth = Parser.stringToDate(birthDay);
			user.setBirthDay(birth);
			user.setMail(mail);
			user.setUserStatus(UserStatus.ACTIVE);
			user.setRoleId(3);
			
			LOG.info("new user: --> " + user);
			
			DAOFactory.getFactory().getUserDAO().insertUser(user);
			LOG.debug("user registration success, user insert in DB --> " + user);
			
			forward = Path.PAGE_LOGIN;
			session.setAttribute("confirmRegistration", "confirmRegistration");
			session.setAttribute("language", language);
			
			/** Send mail about registration */
			String message = ChooseLanguage.getValue("registration.message", session)
					+ System.lineSeparator() + ChooseLanguage.getValue("registration.message.login", session)
					+ " " + login + System.lineSeparator() + ChooseLanguage.getValue("registration.message.password", session)
					+ " " + password;
			LOG.info("Message --> " + message);
			try {
				Mail.sendMail(mail, "Registration", message);
				LOG.info("mail message send for user " + login);
			}
			catch (Exception ex) {
				LOG.error("Exception of send mail " + ex.getMessage());
			}
			
		}
		catch (Exception ex) {
			LOG.error("Registration is interrupt " + ex.getMessage());
			if (errLogin.equals("") && errDate.equals("") && errName.equals("") && 
					errPassword.equals("") && errMail.equals("")) {
				answer.append(ChooseLanguage.getValue("registration.err.total", session));			
			}
			session.setAttribute("answer", answer);
			forward = Path.COMMAND_REGISTRATION;
		}
			
		return forward; 
	}

}
