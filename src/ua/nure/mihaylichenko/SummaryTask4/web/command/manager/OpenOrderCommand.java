package ua.nure.mihaylichenko.SummaryTask4.web.command.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Bill;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Order;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.BillStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.beans.OrderBean;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * Command to open information of some order.
 * @author A.Mihaylichenko
 *
 */
public class OpenOrderCommand extends Command {

	private static final long serialVersionUID = 3594129809663867841L;
	private static final Logger LOG = Logger.getLogger(OpenOrderCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("OpenOrderCommand starts");
		User user = null;
		Order order = null;
		Bill bill = null;
		Car car = null;
		OrderBean orderb = new OrderBean();
		
		try {
			user = DAOFactory.getFactory().getUserDAO().getUserById(Integer.valueOf(request.getParameter("clientId")));
			order = DAOFactory.getFactory().getOrderDAO().getOrderById(Integer.valueOf(request.getParameter("orderId")));
			bill = DAOFactory.getFactory().getBillDAO().getBillById(Integer.valueOf(request.getParameter("billId")));
			car = DAOFactory.getFactory().getCarDAO().getCarById(Integer.valueOf(request.getParameter("carId")));
			
			orderb.setBill(bill);
			LOG.info("OpenOrderCommand get bill from DB --> " + bill);
			orderb.setCar(car);
			LOG.info("OpenOrderCommand get car from DB --> " + car);
			orderb.setOrder(order);
			LOG.info("OpenOrderCommand get order from DB --> " + order);
			orderb.setUser(user);
			LOG.info("OpenOrderCommand get user from DB--> " + user);
			
			if (bill.getRentStatus().equals(BillStatus.NOPAID) 
					|| bill.getRepairStatus().equals(BillStatus.NOPAID)) {
				orderb.setBillStatus("NOPAID");
			}
			else {
				orderb.setBillStatus("PAID");
			}
		}
		catch (Exception ex) {
			LOG.error("Exception in OpenOrderCommand" + ex.getMessage());
			throw new AppException("Exception in OpenOrderCommand");
		}
		
		request.setAttribute("orderb", orderb);
		LOG.debug("OpenOrderCommand set attribute to request orderb --> " + orderb);
		LOG.debug("OpenOrderCommand end");
		
		return Path.PAGE_ORDER_VIEW_MANAGER;
	}

}
