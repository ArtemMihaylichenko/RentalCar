package ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.UserDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.Language;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.Roles;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.UserStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Parser;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Validator;

/**
 * Login command. Calling in the main page of application.
 * This command get user from BD. Determine the user role
 * and redirect user to his main page.
 * @author A.Mihaylichenko
 *
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = 6713905994145584597L;
	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("LoginCommand starts");
		
		HttpSession session = request.getSession();
		DAOFactory factory = DAOFactory.getFactory();
		User user = null;
		Roles role = null;
		String language = null;
		String forward = "";
		String errorMessage = "";
		
		try {	
			if (session.getAttribute("errorMessage") != null) {
				session.removeAttribute("errorMessage");
				LOG.info("session remove message");
			}
			if (session.getAttribute("confirmRegistration") != null) {
				session.removeAttribute("confirmRegistration");
				LOG.info("session remove confirmRegistration");
			}
			if (session.getAttribute("answer") != null) {
				session.removeAttribute("answer");
				LOG.info("session remove answer  " + session.getAttribute("answer"));
			}
			
		if (session.getAttribute("user") == null || session.getAttribute("role") == null) { 
			String login = request.getParameter("login");
			LOG.trace("Request parameter: login --> " + login);
			Validator.validateLogin(login);

			String password = request.getParameter("password");
			LOG.trace("Request parameter: password --> " + password);
			Validator.validatePassword(password);
			
			password = Parser.hash(password);
				if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
						throw new AppException("Login/password cannot be empty");
				}
			UserDAO userDAO = factory.getUserDAO();
			user = userDAO.getUserByLoginAndPassword(login, password);
		}	
		else {	
				user = (User) session.getAttribute("user");
			}
			if (user.getUserStatus().equals(UserStatus.BLOCK)) {
				errorMessage = "block";
				LOG.info("user is blocked --> " + user);
				throw new AppException("User block");
				
			}	
		
			role = Roles.getRole(user);
			LOG.trace("userRole--> " + role);
		
			language = user.getLanguage().getName();
			if (language.equalsIgnoreCase(Language.RUSSIAN.getName())) {
				language = "ru";
			}
			else if (language.equalsIgnoreCase(Language.ENGLISH.getName())) {
				language = "en";
			}
			LOG.trace("language --> " + language);
			
			/** Choose command by user role */
			if (role == Roles.CLIENT) {
				forward = Path.COMMAND_MAIN_CLIENT;
			}
			if (role == Roles.MANAGER) {
				forward = Path.COMMAND_MAIN_MANAGER;
			}
			if (role == Roles.ADMIN) {
				forward = Path.COMMAND_MAIN_ADMIN;
			}
			session.setAttribute("user", user);
			LOG.trace("Set user to session attribute --> " + user);
			
			session.setAttribute("role", role);
			LOG.trace("Set role to session attribute --> " + role);
			
			session.setAttribute("language", language);
			LOG.trace("Set language to session attribute --> " + language);
			
			LOG.info("User " + user + " logged as " + role.toString().toLowerCase());
			
			LOG.debug("LoginCommand finished");
		}
		catch(Exception ex) {
			LOG.error("Invalid login or password" + ex.getMessage());
			if (!errorMessage.equals("block")) {
				errorMessage = "incorrect";
			}
			session.setAttribute("errorMessage", errorMessage);
			forward = Path.PAGE_LOGIN;
		}
		
		return forward;
	}

}
