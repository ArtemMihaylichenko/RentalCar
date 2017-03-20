package ua.nure.mihaylichenko.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.beans.OrderBean;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * ManagerMainCommand is a manager command
 * Open all orders where manager name is nobody or 
 * manager name is name of manager who open orders.
 * Used OrderBean for show information.
 * @author A.Mihaylichenko
 * 
 * @see OrderBean
 *
 */
public class ManagerMainCommand extends Command {

	private static final long serialVersionUID = -3515064806590797931L;
	private static final Logger LOG = Logger.getLogger(ManagerMainCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ManagerMainCommand starts");

		List<Order> orders = null;
		List<OrderBean> orderb = null;
		HttpSession session = null;
		User userManager = null;
		
		try {
			session = request.getSession(true);
			userManager = (User) session.getAttribute("user");
			orders = DAOFactory.getFactory().getOrderDAO().getOrdersByManagerName(userManager.getSurName());
			orderb = new ArrayList<>();
			
			
			/**
			 *  Expect session messages from changeOrder command
			 */
			if (session.getAttribute("message") != null) {
				String mess = (String) session.getAttribute("message");
				
				session.removeAttribute("message");
				
				request.setAttribute("message", mess);
			}
			/**
			 *  Expect session messages from deleteOrder command
			 */
			if (session.getAttribute("messageDelete") != null) {
				String mess = (String) session.getAttribute("messageDelete");
				session.removeAttribute("messageDelete");
				request.setAttribute("messageDelete", mess);
			}
			/** Expect session end */
			
			
			
			for (int i = 0; i < orders.size(); i++) {
				Order order = orders.get(i);
				OrderBean bean = new OrderBean();
				Bill bill = DAOFactory.getFactory().getBillDAO().getBillById(order.getBillId());
				Car car = DAOFactory.getFactory().getCarDAO().getCarById(order.getCarId());
				User user = DAOFactory.getFactory().getUserDAO().getUserById(order.getUserId());
				bean.setBill(bill);
				bean.setCar(car);
				bean.setOrder(order);
				bean.setUser(user);
				if (bill.getRentStatus().equals(BillStatus.NOPAID) 
						|| bill.getRepairStatus().equals(BillStatus.NOPAID)) {
					bean.setBillStatus("NOPAID");
				}
				else {
					bean.setBillStatus("PAID");
				}
				orderb.add(bean);
			}
			
			Collections.sort(orderb, new Comparator<OrderBean>() {
				@Override
				public int compare(OrderBean o1, OrderBean o2) {
					long time2 = o2.getOrder().getOrderDate().getTime();
					long time1 = o1.getOrder().getOrderDate().getTime();
					Long res = time2-time1;
					return res.intValue();
				}
			
			});
		}
		catch (Exception ex) {
			LOG.error("Exception in managerMainCommand" + ex.getMessage());
			throw new AppException("Exception in managerMainCommand");
		}
		request.setAttribute("orders", orders);
		LOG.debug("ManagerMainCommand requet set attr orders --> " + orders);
		request.setAttribute("orderb", orderb);
		LOG.debug("ManagerMainCommand requet set attr orderb --> " + orderb );
		
		LOG.debug("ManagerMainCommand end");
		
		return Path.PAGE_MAIN_MANAGER;
	}

}
