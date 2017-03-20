package ua.nure.mihaylichenko.SummaryTask4.web.command.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.UserStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.exception.Messages;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * OrderCreateFormCommand is a client command.
 * Command for redirect to create order page.
 * Expect user status 
 * @author A.Mihaylichenko
 *
 */
public class OrderCreateFormCommand extends Command {

	private static final long serialVersionUID = -6271519671300262580L;
	private static final Logger LOG = Logger.getLogger(OrderCreateFormCommand.class);
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("OrderCreateFormCommand starts");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		int carId = 0;
		if (request.getParameter("carId") != null) {
			carId = Integer.valueOf(request.getParameter("carId"));
			session.setAttribute("carId", carId);
		}
		else {
			carId = (int)session.getAttribute("carId");
		}
		if (user.getUserStatus().equals(UserStatus.BLOCK)) {
			LOG.error(Messages.USER_HAVE_STATUS_BLOCK);
		}
		/** Remove session attribute answerCreateOrder 
		 * and send this message to request
		 */
		if (session.getAttribute("answerCreateOrder") != null) {
			String answerCreateOrder = (String) session.getAttribute("answerCreateOrder");
			session.removeAttribute("answerCreateOrder");
			request.setAttribute("answerCreateOrder", answerCreateOrder);
		}
		
		return Path.PAGE_CREATE_ORDER_FORM;
	}

}
