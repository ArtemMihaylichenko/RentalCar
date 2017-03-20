package ua.nure.mihaylichenko.SummaryTask4.web.command.client;

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
 * ClientOrdersCommand is a client command.
 * Show all orders of some client.
 * @author A.Mihaylichenko
 *
 */
public class ClientOrdersCommand extends Command {

	private static final long serialVersionUID = -4934277279759273165L;
	
	private static final Logger LOG = Logger.getLogger(ClientOrdersCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ClientOrdersCommand starts");
		
		HttpSession session = request.getSession(false);
		String answer = "";
		try {
		User user = (User) session.getAttribute("user");
		List<Order> orders = DAOFactory.getFactory().getOrderDAO().getOrdersByUserId(user.getId());
		List<OrderBean> orderBeans = new ArrayList<>();
		
		/** Remove settings answers */
		if (session.getAttribute("answerSet") != null) {
			session.removeAttribute("answerSet");
			session.removeAttribute("typeSet");
		}
		
		if (session.getAttribute("answer") != null) {
			answer = (String) session.getAttribute("answer");
			session.removeAttribute("answer");
		}
		
		for (int i = 0; i < orders.size(); i++) {
			Car car = DAOFactory.getFactory().getCarDAO().getCarById(orders.get(i).getCarId());
			Bill bill = DAOFactory.getFactory().getBillDAO().getBillById(orders.get(i).getBillId());
			OrderBean orderb = new OrderBean();
			orderb.setBill(bill);
			orderb.setCar(car);
			orderb.setOrder(orders.get(i));
			
			if (bill.getRentStatus().equals(BillStatus.NOPAID) || bill.getRepairStatus().equals(BillStatus.NOPAID)) {
				orderb.setBillStatus("NOPAID");
			}
			
			else {
				orderb.setBillStatus("PAID");
			}
			orderBeans.add(orderb);
		}
		
		/** 
		 * Sort orders by date. First new, last old 
		 */
		Collections.sort(orderBeans, new Comparator<OrderBean>() {
			@Override
			public int compare(OrderBean o1, OrderBean o2) {
				return Integer.valueOf((int) (o2.getOrder().getOrderDate().getTime()-(o1.getOrder().getOrderDate().getTime())));
			}
		
		});
		
		LOG.trace("Get from BD list of orders --> " + orders);
		
		request.setAttribute("orderBeans", orderBeans);	
		LOG.trace("Set the request attribute: orderBeans --> " + orderBeans);
		
		request.setAttribute("answer", answer);
		}
		catch(Exception ex) {
			LOG.error("Exception in clientOrdersCommand. Cannot get orders");
		}
		LOG.debug("ClientOrdersCommand finished");
		return Path.PAGE_CLIENT_ORDERS;
	}

}
