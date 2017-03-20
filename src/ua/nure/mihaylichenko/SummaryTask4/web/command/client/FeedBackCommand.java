package ua.nure.mihaylichenko.SummaryTask4.web.command.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Message;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;
import ua.nure.mihaylichenko.SummaryTask4.web.util.ChooseLanguage;

/**
 * With this command client send letter for managers.
 * This message save in DB.
 * @author A.Mihaylichenko
 *
 */
public class FeedBackCommand extends Command {

	private static final long serialVersionUID = 9205218165914722370L;
	private static final Logger LOG = Logger.getLogger(FeedBackCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("FeedBackCommand starts");
		
		HttpSession session = request.getSession(false);
		User user = null;
		String typeAnswer = null;
		String message = null;
		String forward = null;
		
		try {
			user = (User) session.getAttribute("user");
			
			/** 
			 * 
			 * -----------------POST-------------------
			 * 
			 */
			if (request.getParameter("type") != null 
					&& request.getParameter("type").equals("send")) {
				String subject = request.getParameter("subject");
				String text = request.getParameter("text");
				int clientId = user.getId();
				
				if (text.length() > 350) {
					LOG.error("text size > 350");
					typeAnswer = "error";
					message = ChooseLanguage.getValue("message.no", session);
					message = message + " " + ChooseLanguage.getValue("message.error.len", session);
					throw new AppException("text size > 350");
				}
				else if (subject == null || subject.length() < 3 || text == null || text.length() < 4) {
					LOG.error("text or subject is empty");
					typeAnswer = "error";
					message = ChooseLanguage.getValue("message.no", session);
					throw new AppException("text size > 350");
				}
				
				/** Create message */
				Message mes = new Message();
				mes.setClientId(clientId);
				mes.setSubject(subject);
				mes.setMessage(text);
				
				
				try {
					DAOFactory.getFactory().getMessageDAO().insertMessage(mes);
				}
				catch (Exception ex) {
					LOG.error("Exception of adding message in DB " + ex.getMessage());
					typeAnswer = "error";
					message = ChooseLanguage.getValue("message.no", session);
					throw new AppException("no add message to db");
				}
				message = ChooseLanguage.getValue("message.yes", session);
				typeAnswer = "success";
				
				session.setAttribute("typeAnswerSend", typeAnswer);
				session.setAttribute("messageSend", message);
				
				forward = Path.COMMAND_CLIENT_FEEDBACK;
				LOG.info("request set attribute message --> " + message);
			}
			/**
			 * 
			 *  -------------GET--------------- 
			 */
			else {
				if (session.getAttribute("typeAnswerSend") != null) {
					String type = (String) session.getAttribute("typeAnswerSend");
					String messageSend = (String) session.getAttribute("messageSend");
					
					session.removeAttribute("typeAnswerSend");
					session.removeAttribute("messageSend");
					
					request.setAttribute("typeAnswerSend", type);
					request.setAttribute("messageSend", messageSend);
				}
				forward = Path.PAGE_FEEDBACK_CLIENT;
			}
		}
		catch (Exception ex) {
			if (message == null || typeAnswer == null) {
				typeAnswer = "error";
				message = ChooseLanguage.getValue("message.no", session);
			}	
			forward = Path.COMMAND_CLIENT_FEEDBACK;
			
			session.setAttribute("typeAnswerSend", typeAnswer);
			session.setAttribute("messageSend", message);
			LOG.info("session set attribute message --> " + message);
		}
		
		request.setAttribute("user", user);
		
		LOG.debug("FeedBackCommand end");
		
		return forward;
	}

}
