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
 * Out control command.
 * This command choosing the language of UI.
 * Calling from header or settings page.
 * @author A.Mihaylichenko
 */
public class ChooseLanguageCommand extends Command {

	private static final long serialVersionUID = 155362509058135833L;
	private static final Logger LOG = Logger.getLogger(ChooseLanguageCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ChooseLanguageCommand starts");
		
		HttpSession session = request.getSession();
		
		String language = request.getParameter("language");
		
		LOG.info("language --> " + language);
		session.setAttribute("language", language);
		
		return Path.PAGE_LOGIN;
	}

}
