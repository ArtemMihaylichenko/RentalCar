package ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * This command redirect from login page 
 * to user registration page.
 * @author A.Mihaylichenko
 *
 */
public class RegistrationCommand extends Command {

	private static final long serialVersionUID = 4546451963461236812L;
	
	private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, AppException {
		LOG.info("Start RegistrationCommand");
		HttpSession session = request.getSession(false);
		
		if (session.getAttribute("errorMessage") != null) {
			String error = (String) session.getAttribute("errorMessage");
			session.removeAttribute("errorMessage");
			request.setAttribute("errorMessage", error);
			LOG.info("session remove message");
		}
		if (session.getAttribute("confirmRegistration") != null) {
			String conf = (String) session.getAttribute("confirmRegistration");
			session.removeAttribute("confirmRegistration");
			request.setAttribute("confirmRegistration", conf);
			LOG.info("session remove confirmRegistration");
		}
		if (session.getAttribute("answer") != null) {
			session.removeAttribute("answer");
			LOG.info("session remove answer  " + session.getAttribute("answer"));
		}
		
		
		if (session.getAttribute("errorMessage") != null) {
			session.removeAttribute("errorMessage");
			LOG.info("session remove message");
		}
		
		LOG.info("End RegistrationCommand");
		return Path.PAGE_REGISTRATION;
	}

}
