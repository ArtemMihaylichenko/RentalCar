package ua.nure.mihaylichenko.SummaryTask4.db.dao;

import java.sql.Connection;
import java.util.List;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.Bill;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.BillStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/**
 * Interface for implementation Bill entity to DB
 * @author A.Mihaylichenko
 * 
 * @see Bill
 * @see MySQLBillDAO
 */

public interface BillDAO {

	void insertBill(Bill bill) throws DBException;

	Bill getBillById(int id) throws DBException;

	List<Bill> getAllBills() throws DBException;

	void deleteBillById(int id) throws DBException;

	void deleteBillByIdInTransaction(int id, Connection con) throws DBException;
	
	public void updateBill(int billId, double rent, double repair, BillStatus rentStatus, BillStatus repairStatus) throws DBException;

	void updateBillStatusForRent(int id, BillStatus status) throws DBException;
	public void updateBillStatusForRepair(int id, BillStatus status) throws DBException;

	int createBill(double billForRent, double billForRepair, Connection con) throws DBException;

	void updateBillById(int billId, double billForRent, double billForRepair, BillStatus rentStatus, BillStatus repairStatus, Connection con)
			throws DBException;

}
