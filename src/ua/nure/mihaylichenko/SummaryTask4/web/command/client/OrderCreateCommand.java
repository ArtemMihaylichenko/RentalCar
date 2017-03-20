package ua.nure.mihaylichenko.SummaryTask4.web.command.client;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.OrderDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.exception.Messages;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;
import ua.nure.mihaylichenko.SummaryTask4.web.util.ChooseLanguage;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Parser;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Validator;

/**
 * Client command to create order.
 * Get parameters for order and create order in DB.
 * @author A.Mihaylichenko
 *
 */
public class OrderCreateCommand extends Command {

	private static final long serialVersionUID = -2272646285351165297L;
	private static final Logger LOG = Logger.getLogger(OrderCreateCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("CreateOrderCommand starts");
		HttpSession session = request.getSession(false);
		
		String error = "";
		String passportError = "";
		String dateError = "";
		StringBuilder answerCreateOrder = new StringBuilder();
		try {
			User user = (User) session.getAttribute("user");
			int userId = user.getId();
			
			int carId = (int) session.getAttribute("carId");
			String passport = request.getParameter("passport");
			
			int driver = 0;
			String driverStr = request.getParameter("driver");
				if (driverStr.equals("Yes") || driverStr.equals("Да")) {
						driver = 1;
				}
		
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			
			/** Expect passport */
			try {
				Validator.validatePassport(passport);
			}
			catch (Exception ex) {
				LOG.error("incorrect passport" + " " + ex.getMessage());
				passportError = ChooseLanguage.getValue("createOrder.errorPassport", session);
				answerCreateOrder.append(passportError);
				LOG.error("Passport error");
			}
			
			Date startDate = null;
			Date endDate = null;
			
			/** Expect dates */
			try {
				Validator.validateDate(startDateStr);
				Validator.validateDate(endDateStr);
			
				startDate = Parser.stringToDate(startDateStr);
				System.out.println(startDate.toString());
				endDate = Parser.stringToDate(endDateStr);
				System.out.println(endDate.toString());
				if (endDate.getTime() - startDate.getTime() < 0) {
					LOG.error("Start date > end date");
					throw new AppException("Start date > end date");
				}
			}
			catch (Exception ex) {
				LOG.error("incorrect dates" + " " + ex.getMessage());
				dateError = ChooseLanguage.getValue("createOrder.errorDate", session);;
				answerCreateOrder.append(dateError);
				LOG.error("Date error" + dateError);
			}
			if (!passportError.equals("") || !dateError.equals("")) {
				throw new AppException("Error input");
			}

			OrderDAO orderDAO = DAOFactory.getFactory().getOrderDAO();
			orderDAO.makeOrderWithTransaction(userId, carId, passport, driver, startDate, endDate);	
		}
		catch (Exception ex) {
			LOG.error(Messages.CANNOT_CREATE_ORDER + " " + ex.getMessage());
			if (passportError.equals("") && dateError.equals("")) {
				error = ChooseLanguage.getValue("createOrder.error", session);
				answerCreateOrder.append(error);
				LOG.error("Total error");
			}
			session.setAttribute("answerCreateOrder", answerCreateOrder.toString());
			return Path.COMMAND_ORDER_CREATE_FORM;
		}
		
		session.removeAttribute("carId");
		
		return Path.COMMAND_CLIENT_ORDERS;		
	}

}
