package ua.nure.mihaylichenko.SummaryTask4.db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.BusyDate;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/**
 * Interface for implementation BusyCar entity to DB
 * @author A.Mihaylichenko
 * 
 * @see BusyDate
 * @see MySQLBusyDateDAO
 */
public interface BusyDateDAO {

	void insertBusyDate(BusyDate busy) throws DBException;

	List<BusyDate> getBusyDatesByCarId(int carId) throws DBException;

	public BusyDate getBusyDateByCarAndDates(int carId, Date start, Date end) throws DBException;

	void deleteBusyDateById(int id) throws DBException;

	public void deleteBusyDateByCarAndDates(int carId, Date start, Date end) throws DBException;

	public void deleteBusyDateByCarAndDatesForTransaction(int carId, Date start, Date end, Connection con)
			throws DBException;

	void makeBusyDates(int carId, Date startDate, Date endDate, Connection con) throws DBException;

}
