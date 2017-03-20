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

public class LoginForwardCommand extends Command {

	private static final long serialVersionUID = 2441573862244497833L;
	
	private static final Logger LOG = Logger.getLogger(LoginForwardCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.info("Start LoginForwardCommand");
		HttpSession session = request.getSession(false);
		
		if (session.getAttribute("confirmRegistration") != null) {
			session.removeAttribute("confirmRegistration");
			LOG.info("session remove confirmRegistration");
		}
		if (session.getAttribute("answer") != null) {
			session.removeAttribute("answer");
			LOG.info("session remove answer");
		}
		
		LOG.info("End LoginForwardCommand");
		return Path.PAGE_LOGIN;
	}

}
