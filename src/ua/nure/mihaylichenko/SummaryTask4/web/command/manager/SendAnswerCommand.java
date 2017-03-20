package ua.nure.mihaylichenko.SummaryTask4.web.command.manager;

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
import ua.nure.mihaylichenko.SummaryTask4.web.util.Mail;

/**
 * Send answer for client questions on client email.
 * And delete client message which have answer, form DB.
 * @author A.Mihaylichenko
 *
 */
public class SendAnswerCommand extends Command {

	private static final long serialVersionUID = 4847681909244577256L;
	private static final Logger LOG = Logger.getLogger(SendAnswerCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("sendMessageCommand starts");
		
		HttpSession session = request.getSession(false);
		
		User client = null;
		Message message = null;
		String subject = null;
		String text = null;
		String answerMessage = null;
		String typeAnswer = null;
		
		try {
			
			client = DAOFactory.getFactory().getUserDAO().
					getUserById(Integer.valueOf(request.getParameter("userId")));
			message = DAOFactory.getFactory().getMessageDAO().
					getMessageById(Integer.valueOf(request.getParameter("messageId")));
			
		
			if (request.getParameter("type") != null 
					&& request.getParameter("type").equals("send")) {
				subject = message.getSubject();
				text = message.getMessage();
				String managerMessage = " (" + text + "). " +
						System.lineSeparator() + System.lineSeparator() + 
						request.getParameter("answerMessage")
						+System.lineSeparator() + " RENTAL CAR ";
				String managerSubject = ChooseLanguage.getValue("message.answer", session)
						+ ": " + subject;
				String mail = client.getMail();
				
				try {
					Mail.sendMail(mail, managerSubject, managerMessage);
				}
				catch (Exception ex) {
					LOG.error("Error of send mail " + ex.getMessage());
					answerMessage = ChooseLanguage.getValue("message.no", session);
					throw new AppException("No send mail + " + ex.getMessage());
				}
				try {
					DAOFactory.getFactory().getMessageDAO().deleteMessageById(message.getId());
				}
				catch (Exception ex) {
					LOG.error("Error of delete message in DB " + ex.getMessage());
					answerMessage = ChooseLanguage.getValue("message.no", session);
					throw new AppException("No delete message + " + ex.getMessage());
				}
				answerMessage = ChooseLanguage.getValue("message.sendConf", session);
				typeAnswer = "success";
				session.setAttribute("answerMessage", answerMessage);
				LOG.info("session set attribute --> " + answerMessage);
		}
		
	}
		catch (Exception ex) {
			if (answerMessage == null) {
				answerMessage = ChooseLanguage.getValue("message.no", session);
			}
			typeAnswer = "error";
			LOG.error("Exception of send Message command " + ex.getMessage());
			session.setAttribute("answerMessage", answerMessage);
		}
		
		session.setAttribute("typeAnswer", typeAnswer);
		
		return Path.COMMAND_ALL_MESSAGES_MANAGER;
	}
}


