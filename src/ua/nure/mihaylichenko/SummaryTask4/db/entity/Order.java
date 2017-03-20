package ua.nure.mihaylichenko.SummaryTask4.db.entity;

import java.sql.Date;

import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.OrderStatus;

/**
 * Order entity
 * @author A.Mihaylichenko
 *
 */
public class Order extends Entity {
	
	private static final long serialVersionUID = 8261941332583303782L;
	public static final long MS_IN_DAY = 86400000L;
	public static final double driverCost = 100;
	
	private int id;
	private int userId;
	private int carId;
	private String passport;
	private int driver;
	private Date dateOfStart;
	private Date dateOfEnd;
	private OrderStatus orderStatus;
	private int billId;
	private Date orderDate;
	private String managerName;
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDriver() {
		return driver;
	}
	public void setDriver(int driver) {
		this.driver = driver;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public Date getDateOfStart() {
		return dateOfStart;
	}
	public void setDateOfStart(Date dateOfStart) {
		this.dateOfStart = dateOfStart;
	}
	public Date getDateOfEnd() {
		return dateOfEnd;
	}
	public void setDateOfEnd(Date dateOfEnd) {
		this.dateOfEnd = dateOfEnd;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", carId=" + carId + ", passport=" + passport + ", driver="
				+ driver + ", dateOfStart=" + dateOfStart + ", dateOfEnd=" + dateOfEnd + ", orderStatus=" + orderStatus
				+ ", billId=" + billId + ", orderDate=" + orderDate + ", managerName=" + managerName + "]";
	}
	
}
