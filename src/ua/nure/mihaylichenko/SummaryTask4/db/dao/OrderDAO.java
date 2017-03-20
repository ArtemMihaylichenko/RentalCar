package ua.nure.mihaylichenko.SummaryTask4.db.dao;

import java.sql.Date;
import java.util.List;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.Order;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.BillStatus;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.OrderStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/**
 * Interface for implementation Order entity to DB
 * @author A.Mihaylichenko 
 * 
 * @see Order
 * @see MySQLOrderDAO
 */
public interface OrderDAO {

	void insertOrder(Order order) throws DBException;
	
	void updateOrderManagerById(int id, String managerName) throws DBException;

	List<Order> getAllOrders() throws DBException;

	Order getOrderById(int orderId) throws DBException;

	List<Order> getOrdersByUserId(int userId) throws DBException;
	
	List<Order> getOrdersByCarId(int carId) throws DBException;

	List<Order> getOrdersByStatus(OrderStatus status) throws DBException;

	List<Order> getOrdersByBillRentStatus(BillStatus status) throws DBException;
	
	public List<Order> getOrdersByBillRepairStatus(BillStatus repairStatus) throws DBException;

	void deleteOrderById(int id) throws DBException;

	void deleteOrderByIdWithTransaction(int id) throws DBException;

	void makeOrderWithTransaction(int userId, int carId, String passport, int driver, Date startDate, Date endDate)
			throws DBException;

	void updateOrderStatusById(int id, OrderStatus status) throws DBException;
	
	int getOrdersConfirmedCountByManagerName(String manager) throws DBException;
	
	int getOrdersCountByClientId(int userId) throws DBException;
	
	List<Order> getOrdersByManagerName(String surname) throws DBException;
}
