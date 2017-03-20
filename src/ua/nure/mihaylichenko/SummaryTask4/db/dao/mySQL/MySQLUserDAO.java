package ua.nure.mihaylichenko.SummaryTask4.db.dao.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.UserDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.Language;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.UserStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;
import ua.nure.mihaylichenko.SummaryTask4.exception.Messages;

/**
 * Implementation for UserDAO.
 * Using with MySQL
 * @author A.Mihaylcihenko
 * @see UserDAO
 * @see User
 *
 */
public class MySQLUserDAO implements UserDAO {

	/**
	 * Queries
	 */
	private final String insertNewUser = "INSERT INTO `rental_car`.`users` (`login`, `password`, `name`, `surname`, `birthday`, `mail`, `language`, `user_status`, `role_id`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final String getUserById = "SELECT * FROM `rental_car`.`users` WHERE id=?;";
	private final String getAllUsers = "SELECT * FROM `rental_car`.`users`;";
	private final String getUserByLoginAndPassword = "SELECT * FROM `rental_car`.`users` WHERE login=? AND password=?;";
	private final String updateUser = "UPDATE `rental_car`.`users` SET login=?, password=?, name=?, surname=?,"
			+ "birthday=?, mail=?, language=?, user_status=?, role_id=? WHERE id=?;";
	private final String deleteUser = "DELETE FROM `rental_car`.`users` WHERE `id`=?;";
	
	/**
	 * Logger Log4j
	 */
	private static final Logger LOG = Logger.getLogger(MySQLUserDAO.class);
	
	@Override
	public void insertUser(User user) throws DBException {
		LOG.info("inserting user start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(insertNewUser)) {
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getSurName());
			ps.setDate(5, user.getBirthDay());
			ps.setString(6, user.getMail());
			ps.setString(7, user.getLanguage().getName());
			ps.setString(8, user.getUserStatus().getName());
			ps.setInt(9, user.getRoleId());
			ps.executeUpdate();
		}
		
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_INSERT_USER);
			throw new DBException(Messages.CANNOT_INSERT_USER, ex);
		}
		
		LOG.info("inserting user end");
	}

	@Override
	public User getUserById(int userId) throws DBException {
		
		User user = new User();
		LOG.info("getting user by id start");
		ResultSet rs = 	null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getUserById)) {

			ps.setInt(1, userId);
			ps.executeQuery();
			rs = ps.getResultSet();
			int count = 0;
			while (rs.next()) {
				if (count > 1) {
					throw new SQLException("must be one");
				}
				count++;
				user = extractUser(rs);
			}
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_USER_BY_ID);
			throw new DBException(Messages.CANNOT_GET_USER_BY_ID, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("getting user by id end");
		return user;
	}

	@Override
	public List<User> getAllUsers() throws DBException {
		List<User> users = new ArrayList<>();
		LOG.info("getting all users by id start");
		ResultSet rs = null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getAllUsers)) {

			ps.executeQuery();
			rs = ps.getResultSet();
			while (rs.next()) {
				User user = new User();
				user = extractUser(rs);
				users.add(user);
			}
		}
		
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ALL_USERS);
			throw new DBException(Messages.CANNOT_GET_ALL_USERS, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("getting all users by id end");
		return users;
	}

	@Override
	public User getUserByLoginAndPassword(String login, String password) throws DBException {
		User user = new User();
		LOG.info("getting user by login and password start");
		ResultSet rs = null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getUserByLoginAndPassword)) {

			ps.setString(1, login);
			ps.setString(2, password);
			ps.execute();
			rs = ps.getResultSet();
			int count = 0;
			while (rs.next()) {
				if (count > 0) {
					throw new SQLException();
				}
				count++;
				user = extractUser(rs);
			}
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_USER_BY_LOGIN_AND_PASSWORD);
			throw new DBException(Messages.CANNOT_GET_USER_BY_LOGIN_AND_PASSWORD, ex);
		}
		finally {
			try {
				if (rs != null) {
				rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("getting user by login and password end");	
		return user;
	}

	@Override
	public void deleteUserById(int id) throws DBException {
		LOG.info("delete user by id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(deleteUser)) {
			
			ps.setInt(1, id);
			ps.executeUpdate();	
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_DELETE_USER);
			throw new DBException(Messages.CANNOT_DELETE_USER, ex);
		}
		LOG.info("delete user by id start");
	}
	
	@Override
	public void updateUser(User user) throws DBException {
		LOG.info("update user by id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateUser)) {
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getSurName());
			ps.setDate(5, user.getBirthDay());
			ps.setString(6, user.getMail());
			ps.setString(7, user.getLanguage().getName());
			ps.setString(8, user.getUserStatus().getName());
			ps.setInt(9, user.getRoleId());
			ps.setInt(10, user.getId());
			ps.executeUpdate();
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_UPDATE_USER + " " + ex.getMessage());
			throw new DBException(Messages.CANNOT_UPDATE_USER, ex);
		}
		LOG.info("update user by id start");
		
	}
	
	@Override
	public void updateUserById(int id, User user) throws DBException {
		LOG.info("update user by id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateUser)) {
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getSurName());
			ps.setDate(5, user.getBirthDay());
			ps.setString(6, user.getMail());
			ps.setString(7, user.getLanguage().getName());
			ps.setString(8, user.getUserStatus().getName());
			ps.setInt(9, user.getRoleId());
			ps.setInt(10, id);
			ps.executeUpdate();
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_UPDATE_USER + " " + ex.getMessage());
			throw new DBException(Messages.CANNOT_UPDATE_USER, ex);
		}
		LOG.info("update user by id end");
		
	}
	
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setPassword(rs.getString("password"));
		user.setName(rs.getString("name"));
		user.setSurName(rs.getString("surname"));
		user.setBirthDay(rs.getDate("birthday"));
		user.setMail(rs.getString("mail"));
		user.setLanguage(Language.valueOf(rs.getString("language")));
		user.setUserStatus(UserStatus.valueOf(rs.getString("user_status")));
		user.setRoleId(rs.getInt("role_id"));
		return user;
	}


}
