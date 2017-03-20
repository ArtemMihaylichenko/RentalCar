package ua.nure.mihaylichenko.SummaryTask4.db.entity;

import java.sql.Date;

/**
 * BusyDate entity
 * @author A.Mihaylichenko
 *
 */
public class BusyDate extends Entity {

	private static final long serialVersionUID = -2813744151343519687L;
	
	private int id;
	private int carId;
	private Date startDate;
	private Date endDate;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "BusyCar [id=" + id + ", carId=" + carId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}	

}
