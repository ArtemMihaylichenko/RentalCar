package ua.nure.mihaylichenko.SummaryTask4.web.beans;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.Bill;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Order;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
/**
 * Bean class for order list page
 * Contains classes car, bill, order, user.
 * Created from correct view orders in client pages and manager pages.
 * @author A.Mihaylichenko
 *
 */
public class OrderBean {
	
	private Car car;
	private Order order;
	private Bill bill;
	private String billStatus;
	private User user;
	
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "OrderBean [car=" + car + ", order=" + order + ", bill=" + bill
				+ ", billStatus=" + billStatus + ", user=" + user + "]";
	}
	
}
