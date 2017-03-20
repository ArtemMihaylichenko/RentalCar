package ua.nure.mihaylichenko.SummaryTask4.web.command.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * DeleteOrder is a manager command.
 * This command delete order if this order has status CLOSE or ABORT.
 * Use delete order with transaction from OrderDAO. This command delete
 * bill from this order and then delete order.
 * @author A.Mihaylichenko
 * 
 * @see OrderDAO
 *
 */
public class DeleteOrderCommand extends Command {

	private static final long serialVersionUID = 9194861514646714688L;
	
	private static final Logger LOG = Logger.getLogger(DeleteOrderCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("deleteOrderCommand starts");
		
		HttpSession session = request.getSession(false);
		String message = "";
		
		try {
			int orderId = Integer.valueOf(request.getParameter("orderId"));
			DAOFactory.getFactory().getOrderDAO().deleteOrderByIdWithTransaction(orderId);
			message = "delete";
			LOG.debug("Order deleted");
		}
		catch (Exception ex) {
			LOG.error("order not delete " + ex.getMessage());
			message = "not delete";
		}
		
		session.setAttribute("messageDelete", message);
		
		LOG.debug("deleteOrderCommand end");
		
		return Path.COMMAND_MAIN_MANAGER;
	}
	
	

}
