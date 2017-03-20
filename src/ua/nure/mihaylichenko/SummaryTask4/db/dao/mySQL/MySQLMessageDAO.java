package ua.nure.mihaylichenko.SummaryTask4.db.dao.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.MessageDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Message;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/**
 * Implementation for MessageDAO.
 * Using with MySQL
 * @author A.Mihaylcihenko
 * @see MessageDAO
 * @see Message
 */
public class MySQLMessageDAO implements MessageDAO {

	/**
	 * Queries
	 */
	private final String insertMessage = "INSERT INTO `rental_car`.`messages` (`client_id`, `message`, `subject`) VALUES (?, ?, ?);";
	private final String getMessageById = "SELECT * FROM `rental_car`.`messages` WHERE id=?;";
	private final String getAllMessages = "SELECT * FROM `rental_car`.`messages`";
	private final String deleteMessageById = "DELETE FROM `rental_car`.`messages` WHERE id=?;";
	
	private static final Logger LOG = Logger.getLogger(MySQLMessageDAO.class);

	@Override
	public void insertMessage(Message message) throws DBException {
		LOG.info("inserting message start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(insertMessage)) {
			ps.setInt(1, message.getClientId());
			ps.setString(2, message.getMessage());
			ps.setString(3, message.getSubject());
			ps.executeUpdate();
		}
		catch (SQLException ex) {
			LOG.error("exception of creating message " + ex.getMessage());
			throw new DBException("exception of creating bill", ex);
		}
		LOG.info("inserting message end");

	}

	@Override
	public Message getMessageById(int id) throws DBException {
		
		LOG.info("geting message start");
		Message message = new Message();
		ResultSet rs = null;
		
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getMessageById)) {
			
			ps.setInt(1, id);
			ps.execute();
			rs = ps.getResultSet();
			int count = 0;
			while (rs.next()) {
				if (count > 1) {
					throw new SQLException();
				}
				count++;
				message.setId(id);
				message.setClientId(rs.getInt("client_id"));
				message.setMessage(rs.getString("message"));
				message.setSubject(rs.getString("subject"));
			}
		}
		catch (SQLException ex) {
			LOG.error("exception of get message " + ex.getMessage());
			throw new DBException("exception of creating bill", ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("geting message end");	
		return message;
		
		
	}

	@Override
	public void deleteMessageById(int id) throws DBException {
		LOG.info("delete message start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(deleteMessageById)) {
			
			ps.setInt(1, id);
			ps.executeUpdate();	
		}
		catch (SQLException ex) {
			LOG.error("exception of delete message " + ex.getMessage());
			throw new DBException("exception of delete bill", ex);
		}
		LOG.info("delete message end");
		
	}

	@Override
	public List<Message> getAllMessage() throws DBException {
		List<Message> messages = new ArrayList<>();
		LOG.info("geting all messages start");
		ResultSet rs = null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getAllMessages)) {
			
			ps.executeQuery();
			rs = ps.getResultSet();
			while (rs.next()) {
				Message message = new Message();
				message.setId(rs.getInt("Id"));
				message.setClientId(rs.getInt("client_id"));
				message.setMessage(rs.getString("message"));
				message.setSubject(rs.getString("subject"));
				messages.add(message);
			}
			
		}
		catch (SQLException ex) {
			LOG.error("exception of getting bills");
			throw new DBException("exception of getting bills", ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("geting all bills end");
		return messages;
	}

}
