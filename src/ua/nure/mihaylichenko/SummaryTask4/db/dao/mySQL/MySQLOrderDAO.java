 package ua.nure.mihaylichenko.SummaryTask4.db.dao.mySQL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.BillDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.BusyDateDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.CarDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.OrderDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.UserDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Bill;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.BusyDate;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Order;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.BillStatus;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.OrderStatus;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.UserStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;
import ua.nure.mihaylichenko.SummaryTask4.exception.Messages;

/**
 * Implementation for OrderDAO.
 * Using with MySQL
 * Main class for DB. Contains method for transaction delete, 
 * update and create other items. There are BillDAO, BusyDateDAO, UserDAO, CarDAO.
 * @author A.Mihaylcihenko
 * @see OrderDAO
 * @see Order
 *
 */
public class MySQLOrderDAO implements OrderDAO {

	/**
	 * Queries
	 */
	private final String insertOrder = "INSERT INTO `rental_car`.`orders` (`passport`, `user_id`, `car_id`, `driver`, `date_of_start`, `date_of_end`, `order_status`, `bill_id`, `order_date`, `manager_name`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final String getAllOrders =  "SELECT * FROM `rental_car`.`orders`;";
	private final String getOrderById = "SELECT * FROM `rental_car`.`orders` WHERE id=?;";
	private final String getOrdersByUserId = "SELECT * FROM `rental_car`.`orders` WHERE user_id=?;";
	private final String getOrdersByCarId = "SELECT * FROM `rental_car`.`orders` WHERE car_id=?;";
	private final String getOrdersByStatus = "SELECT * FROM `rental_car`.`orders` WHERE order_status=?;";
	private final String deleteOrder = "DELETE FROM `rental_car`.`orders` WHERE id=?;";
	private final String updateOrderStatus = "UPDATE `rental_car`.`orders` SET order_status=? WHERE id=?;";
	private final String updateOrderManager = "UPDATE `rental_car`.`orders` SET manager_name=? WHERE id=?;";
	private final String getClosedOrdersWhereManager = "SELECT COUNT(*) AS 'count' FROM rental_car.orders WHERE manager_name =? AND order_status='CONFIRMED';";
	private final String getOpenAndConfOrdersByUserId = "SELECT COUNT(*) AS 'count' FROM rental_car.orders WHERE user_id =? AND order_status='CONFIRMED' or order_status='OPENED';";
	private final String getOrdersByManagerName = "SELECT * FROM rental_car.orders WHERE manager_name=? OR manager_name='nobody';";

	/**
	 * Logger Log4j
	 */
	private static final Logger LOG = Logger.getLogger(MySQLOrderDAO.class);
	
