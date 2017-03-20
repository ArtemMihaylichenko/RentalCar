package ua.nure.mihaylichenko.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Message;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

public class AllMessagesCommand extends Command {

	private static final long serialVersionUID = 3686932337094924116L;
	private static final Logger LOG = Logger.getLogger(AllMessagesCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		
		LOG.debug("AllMessagesCommand starts");
		HttpSession session = request.getSession(false);
		List<Message> messages = null;
		
		try {
			/** Expect session for messages and send them to request */
			if (session.getAttribute("answerMessage") != null) {
				String answer = (String) session.getAttribute("answerMessage");
				String type = (String) session.getAttribute("typeAnswer");
				
				session.removeAttribute("answerMessage");
				session.removeAttribute("typeAnswer");
				
				request.setAttribute("answerMessage", answer);
				request.setAttribute("typeAnswer", type);
			}
			
			
			messages = DAOFactory.getFactory().getMessageDAO().getAllMessage();
			
		}
		catch (Exception ex) {
			LOG.error("Exception in AllMessagesCommand " + ex.getMessage());
		}
		
		LOG.info("request set attribute messages " + messages);
		request.setAttribute("messages", messages);
		
		return Path.PAGE_ALL_MESSAGES_MANAGER;
	}

}
