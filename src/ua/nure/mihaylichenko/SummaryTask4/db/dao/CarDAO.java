package ua.nure.mihaylichenko.SummaryTask4.db.dao;

import java.util.List;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/**
 * Interface for implementation Car entity to DB
 * @author A.Mihaylichenko
 * 
 * @see Car
 * @see MySQLCarDAO
 */
public interface CarDAO {

	void insertNewCar(Car car) throws DBException;

	List<Car> getAllCars() throws DBException;

	Car getCarById(int id) throws DBException;

	void updateCar(Car car) throws DBException;

	void updateCarById(int id, Car car) throws DBException;

	void deleteCarById(int id) throws DBException;
}
