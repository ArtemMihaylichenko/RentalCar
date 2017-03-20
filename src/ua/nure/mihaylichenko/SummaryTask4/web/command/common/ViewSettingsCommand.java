package ua.nure.mihaylichenko.SummaryTask4.web.command.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * ViewSettingsCommand is a common command
 * Redirect to page settings from all pages
 * but from out control command no.
 * @author A.Mihaylichenko
 *
 */
public class ViewSettingsCommand extends Command {

	private static final long serialVersionUID = -5785276874876093739L;
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		
		HttpSession session = request.getSession(false);
		
		if (session.getAttribute("typeSet") != null) {
			String type = (String) session.getAttribute("typeSet");
			session.removeAttribute("typeSet");
			request.setAttribute("typeSet", type);
		}
		if (session.getAttribute("answerSet") != null) {
			String answ = (String) session.getAttribute("answerSet");
			session.removeAttribute("answerSet");
			request.setAttribute("answerSet", answ);
		}
		return Path.PAGE_SETTINGS;
	}

}
