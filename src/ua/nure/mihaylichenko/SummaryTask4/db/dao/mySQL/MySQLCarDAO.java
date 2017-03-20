package ua.nure.mihaylichenko.SummaryTask4.db.dao.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.CarDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.CarClass;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.CarColor;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;
import ua.nure.mihaylichenko.SummaryTask4.exception.Messages;

/**
 * Implementation for CarDAO.
 * Using with MySQL
 * @author A.Mihaylcihenko
 * @see CarDAO
 * @see Car
 *
 */
public class MySQLCarDAO implements CarDAO {
	
	/**
	 * Queries
	 */
	private final String insertCar = "INSERT INTO `rental_car`.`cars` (`mark`, `model`, `engine`, `color`, `year`, `class`, `price`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
	private final String getAllCars =  "SELECT * FROM `rental_car`.`cars`;";
	private final String getCarById = "SELECT * FROM `rental_car`.`cars` WHERE id=?;";
	private final String updateCarById = "UPDATE `rental_car`.`cars` SET mark=?, model=?, engine=?, "
			+ "color=?, year=?, class=?, price=? WHERE id=?;";
	private final String deleteCar = "DELETE FROM `rental_car`.`cars` WHERE `id`=?;";
	
	/**
	 * Logger Log4j
	 */
	private static final Logger LOG = Logger.getLogger(MySQLCarDAO.class);
	

	@Override
	public void insertNewCar(Car car) throws DBException {
		LOG.info("inserting car start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(insertCar)) {
			ps.setString(1, car.getMark());
			ps.setString(2, car.getModel());
			ps.setDouble(3, car.getEngine());
			ps.setString(4, car.getColor().getName());
			ps.setInt(5, car.getYear());
			ps.setString(6, car.getCarClass().getName());
			ps.setDouble(7, car.getPrice());
			ps.executeUpdate();
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_INSERT_CAR);
			throw new DBException(Messages.CANNOT_INSERT_CAR, ex);
		}
		LOG.info("inserting car end");

	}

	@Override
	public List<Car> getAllCars() throws DBException {
		List<Car> cars = new ArrayList<>();
		LOG.info("get all cars start");
		ResultSet rs = null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getAllCars)) {

			ps.executeQuery();
			rs = ps.getResultSet();
			while (rs.next()) {
				Car car = new Car();
				car = extractCar(rs);
				cars.add(car);
			}
			
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_ALL_CARS);
			throw new DBException(Messages.CANNOT_GET_ALL_CARS, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get all cars end");
		return cars;
	}

	@Override
	public Car getCarById(int id) throws DBException {
		
		Car car = new Car();
		LOG.info("get car by id start");
		ResultSet rs = null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getCarById)) {
			
			ps.setInt(1, id);
			ps.execute();
			rs = ps.getResultSet();
			int count = 0;
			while (rs.next()) {
				if (count > 1) {
					throw new SQLException();
				}
				count++;
				car = extractCar(rs);
			}	
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_GET_CAR_BY_ID);
			throw new DBException(Messages.CANNOT_GET_CAR_BY_ID, ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("get car by id end");
		return car;
	}


	@Override
	public void deleteCarById(int id) throws DBException {
		LOG.info("delete car by id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(deleteCar)) {
			
			ps.setInt(1, id);
			ps.executeUpdate();	
		}
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_DELETE_CAR);
			throw new DBException(Messages.CANNOT_DELETE_CAR, ex);
		}
		LOG.info("delete car by id start");

	}

	@Override
	public void updateCar(Car car) throws DBException {
		LOG.info("update car by id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateCarById)) {
			ps.setString(1, car.getMark());
			ps.setString(2, car.getModel());
			ps.setDouble(3, car.getEngine());
			ps.setString(4, car.getColor().getName());
			ps.setInt(5, car.getYear());
			ps.setString(6, car.getCarClass().getName());
			ps.setDouble(7, car.getPrice());
			ps.setInt(8, car.getId());
			ps.executeUpdate();
		}
		
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_UPDATE_CAR);
			throw new DBException(Messages.CANNOT_UPDATE_CAR, ex);
		}
		LOG.info("update car by id start");
	}
	
	@Override
	public void updateCarById(int id, Car car) throws DBException {
		LOG.info("update car by id start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateCarById)) {
			ps.setString(1, car.getMark());
			ps.setString(2, car.getModel());
			ps.setDouble(3, car.getEngine());
			ps.setString(4, car.getColor().getName());
			ps.setInt(5, car.getYear());
			ps.setString(6, car.getCarClass().getName());
			ps.setDouble(7, car.getPrice());
			ps.setInt(8, id);
			ps.executeUpdate();
		}
		
		catch (SQLException ex) {
			LOG.error(Messages.CANNOT_UPDATE_CAR);
			throw new DBException(Messages.CANNOT_UPDATE_CAR, ex);
		}
		LOG.info("update car by id end");
	}
	
	private Car extractCar(ResultSet rs) throws SQLException {
		Car car = new Car();
		car.setId(rs.getInt("id"));
		car.setMark(rs.getString("mark"));
		car.setModel(rs.getString("model"));
		car.setEngine(rs.getDouble("engine"));
		car.setColor(CarColor.valueOf(rs.getString("color")));
		car.setYear(rs.getInt("year"));
		car.setCarClass(CarClass.valueOf(rs.getString("class")));
		car.setPrice(rs.getDouble("price"));
		return car;
	}

}
