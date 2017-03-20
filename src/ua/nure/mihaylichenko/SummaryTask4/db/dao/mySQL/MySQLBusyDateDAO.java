package ua.nure.mihaylichenko.SummaryTask4.db.dao.mySQL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.BusyDateDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.BusyDate;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/**
 * Implementation for BusyDateDAO.
 * Using with MySQL
 * @author A.Mihaylcihenko
 * @see BusyDateDAO
 * @see BusyDate
 *
 */

public class MySQLBusyDateDAO implements BusyDateDAO {

	/**
	 * Queries
	 */
	private final String insertBusyDate = "INSERT INTO `rental_car`.`busydates` (`car_id`, `start_date`, `end_date`)"
			+ " VALUES (?, ?, ?);";
	private final String getBusyDatesByCarId = "SELECT * FROM `rental_car`.`busydates` WHERE car_id=?;";
	private final String getBusyDatesByCarIdAndDates = "SELECT * FROM `rental_car`.`busydates` WHERE car_id=? AND start_date=? AND end_date=?;";
	private final String deleteBusyDateById = "DELETE FROM `rental_car`.`busydates` WHERE id=?;";
	private final String deleteBusyDateByCarIdAndDates = "DELETE FROM `rental_car`.`busydates` WHERE car_id=? AND start_date=? AND end_date=?;";
	
	/**
	 * Logger Log4j
	 */
	private static final Logger LOG = Logger.getLogger(MySQLBusyDateDAO.class);
	
	
	
	@Override
	public void insertBusyDate(BusyDate busy) throws DBException {
		LOG.info("inserting date start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(insertBusyDate)) {
			ps.setInt(1, busy.getCarId());
			ps.setDate(2, busy.getStartDate());
			ps.setDate(3, busy.getEndDate());
			ps.executeUpdate();
		}
		catch (SQLException ex) {
			LOG.error("exception of inserting date");
			throw new DBException("exception of inserting date", ex);
		}
		LOG.info("inserting date end");
	}

	@Override
	public List<BusyDate> getBusyDatesByCarId(int carId) throws DBException {
		List<BusyDate> busyDates = new ArrayList<>();
		LOG.info("getting dates by car id start");
		ResultSet rs = null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getBusyDatesByCarId)) {
			
			ps.setInt(1, carId);
			ps.executeQuery();
			rs = ps.getResultSet();
			if (rs != null) {
			while (rs.next()) {
				BusyDate busy = new BusyDate();
				busy.setCarId(rs.getInt("car_id"));
				busy.setId(rs.getInt("id"));
				busy.setStartDate(rs.getDate("start_date"));
				busy.setEndDate(rs.getDate("end_date"));
				busyDates.add(busy);
			}
			}
		}
		catch (SQLException ex) {
			LOG.error("exception of getting dates by car id");
			throw new DBException("exception of getting dates by car id", ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("getting dates by car id end");
		return busyDates;
	}
	
	@Override
	public BusyDate getBusyDateByCarAndDates(int carId, Date start, Date end) throws DBException {
		LOG.info("getting dates by car id start");
		BusyDate busy = new BusyDate();
		ResultSet rs = null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getBusyDatesByCarIdAndDates)) {
			
			ps.setInt(1, carId);
			ps.setDate(2, start);
			ps.setDate(3, end);
			ps.executeQuery();
			rs = ps.getResultSet();
			int count = 0;
			if (rs != null) {
			while (rs.next()) {
				if (count >= 1) {
					throw new SQLException("must be one date");
				}
				busy.setId(rs.getInt("id"));
				busy.setCarId(rs.getInt("car_id"));
				busy.setStartDate(rs.getDate("start_date"));
				busy.setEndDate(rs.getDate("end_date"));
				count++;
			}
			}
		}
		catch (SQLException ex) {
			LOG.error("exception of getting dates by car id");
			throw new DBException("exception of getting dates by car id", ex);
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
		LOG.info("getting dates by car id end");
		return busy;
	}

	@Override
	public void deleteBusyDateById(int id) throws DBException {
		LOG.info("delete dates by id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(deleteBusyDateById)) {
			
			ps.setInt(1, id);
			ps.executeUpdate();	
		}
		catch (SQLException ex) {
			LOG.error("exception of delete dates by id start");
			throw new DBException("exception of delete dates by id start", ex);
		}
		LOG.info("delete dates by id start");
	}
	
	@Override
	public void deleteBusyDateByCarAndDates(int carId, Date start, Date end) throws DBException {
		LOG.info("delete dates by id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(deleteBusyDateByCarIdAndDates)) {
			
			ps.setInt(1, carId);
			ps.setDate(2, start);
			ps.setDate(3, end);
			ps.executeUpdate();	
		}
		catch (SQLException ex) {
			LOG.error("exception of delete dates by id start");
			throw new DBException("exception of delete dates by id start", ex);
		}
		LOG.info("delete dates by id start");
	}
	
	@Override
	public void deleteBusyDateByCarAndDatesForTransaction(int carId, Date start, Date end, Connection con) throws DBException {
		LOG.info("delete dates by id start");
		PreparedStatement ps = null;
		try  {
				ps = con.prepareStatement(deleteBusyDateByCarIdAndDates);
				ps.setInt(1, carId);
				ps.setDate(2, start);
				ps.setDate(3, end);
				ps.executeUpdate();	
		}
		catch (SQLException ex) {
			LOG.error("exception of delete dates by id start");
			throw new DBException("exception of delete dates by id start", ex);
		}
		finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		LOG.info("delete dates by id start");
	}

	@Override
	public void makeBusyDates(int carId, Date startDate, Date endDate, Connection con) throws DBException {
		LOG.info("make busy dates start");
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(insertBusyDate);
			ps.setInt(1, carId);
			ps.setDate(2, startDate);
			ps.setDate(3, endDate);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			LOG.error("exception of make busy dates");
			throw new DBException("exception of make busy dates", e);
		}
		LOG.info("make busy dates end");
		
	}

}
