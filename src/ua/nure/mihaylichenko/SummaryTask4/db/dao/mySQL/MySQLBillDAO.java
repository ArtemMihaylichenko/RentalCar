package ua.nure.mihaylichenko.SummaryTask4.db.dao.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.BillDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Bill;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.BillStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/**
 * Implementation for BillDAO.
 * Using with MySQL
 * @author A.Mihaylcihenko
 * @see BillDAO
 * @see Bill
 */
public class MySQLBillDAO implements BillDAO {
	
	/**
	 * Queries
	 */
	private final String insertBill = "INSERT INTO `rental_car`.`bills` (`rent`, `repair`, `rent_status`, `repair_status`) VALUES (?, ?, ?, ?);";
	private final String getAllBills =  "SELECT * FROM `rental_car`.`bills`;";
	private final String getBillById = "SELECT * FROM `rental_car`.`bills` WHERE id=?;";
	private final String deleteBill = "DELETE FROM `rental_car`.`bills` WHERE id=?;";
	private final String updateBillById = "UPDATE `rental_car`.`bills` SET rent=?, repair=?, rent_status=?, repair_status=? WHERE id=?;";
	private final String lastId = "SELECT last_insert_id()";
	private final String updateBillStatusForRentById = "UPDATE `rental_car`.`bills` SET rent_status=? WHERE id=?;";
	private final String updateBillStatusForRepairById = "UPDATE `rental_car`.`bills` SET repair_status=? WHERE id=?;";

	
	/**
	 * Logger Log4j
	 */
	private static final Logger LOG = Logger.getLogger(MySQLBillDAO.class);
	
	@Override
	public void insertBill(Bill bill) throws DBException {
		
		LOG.info("inserting bill start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(insertBill)) {
			ps.setDouble(1, bill.getRent());
			ps.setDouble(2, bill.getRepair());
			ps.setString(3, bill.getRentStatus().getName());
			ps.setString(3, bill.getRepairStatus().getName());
			ps.executeUpdate();
		}
		catch (SQLException ex) {
			LOG.error("exception of creating bill");
			throw new DBException("exception of creating bill", ex);
		}
		LOG.info("inserting bill end");

	}

	@Override
	public Bill getBillById(int id) throws DBException {
		LOG.info("geting bill start");
		Bill bill = new Bill();
		ResultSet rs = null;
		
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getBillById)) {
			
			ps.setInt(1, id);
			ps.execute();
			rs = ps.getResultSet();
			int count = 0;
			while (rs.next()) {
				if (count > 1) {
					throw new SQLException();
				}
				count++;
				bill.setId(id);
				bill.setRent(rs.getInt("rent"));
				bill.setRepair(rs.getInt("repair"));
				bill.setRentStatus(BillStatus.valueOf(rs.getString("rent_status")));
				bill.setRepairStatus(BillStatus.valueOf(rs.getString("repair_status")));
			}
		}
		catch (SQLException ex) {
			LOG.error("exception of creating bill");
			throw new DBException("exception of creating bill", ex);
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("geting bill end");	
		return bill;
	}

	@Override
	public List<Bill> getAllBills() throws DBException {
		List<Bill> bills = new ArrayList<>();
		LOG.info("geting all bills start");
		ResultSet rs = null;
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getAllBills)) {
			
			ps.executeQuery();
			rs = ps.getResultSet();
			while (rs.next()) {
				Bill bill = new Bill();
				bill.setId(rs.getInt("Id"));
				bill.setRent(rs.getDouble("rent"));
				bill.setRepair(rs.getDouble("repair"));
				bill.setRentStatus(BillStatus.valueOf(rs.getString("rent_status")));
				bill.setRepairStatus(BillStatus.valueOf(rs.getString("repair_status")));
				bills.add(bill);
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
		return bills;
	}
	
	
	@Override
	public void deleteBillById(int id) throws DBException {
		LOG.info("delete bill start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(deleteBill)) {
			
			ps.setInt(1, id);
			ps.executeUpdate();	
		}
		catch (SQLException ex) {
			LOG.error("exception of delete bill");
			throw new DBException("exception of delete bill", ex);
		}
		LOG.info("delete bill end");

	}
	
	@Override
	public void updateBillStatusForRent(int id, BillStatus status) throws DBException {
		LOG.info("update bill status start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateBillStatusForRentById)) {
			ps.setString(1, status.getName());
			ps.setInt(2, id);
			ps.executeUpdate();
		}
		
		catch (SQLException ex) {
			LOG.error("exception of update bill status");
			throw new DBException("exception of update bill status", ex);
		}
		LOG.info("update bill status end");
	}
	
	@Override
	public void updateBillStatusForRepair(int id, BillStatus status) throws DBException {
		LOG.info("update bill status start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateBillStatusForRepairById)) {
			ps.setString(1, status.getName());
			ps.setInt(2, id);
			ps.executeUpdate();
		}
		
		catch (SQLException ex) {
			LOG.error("exception of update bill status");
			throw new DBException("exception of update bill status", ex);
		}
		LOG.info("update bill status end");
	}

	@Override
	public int createBill(double rent, double repair, Connection con) throws DBException {
		LOG.info("creating bill in transaction start");
		int lastInsId = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(insertBill);
			ps.setDouble(1, rent);
			ps.setDouble(2, repair);
			ps.setString(3, BillStatus.NOPAID.getName());
			ps.setString(4, BillStatus.PAID.getName());
			ps.executeUpdate();
			rs = ps.executeQuery(lastId);
			while (rs.next()) {
				lastInsId = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			LOG.error("exception of creating bill in transaction");
			throw new DBException("exception of creating bill in transaction", e);
		}
		finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("creating bill in transaction end");
		return lastInsId;
	}

	@Override
	public void updateBill(int billId, double rent, double repair, BillStatus rentStatus, BillStatus repairStatus) throws DBException {
		LOG.info("updating bill start");
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateBillById)) {
			ps.setDouble(1, rent);
			ps.setDouble(2, repair);
			ps.setString(3, rentStatus.getName());
			ps.setString(4, repairStatus.getName());
			ps.setInt(5, billId);
			ps.executeUpdate();
		} catch (SQLException e) {
			LOG.error("exception of updating bill " + e.getMessage());
			throw new DBException("exception of updating bill ", e);
		}
		LOG.info("updating bill end");
	}
	
	@Override
	public void updateBillById(int billId, double rent, double repair, BillStatus rentStatus, BillStatus repairStatus, Connection con) throws DBException {
		LOG.info("updating bill in transaction start");
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(updateBillById);
			ps.setDouble(1, rent);
			ps.setDouble(2, repair);
			ps.setString(3, rentStatus.getName());
			ps.setString(4, repairStatus.getName());
			ps.setInt(5, billId);
			ps.executeUpdate();
		} catch (SQLException e) {
			LOG.error("exception of updating bill in transaction");
			throw new DBException("exception of updating bill in transaction", e);
		}
		finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOG.info("updating bill in transaction end");
	}

	@Override
	public void deleteBillByIdInTransaction(int id, Connection con) throws DBException {
		LOG.info("delete bill start");
		PreparedStatement ps = null;
		try  {
			ps = con.prepareStatement(deleteBill);
			ps.setInt(1, id);
			ps.executeUpdate();	
		}
		catch (SQLException ex) {
			LOG.error("exception of delete bill");
			throw new DBException("exception of delete bill", ex);
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
		LOG.info("delete bill end");
	}

}
