package ua.nure.mihaylichenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Order;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.OrderStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * deleteCarCommand is a admin command.
 * This command delete cars from DB and system.
 * Before deleting first off all expected orders with this car.
 * @author A.Mihaylichenko
 *
 */
public class deleteCarCommand extends Command {

	private static final long serialVersionUID = -9220756482913930074L;
	
	private static final Logger LOG = Logger.getLogger(deleteCarCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("deleteCarCommand starts");
		
		HttpSession session = request.getSession(false);
		Car car = null;
		String messageDelete = "";
		
		try {
			car = DAOFactory.getFactory().getCarDAO().
					getCarById(Integer.valueOf(request.getParameter("carId")));
			
			List<Order> orders = DAOFactory.getFactory().getOrderDAO().getOrdersByCarId(car.getId());
			
			/** Expect all orders with this car. 
			 * If orders contain no close or aborted order throw exception. 
			 */
			for (int i = 0; i < orders.size(); i++) {
				Order order = orders.get(i);
				if (order.getOrderStatus().equals(OrderStatus.OPENED) 
						|| order.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
					throw new AppException("Cannot delete car with no closed orders");
				}
			}
			
			/** Delete orders with this car */
			for (int i = 0; i < orders.size(); i++) {
				Order order = orders.get(i);
				if (!order.getOrderStatus().equals(OrderStatus.OPENED) 
						|| !order.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
					DAOFactory.getFactory().getOrderDAO().deleteOrderByIdWithTransaction(order.getId());
				}
			}
			LOG.info("orders with car " + car.getId() + " was deleted");
			
			/** Delete car */
			DAOFactory.getFactory().getCarDAO().deleteCarById(car.getId());
			LOG.info("car was deleted --> " + car);
			
			messageDelete = "success";
		}
		catch (Exception ex) {
			messageDelete = "error";
			LOG.error("Error of delete car --> " + car);
		}
		
		session.setAttribute("messageDelete", messageDelete);
		LOG.info("request set attribute message --> " + messageDelete);
		
		LOG.debug("deleteCarCommand end");
		
		return Path.COMMAND_CARS_ADMIN;
	}

}
