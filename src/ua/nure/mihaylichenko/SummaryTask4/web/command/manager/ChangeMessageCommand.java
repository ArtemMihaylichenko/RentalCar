package ua.nure.mihaylichenko.SummaryTask4.web.command.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Message;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

public class ChangeMessageCommand extends Command {

	private static final long serialVersionUID = 4016065221756370072L;
	
	private static final Logger LOG = Logger.getLogger(ChangeMessageCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ChangeMessageCommand starts");
		
		Message message = null;
		User client = null;
		
		try {
			message = DAOFactory.getFactory().getMessageDAO().
					getMessageById(Integer.valueOf(request.getParameter("messageId")));
			client = DAOFactory.getFactory().getUserDAO().getUserById(message.getClientId());
		}
		catch (Exception ex) {
			LOG.error("Exception in ChangeMessageCommand " + ex.getMessage());
		}
		
		request.setAttribute("message", message);
		LOG.info("request set attribute message " + message);
		request.setAttribute("client", client);
		LOG.info("request set attribute client " + client);
		
		return Path.PAGE_CHANGE_MESSAGE_MANAGER;
	}

}
