package ua.nure.mihaylichenko.SummaryTask4.web.command.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.Language;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;
import ua.nure.mihaylichenko.SummaryTask4.web.util.ChooseLanguage;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Parser;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Validator;

/**
 * EditSettingCommand is a common command
 * Command to accept settings parameters
 * from settings page.
 * @author A.Mihaylichenko
 *
 */
public class EditSettingsCommand extends Command {

	private static final long serialVersionUID = -7552324314351986019L;
	private static final Logger LOG = Logger.getLogger(EditSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("EditSettingsCommand starts");
		
		HttpSession session = request.getSession(false);
		User user = null;
		StringBuilder answer = new StringBuilder();
		String typeMessage = "";
		String forward = "";
		
		try {
			user = (User) session.getAttribute("user");
			
			/** 
			 * POST 
			 */
			if (request.getParameter("settings") != null) {
				String reqCommand = request.getParameter("settings");
			
			/**
			 *  Insert new password 
			 */
			if (reqCommand.equals("password")) {
			try {
				String oldPass = request.getParameter("oldPass");
				String newPass = request.getParameter("newPass");
				String repeatNewPass = request.getParameter("repeatNewPass");
				Validator.validatePassword(oldPass);
				Validator.validatePassword(newPass);
				Validator.validatePassword(repeatNewPass);
				
				if (!user.getPassword().equals(Parser.hash(oldPass))) {
					LOG.error("incorrect old password");
					throw new AppException("incorrect old password");
				}
				
				if (!newPass.equals(repeatNewPass)) {
					LOG.error("new password != repeat new password");
					throw new AppException("new password != repeat new password");
				}
				answer.append(ChooseLanguage.getValue("password.success", session));
				typeMessage = "success";
				user.setPassword(Parser.hash(newPass));
			}
			catch (Exception ex) {
				LOG.error("password is not accept ");
				answer.append(ChooseLanguage.getValue("password.error", session));
				typeMessage = "error";
			}
			}
			
			/** 
			 * Insert new language 
			 */
			else if (reqCommand.equals("language")) {
				String language = request.getParameter("selectLanguage");
				if (language.equals("ru")) {
					user.setLanguage(Language.RUSSIAN);
					session.setAttribute("language", "ru");
					LOG.debug("set language --> " + "ru");
				}
				if (language.equals("en")) {
					user.setLanguage(Language.ENGLISH);
					session.setAttribute("language", "en");
					LOG.debug("set language --> " + "en");
				}
				answer.append(ChooseLanguage.getValue("lang.success", session));
				typeMessage = "success";
				
			}
			
			/** 
			 * Insert new e-mail 
			 */
			else if (reqCommand.equals("mail")) {
			try {
				String oldMail = request.getParameter("oldMail");
				String newMail = request.getParameter("newMail");
				Validator.validateMail(oldMail);
				Validator.validateMail(newMail);
				if (!oldMail.equals(user.getMail())) {
					LOG.error("user mail != your input old mail");
					throw new AppException("user mail != your input old mail");
				}
				user.setMail(newMail);
				answer.append(ChooseLanguage.getValue("mail.success", session));
				typeMessage = "success";
			}
			catch (Exception ex) {
				LOG.error("mail is not accept ");
				answer.append(ChooseLanguage.getValue("mail.error", session));
				typeMessage = "error";
			}
			}
			DAOFactory.getFactory().getUserDAO().updateUserById(user.getId(), user);
			LOG.debug("user is updating in BD --> " + user);
			
			String answ = String.valueOf(answer);
			session.setAttribute("answerSet", answ);
			session.setAttribute("typeSet", typeMessage);
			session.setAttribute("user", user);
			LOG.debug("session set update user --> " + user);
			
			forward = Path.COMMAND_VIEW_SETTINGS;
			}
			/** GET */
			else {
				forward = Path.COMMAND_VIEW_SETTINGS;
				
			}
		}
		catch (Exception ex) {
			LOG.error("Edit settings exception" + ex.getMessage());
			answer.append(ChooseLanguage.getValue("settings.error", session));
			typeMessage = "error";
			String answ = String.valueOf(answer);
			session.setAttribute("answerSet", answ);
			session.setAttribute("typeSet", typeMessage);
			forward = Path.COMMAND_EDIT_SETTINGS;
		}		
		
		return forward;
	}

}

