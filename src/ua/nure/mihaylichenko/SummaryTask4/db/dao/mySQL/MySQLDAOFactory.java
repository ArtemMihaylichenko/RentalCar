package ua.nure.mihaylichenko.SummaryTask4.db.dao.mySQL;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.BillDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.BusyDateDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.CarDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.MessageDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.OrderDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.RoleDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.UserDAO;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/**
 * Implementation for DAOFactory.
 * Using with MySQL in abstract DAOFactory
 * @author A.Mihaylcihenko
 * @see DAOFactory
 *
 */
public class MySQLDAOFactory extends DAOFactory {

	private static MySQLDAOFactory instance;
	
	public static synchronized MySQLDAOFactory getInstance() throws DBException {
		if (instance == null) {
			instance = new MySQLDAOFactory();
		}
		return instance;
	}
	
	private MySQLDAOFactory() {
	}
	
	@Override
	public CarDAO getCarDAO() {
		return new MySQLCarDAO();
	}
	@Override
	public UserDAO getUserDAO() {
		return new MySQLUserDAO();
	}
	@Override
	public OrderDAO getOrderDAO() {
		return new MySQLOrderDAO();
	}
	@Override
	public BillDAO getBillDAO() {
		return new MySQLBillDAO();
	}
	@Override
	public BusyDateDAO getBusyDateDAO() {
		return new MySQLBusyDateDAO();
	}
	@Override
	public RoleDAO getRoleDAO() {
		return new MySQLRoleDAO();
	}

	@Override
	public MessageDAO getMessageDAO() {
		return new MySQLMessageDAO();
	}
}