	@Override
	public void insertOrder(Order order) throws DBException {
		LOG.info("inserting order start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(insertOrder)) {
			ps.setString(1, order.getPassport());
			ps.setInt(2, order.getUserId());
			ps.setInt(3, order.getCarId());
			ps.setInt(4, order.getDriver());
			ps.setDate(5, order.getDateOfStart());
			ps.setDate(6, order.getDateOfEnd());
			ps.setString(7, order.getOrderStatus().getName());
			ps.setInt(8, order.getBillId());
			ps.setDate(9, order.getOrderDate());
			ps.setString(10, order.getManagerName());
			ps.executeUpdate();
		
		} catch (SQLException e) {
			LOG.error(Messages.CANNOT_CREATE_ORDER);
			throw new DBException(Messages.CANNOT_CREATE_ORDER, e);
		}
		LOG.info("inserting order end");
	}
	@Override
	public void updateOrderManagerById(int id, String managerName) throws DBException {
		LOG.info("updating order manager start --> " + managerName);
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateOrderManager)){
			ps.setString(1, managerName);
			ps.setInt(2, id);
			ps.executeUpdate();
		}
		catch (SQLException e) {
				LOG.error(Messages.CANNOT_UPDATE_ORDER + " " + e);
				throw new DBException(Messages.CANNOT_UPDATE_ORDER, e);
		}
		LOG.info("updating order manager end");
	}

	@Override
	public List<Order> getAllOrders() throws DBException {
		
		List<Order> orders = new ArrayList<>();
		LOG.info("get all orders start");
		ResultSet rs = null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getAllOrders)) {

			ps.executeQuery();
			rs = ps.getResultSet();
			while(rs.next()) {
				Order order = new Order();
				order = extractOrder(rs);
				orders.add(order);
			}
		} catch (SQLException e) {
			LOG.error(Messages.CANNOT_GET_ALL_ORDERS);
			throw new DBException(Messages.CANNOT_GET_ALL_ORDERS, e);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get all orders end");
		return orders;
	}

	@Override
	public Order getOrderById(int orderId) throws DBException {
		Order order = new Order();
		LOG.info("get order by id start");
		ResultSet rs = 	null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getOrderById)) {
			
			ps.setInt(1, orderId);
			ps.execute();
			rs = ps.getResultSet();
			int count = 0;
			while (rs.next()) {
				if (count > 1) {
					throw new SQLException();
				}
				count++;
				order = extractOrder(rs);
			}	
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ORDER_BY_ID);
			throw new DBException(Messages.CANNOT_GET_ORDER_BY_ID, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get order by id end");
		return order;
	}
	
	/** Select count by */

	@Override
	public List<Order> getOrdersByUserId(int userId) throws DBException {
		List<Order> orders = new ArrayList<>();
		ResultSet rs = 	null;
		LOG.info("get order by user id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getOrdersByUserId)) {
			
			ps.setInt(1, userId);
			ps.execute();
			rs = ps.getResultSet();
			while (rs.next()) {
				Order order = new Order();
				order = extractOrder(rs);
				orders.add(order);
			}	
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ORDER_BY_USER_ID);
			throw new DBException(Messages.CANNOT_GET_ORDER_BY_USER_ID, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get order by user id end");
		return orders;
	}
	
	@Override
	public List<Order> getOrdersByCarId(int carId) throws DBException {
		List<Order> orders = new ArrayList<>();
		ResultSet rs = 	null;
		LOG.info("get order by user id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getOrdersByCarId)) {
			
			ps.setInt(1, carId);
			ps.execute();
			rs = ps.getResultSet();
			while (rs.next()) {
				Order order = new Order();
				order = extractOrder(rs);
				orders.add(order);
			}	
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ORDER_BY_CAR_ID);
			throw new DBException(Messages.CANNOT_GET_ORDER_BY_CAR_ID, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get order by user id end");
		return orders;
	}

	@Override
	public void deleteOrderById(int id) throws DBException {
		LOG.info("delete order by id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(deleteOrder)) {
			
			ps.setInt(1, id);
			ps.executeUpdate();	
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_DELETE_ORDER);
			throw new DBException(Messages.CANNOT_DELETE_ORDER, ex);
		}
		LOG.info("delete order by id start");
	}
	
	private Order extractOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setPassport(rs.getString("passport"));
		order.setUserId(rs.getInt("user_id"));
		order.setCarId(rs.getInt("car_id"));
		order.setDriver(rs.getInt("driver"));
		order.setDateOfStart(rs.getDate("date_of_start"));
		order.setDateOfEnd(rs.getDate("date_of_end"));
		order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
		order.setBillId(rs.getInt("bill_id"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setManagerName(rs.getString("manager_name"));
		return order;
		
	}

	/** Create order in transaction. 
	 * Create busy dates for car in order and bill for this order 
	 */
	@Override
	public void makeOrderWithTransaction(int userId, int carId, String passport, int driver, Date startDate, Date endDate) throws DBException {
		CarDAO car = new MySQLCarDAO();
		BillDAO bill = new MySQLBillDAO();
		BusyDateDAO dates = new MySQLBusyDateDAO();
		UserDAO user = new MySQLUserDAO();
		LOG.info("make order with transaction start");
		int lastInsertId = 0;
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		/**
		 * Start transaction
		 */
		try {			
			/**
			 * Dates inspections for busy this car
			 */
			User userEntity = user.getUserById(userId);
			System.out.println(userEntity);
			if (userEntity.getUserStatus().equals(UserStatus.BLOCK)) {
				LOG.error(Messages.USER_HAVE_STATUS_BLOCK);
				throw new SQLException(Messages.USER_HAVE_STATUS_BLOCK);
			}
			List<BusyDate> busy = new ArrayList<>();
			busy = dates.getBusyDatesByCarId(carId);
			if (!busy.isEmpty()) {
			long startDateIn = startDate.getTime();
			long endDateIn = endDate.getTime();
			
			if (endDateIn < startDateIn) {
				LOG.error(Messages.START_DATE_MUST_BE_LESS_END_DATE);
				throw new SQLException(Messages.START_DATE_MUST_BE_LESS_END_DATE);
			}
			
			for (int i = 0; i < busy.size(); i++) {
				long dateStart = busy.get(i).getStartDate().getTime();
				long dateEnd = busy.get(i).getEndDate().getTime();
				if (startDateIn >= dateStart && startDateIn <= dateEnd) {
					LOG.error(Messages.ERR_CANNOT_REGISTER_THIS_DATES);
					throw new SQLException(Messages.ERR_CANNOT_REGISTER_THIS_DATES);
				}
				else if (endDateIn >= dateStart && endDateIn <= dateEnd) {
					LOG.error(Messages.ERR_CANNOT_REGISTER_THIS_DATES);
					throw new SQLException(Messages.ERR_CANNOT_REGISTER_THIS_DATES);
				}
			}
			}
			connection = DAOFactory.getConnection();
			connection.setAutoCommit(false);
			/**
			 * Create bill and get last bill_id
			 */
			lastInsertId = bill.createBill(0, 0, connection);
			System.out.println("bill create");
			
			/**
			 * Create order
			 */
			ps = connection.prepareStatement(insertOrder);
			ps.setString(1, passport);
			ps.setInt(2, userId);
			ps.setInt(3, carId);
			ps.setInt(4, driver);
			ps.setDate(5, startDate);
			ps.setDate(6, endDate);
			ps.setString(7, OrderStatus.OPENED.getName());
			ps.setInt(8, lastInsertId);
			ps.setDate(9, new Date(System.currentTimeMillis()));
			ps.setString(10, "nobody");
			ps.executeUpdate();	
			System.out.println("order create");
			/**
			 * Update bill for rent
			 */
			Car carEntity = car.getCarById(carId);
			long time = (endDate.getTime() - startDate.getTime()) / Order.MS_IN_DAY;
			System.out.println(time);
			double cost = carEntity.getPrice() * time;
			if (driver == 1) {
				double driverCost = Order.driverCost * time;
				cost += driverCost;
			}
			bill.updateBillById(lastInsertId, cost, 0, BillStatus.NOPAID, BillStatus.PAID, connection);
			System.out.println("bill update");
			/**
			 * Create busy dates for car
			 */
			dates.makeBusyDates(carId, startDate, endDate, connection);
			System.out.println("dates create");
			connection.commit();
			System.out.println("commit");
		} catch (SQLException e) {
			try {
				connection.rollback();
				LOG.error(Messages.ROLLBACK_IN_ORDER_IN_MAKE_ORDER_METHOD);
				System.out.println(Messages.ROLLBACK_IN_ORDER_IN_MAKE_ORDER_METHOD);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			LOG.error(Messages.CANNOT_CREATE_ORDER_WITH_TRANSACTION);
			throw new DBException(Messages.CANNOT_CREATE_ORDER_WITH_TRANSACTION, e);
		}
		finally {
			try {
				if (connection != null) {
				connection.close();
				}
				if (ps != null) {
				ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("make order with transaction start");
	}


	@Override
	public void updateOrderStatusById(int id, OrderStatus status) throws DBException {
		LOG.info("update order status start");
		BusyDateDAO busyDate = new MySQLBusyDateDAO();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DAOFactory.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(updateOrderStatus);
			ps.setString(1, status.getName());
			ps.setInt(2, id);
			ps.executeUpdate();
			
			if (status.equals(OrderStatus.ABORTED) || status.equals(OrderStatus.CLOSED)) {
				Order order = new Order();
				order = getOrderById(id);
				busyDate.deleteBusyDateByCarAndDatesForTransaction(order.getCarId(), order.getDateOfStart(), order.getDateOfEnd(), connection);
			}
			connection.commit();
		}
		catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			LOG.error(Messages.CANNOT_UPDATE_ORDER_STATUS);
			throw new DBException(Messages.CANNOT_UPDATE_ORDER_STATUS, ex);
		}
		finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		LOG.info("update order status end");
	}

	@Override
	public List<Order> getOrdersByStatus(OrderStatus rentStatus) throws DBException {
		List<Order> orders = new ArrayList<>();
		ResultSet rs = 	null;
		LOG.info("get order by user id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getOrdersByStatus)) {
			
			ps.setString(1, rentStatus.getName());
			ps.execute();
			rs = ps.getResultSet();
			while (rs.next()) {
				Order order = new Order();
				order = extractOrder(rs);
				orders.add(order);
			}	
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ORDER_BY_STATUS);
			throw new DBException(Messages.CANNOT_GET_ORDER_BY_STATUS, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get order by user id end");
		return orders;
	}

	@Override
	public List<Order> getOrdersByBillRentStatus(BillStatus status) throws DBException {
		List<Order> orders = new ArrayList<>();
		BillDAO billDAO = new MySQLBillDAO();
		ResultSet rs = 	null;
		LOG.info("get order by user id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getAllOrders)) {
			
			ps.setString(1, status.getName());
			ps.execute();
			rs = ps.getResultSet();
			while (rs.next()) {
				Order order = new Order();
				order = extractOrder(rs);
				orders.add(order);
			}
			List<Order> ordersWithBillStatus = new ArrayList<>();
			for (int i = 0; i < orders.size(); i++) {
				Bill bill = new Bill();
				Order order = orders.get(i);
				bill = billDAO.getBillById(order.getBillId());
				if (bill.getRentStatus().equals(status)) {
					ordersWithBillStatus.add(order);
				}
			}
			orders = ordersWithBillStatus;
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ORDER_BY_BILL_STATUS);
			throw new DBException(Messages.CANNOT_GET_ORDER_BY_BILL_STATUS, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get order by user id end");
		return orders;
	}
	
	@Override
	public List<Order> getOrdersByBillRepairStatus(BillStatus repairStatus) throws DBException {
		List<Order> orders = new ArrayList<>();
		BillDAO billDAO = new MySQLBillDAO();
		ResultSet rs = 	null;
		LOG.info("get order by user id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getAllOrders)) {
			
			ps.setString(1, repairStatus.getName());
			ps.execute();
			rs = ps.getResultSet();
			while (rs.next()) {
				Order order = new Order();
				order = extractOrder(rs);
				orders.add(order);
			}
			List<Order> ordersWithBillStatus = new ArrayList<>();
			for (int i = 0; i < orders.size(); i++) {
				Bill bill = new Bill();
				Order order = orders.get(i);
				bill = billDAO.getBillById(order.getBillId());
				if (bill.getRepairStatus().equals(repairStatus)) {
					ordersWithBillStatus.add(order);
				}
			}
			orders = ordersWithBillStatus;
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ORDER_BY_BILL_STATUS);
			throw new DBException(Messages.CANNOT_GET_ORDER_BY_BILL_STATUS, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get order by user id end");
		return orders;
	}

	/** 
	 * Before deleting order, delete bill in this order 
	 */
	@Override
	public void deleteOrderByIdWithTransaction(int id) throws DBException {
		LOG.info("delete order by id start");
		BillDAO billDAO = new MySQLBillDAO();
		int billId = getOrderById(id).getBillId();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DAOFactory.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(deleteOrder);
			ps.setInt(1, id);
			ps.executeUpdate();	
			
			billDAO.deleteBillByIdInTransaction(billId, connection);
			connection.commit();
		}
		catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			LOG.error(Messages.CANNOT_DELETE_ORDER);
			throw new DBException(Messages.CANNOT_DELETE_ORDER, ex);
		}
		finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		LOG.info("delete order by id start");
		
	}
	@Override
	public int getOrdersConfirmedCountByManagerName(String manager)
			throws DBException {
		LOG.info("get order count by manager name start");
		ResultSet rs = 	null;
		int result = 0;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getClosedOrdersWhereManager)) {
			
			ps.setString(1, manager);
			ps.execute();
			rs = ps.getResultSet();
			rs.next();
			result = rs.getInt("count");
			
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ORDER_BY_ID);
			throw new DBException(Messages.CANNOT_GET_ORDER_BY_ID, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get order count by manager name end");
		return result;
	}
	@Override
	public int getOrdersCountByClientId(int userId) throws DBException {
		LOG.info("get orders count by user id where order status = OPENED or CONFIRMED start");
		ResultSet rs = null;
		int result = 0;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getOpenAndConfOrdersByUserId)) {
			
			ps.setInt(1, userId);
			ps.execute();
			rs = ps.getResultSet();
			rs.next();
			result = rs.getInt("count");
			
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ORDER_BY_ID);
			throw new DBException(Messages.CANNOT_GET_ORDER_BY_ID, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get order count by manager name end");
		return result;
	}
	@Override
	public List<Order> getOrdersByManagerName(String surname)
			throws DBException {
		List<Order> orders = new ArrayList<>();
		ResultSet rs = 	null;
		LOG.info("get order by manager name start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getOrdersByManagerName)) {
			
			ps.setString(1, surname);
			ps.execute();
			rs = ps.getResultSet();
			while (rs.next()) {
				Order order = new Order();
				order = extractOrder(rs);
				orders.add(order);
			}	
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ORDER_BY_USER_ID);
			throw new DBException(Messages.CANNOT_GET_ORDER_BY_USER_ID, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get order by manager name end");
		return orders;
	}
	

}
