package ua.nure.mihaylichenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Order;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.OrderStatus;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.UserStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * ChangeUserCommand is a admin command.
 * This command make changes for some user.
 * (Block or Unblock) and (Make manager or make client)
 * @author A.Miahylichenko
 *
 */
public class ChangeUserCommand extends Command {

	private static final long serialVersionUID = -2025918233955119418L;
	
	private static final Logger LOG = Logger.getLogger(ChangeUserCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ChangeUserCommand starts");
		
		String message = "";
		User user = null;
		String type = "";
		HttpSession session = request.getSession(false);
		
		try {
			user = DAOFactory.getFactory().getUserDAO().
					getUserById(Integer.valueOf(request.getParameter("userId")));
			type = request.getParameter("type");
			
			/** Unblock user */
			if (type.equals("unblock")) {
				user.setUserStatus(UserStatus.ACTIVE);
				message = "statusUnBlock";
				DAOFactory.getFactory().getUserDAO().updateUserById(user.getId(), user);
				LOG.info("user --> " + user + " is unblocked");
			}
			
			/** Block user */
			else if (type.equals("block")) {
				if (user.getRoleId() == 3) {
				user.setUserStatus(UserStatus.BLOCK);
				message = "statusBlock";
				DAOFactory.getFactory().getUserDAO().updateUserById(user.getId(), user);
				List<Order> orders = DAOFactory.getFactory().getOrderDAO().getOrdersByUserId(user.getId());
				
				/** Delete open orders by this user */
				
				for (int i = 0; i < orders.size(); i++) {
					if (orders.get(i).getOrderStatus().equals(OrderStatus.OPENED)) {
						DAOFactory.getFactory().getOrderDAO().
						deleteOrderByIdWithTransaction(orders.get(i).getId());
					}
				}
				LOG.info("user --> " + user + " is blocked");
				}
				else {
					message = "blockErrorManager";
				}
			}
			
			/** Change user to client */
			else if (type.equals("toClient")) {
		//		List<Order> orders = DAOFactory.getFactory().getOrderDAO().getOrdersByStatus(OrderStatus.CONFIRMED);
				
				int countConfOrders = DAOFactory.getFactory().getOrderDAO()
						.getOrdersConfirmedCountByManagerName(user.getSurName());
				
				boolean flag = false;
				
				if (countConfOrders > 0) {
					flag = true;
				}
				
			/*	for (int i = 0; i < orders.size(); i++) {
					Order order = orders.get(i);
					if (order.getManagerName().equals(user.getSurName())) {
						flag = true;
					}
				} */
				if (!flag) {
					user.setRoleId(3);
					message = "role";
					DAOFactory.getFactory().getUserDAO().updateUserById(user.getId(), user);
					LOG.info("user --> " + user + " is client");
				}
				else {
					message = "roleErrManager";
					
				}
			}
			
			/** Change user to manager */
			else if (type.equals("toManager")) {
		//		List<Order> orders = DAOFactory.getFactory().getOrderDAO().getOrdersByUserId(user.getId());
				
				boolean flag = false;
		
				int count = DAOFactory.getFactory().getOrderDAO().getOrdersCountByClientId(user.getId());
				
				if (count > 0) {
					flag = true;
					
				}
				
				/*		for (int i = 0; i < orders.size(); i++) {
					if (orders.get(i).getOrderStatus().equals(OrderStatus.OPENED) || 
						orders.get(i).getOrderStatus().equals(OrderStatus.CONFIRMED)) {
						flag = true;
					}
				}*/
				if (!flag) {
					user.setRoleId(2);
					message = "role";
					DAOFactory.getFactory().getUserDAO().updateUserById(user.getId(), user);
					LOG.info("user --> " + user + " is client");
				}
				else {
					message = "roleErr";
				}
			}
			
		}
		catch (Exception ex) {
			LOG.error("Exception in ChangeUserCommand " + ex);
			message = "error";
		}
		session.setAttribute("message", message);
		LOG.debug("request set attribute message --> " + message);
		
		return Path.COMMAND_MAIN_ADMIN;
	}

}