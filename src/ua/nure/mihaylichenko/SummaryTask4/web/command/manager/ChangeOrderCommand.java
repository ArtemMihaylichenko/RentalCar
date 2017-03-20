package ua.nure.mihaylichenko.SummaryTask4.web.command.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Bill;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Order;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.BillStatus;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.OrderStatus;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.UserStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.beans.OrderBean;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * ChangeOrderCommand is a manager command.
 * This command make changes for order. 
 * Can change order status and add bill for repair
 * @author A.Mihaylichenko
 *
 */
public class ChangeOrderCommand extends Command {

	private static final long serialVersionUID = 6868624975846412713L;
	private static final Logger LOG = Logger.getLogger(ChangeOrderCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ChangeOrderCommand starts");
		
		Car car = null;
		User client = null;
		Bill bill = null;
		User manager = null;
		Order order = null;
		HttpSession session = null;
		String type = null;
		String message = "";
		OrderBean orderb = new OrderBean();
		
		
		try {
			session = request.getSession(false);
			
			
			type = request.getParameter("type");
			car = DAOFactory.getFactory().getCarDAO().
					getCarById(Integer.valueOf(request.getParameter("carId")));
			
			client = DAOFactory.getFactory().getUserDAO().
					getUserById(Integer.valueOf(request.getParameter("userId")));
			
			bill = DAOFactory.getFactory().getBillDAO().
					getBillById(Integer.valueOf(request.getParameter("billId")));
			
			order = DAOFactory.getFactory().getOrderDAO().
					getOrderById(Integer.valueOf(request.getParameter("orderId")));
			
			manager = (User) session.getAttribute("user");
			
			type = request.getParameter("type");
			LOG.info("request type: " + type);
			
			if (!type.equals("") && type != null) {
			if (type.equals("confirm")) {
				if (client.getUserStatus().equals(UserStatus.BLOCK)) {
					message = "error";
					LOG.info("client is block");
				}
				else {
					order.setOrderStatus(OrderStatus.CONFIRMED);
					
					DAOFactory.getFactory().getOrderDAO().
					updateOrderStatusById(order.getId(), OrderStatus.CONFIRMED);
					
					order.setManagerName(manager.getSurName());
					
					DAOFactory.getFactory().getOrderDAO().
					updateOrderManagerById(order.getId(), manager.getSurName());
					message = "success";
					LOG.info("order status is changed " + order.getOrderStatus());
				}
			}
			else if (type.equals("decline")) {
				order.setOrderStatus(OrderStatus.ABORTED);
				
				DAOFactory.getFactory().getOrderDAO().
				updateOrderStatusById(order.getId(), OrderStatus.ABORTED);
				
				order.setManagerName(manager.getSurName());
				
				DAOFactory.getFactory().getOrderDAO().
				updateOrderManagerById(order.getId(), manager.getSurName());
				
				message = "success";
				LOG.info("order status is changed " + order.getOrderStatus());
				LOG.info("order manager is changed " + order.getManagerName());
			}
			else if (type.equals("close")) {
				if (bill.getRentStatus().equals(BillStatus.NOPAID) || 
						bill.getRepairStatus().equals(BillStatus.NOPAID)) {
					message = "nopaid";
					LOG.info("order status is no changed bill status is paid");
				}
				else {
					order.setOrderStatus(OrderStatus.CLOSED);
					
					DAOFactory.getFactory().getOrderDAO().
					updateOrderStatusById(order.getId(), OrderStatus.CLOSED);
					
					message = "success";
					LOG.info("order status is changed " + order.getOrderStatus());
				}
				
			}
			else if (type.equals("repair")) {
				double cost = Double.valueOf(request.getParameter("repairPrice"));
				cost += bill.getRepair();
				bill.setRepair(cost);
				if (cost != 0) {
					bill.setRepairStatus(BillStatus.NOPAID);
					bill.setRepair(cost);
					
					DAOFactory.getFactory().getBillDAO().
					updateBill(bill.getId(), bill.getRent(), cost, bill.getRentStatus(), BillStatus.NOPAID);
					LOG.info("bill is changed " + bill);
					message = "success";
				}
				orderb.setBill(bill);
				orderb.setCar(car);
				orderb.setOrder(order);
				orderb.setUser(client);
				
				if (bill.getRentStatus().equals(BillStatus.NOPAID) ||
						bill.getRepairStatus().equals(BillStatus.NOPAID)) {
					orderb.setBillStatus("NOPAID");
				}
				else {
					orderb.setBillStatus("PAID");
				}
			}
			session.setAttribute("orderb", orderb);
			LOG.info("request set orderb --> " + orderb);
			
			session.setAttribute("message", message);
			
			}
			
		}
		catch (Exception ex) {
			LOG.error("Exception in ChangeOrderCommand " + ex.getMessage());
			message = "error";
			session.setAttribute("orderb", orderb);
			LOG.info("request set orderb --> " + orderb);
			
			session.setAttribute("message", message);
			
		}
		
		return Path.COMMAND_MAIN_MANAGER;
	}

}
