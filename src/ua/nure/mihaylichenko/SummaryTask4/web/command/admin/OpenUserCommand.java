package ua.nure.mihaylichenko.SummaryTask4.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * OpenUserCommand - admin command
 * Get user from DB.
 * Open information about this user.
 * 
 * @author A.Mihaylichenko
 *
 */
public class OpenUserCommand extends Command {

	private static final long serialVersionUID = 6421064485196701700L;
	private static final Logger LOG = Logger.getLogger(OpenUserCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("OpenUserCommand starts");
		User user = null;
		
		try {
			user = DAOFactory.getFactory().getUserDAO().
					getUserById(Integer.valueOf(request.getParameter("userId")));
			
		}
		catch (Exception ex) {
			LOG.error("Exception in OpenUserCommand " + ex.getMessage());
			throw new AppException("Exception of open user");
		}
		
		request.setAttribute("user", user);
		LOG.debug("request set attribute user --> " + user);
		LOG.debug("OpenUserCommand end");
		
		return Path.PAGE_USER_PAGE_ADMIN;
	}

}
