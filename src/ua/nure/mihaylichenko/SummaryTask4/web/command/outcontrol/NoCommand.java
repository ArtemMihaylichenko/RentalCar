package ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * No command is out control command.
 * @author A.Mihaylichenko
 *
 */
public class NoCommand extends Command {

	private static final long serialVersionUID = 6467010099117453222L;
	
	private static final Logger LOG = Logger.getLogger(NoCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command NoCommand starts");
		
		String errorMessage = "No commands";
		request.setAttribute("errorMessage", errorMessage);
		LOG.error("Set the request attribute: errorMessage --> " + errorMessage);

		LOG.debug("Command NoCommand finished");
		return Path.PAGE_ERROR;
	}

}
