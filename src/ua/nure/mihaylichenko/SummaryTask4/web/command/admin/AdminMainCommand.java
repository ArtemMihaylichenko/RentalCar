package ua.nure.mihaylichenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Search;

/**
 * AdminMainCommand is a admin command.
 * This command print for jsp all users which has role
 * manager or client.
 * Has function: search user by parameters: name, surname, date of birth
 * @author A.Mihaylichenko
 *
 */
public class AdminMainCommand extends Command {

	private static final long serialVersionUID = -653286619029499415L;
	
	private static final Logger LOG = Logger.getLogger(AdminMainCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("AdminMainCommand starts");
		
		HttpSession session = request.getSession(false);
		
		List<User> users = null;
		List<String> sortValues = new ArrayList<>();
		sortValues.add("name");
		sortValues.add("surname");
		sortValues.add("login");
		sortValues.add("date");
		
		try {
			users = DAOFactory.getFactory().getUserDAO().getAllUsers();
			
			/** Choose users where roleId != 1 */
			List<User> us = new ArrayList<>();
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				if (user.getRoleId() != 1) {
					us.add(user);
				}
			}
			users = us;
			
			if (session.getAttribute("message") != null) {
				String message = (String) session.getAttribute("message");
				session.removeAttribute("message");
				
				request.setAttribute("message", message);
			}
			
			
			/** Search users */
			String searchBy = request.getParameter("searchBy");
			String search = new String(request.getParameter("search").getBytes("ISO-8859-1"), "UTF-8");;
			
			if (searchBy != null && search != null && !search.equals("")) {
				LOG.info("search users by " + searchBy + " equals " + search);
				users = Search.searchUsers(users, searchBy, search);	
				request.setAttribute("searchValue", searchBy);
				request.setAttribute("search", search);
			}
		}
		catch (Exception ex) {
			LOG.error("Exception in admin main command " + ex.getMessage());
			request.setAttribute("errMessage", "error");
		}
		request.setAttribute("sort", sortValues);
		request.setAttribute("users", users);
		LOG.info("request set attribute users --> " + users);
		
		return Path.PAGE_MAIN_ADMIN;
	}

}
