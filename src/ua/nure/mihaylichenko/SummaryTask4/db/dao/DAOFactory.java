package ua.nure.mihaylichenko.SummaryTask4.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.mySQL.MySQLDAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/** 
 * DAOFactory. abstract class for connection with DB.
 * @author A.Mihaylichenko
 *
 */

public abstract class DAOFactory {

	private static DataSource dataSource = getDataSource();
	private static Connection connection;

	public static DAOFactory getFactory() throws DBException {
		return MySQLDAOFactory.getInstance();
	}
	
	 private static DataSource getDataSource() {
	 try { 
		 Context initContext = new InitialContext(); 
		 Context envContext = (Context) initContext.lookup("java:/comp/env"); 
		 dataSource = (DataSource) envContext.lookup("jdbc/SummaryTask4"); 
		 } 
	 catch (NamingException e) {
			 e.printStackTrace(); 
	 } 
	 return dataSource; 
	 }
	 
	 public static Connection getConnection() { 
		 try { 
			 connection = dataSource.getConnection(); 
			 } 
		 catch (SQLException e) {
			 e.printStackTrace(); 
			 }
		 return connection; 
		 }

	public abstract CarDAO getCarDAO();

	public abstract UserDAO getUserDAO();

	public abstract OrderDAO getOrderDAO();

	public abstract BillDAO getBillDAO();

	public abstract BusyDateDAO getBusyDateDAO();

	public abstract RoleDAO getRoleDAO();
	
	public abstract MessageDAO getMessageDAO();

}
