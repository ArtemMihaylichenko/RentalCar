package ua.nure.mihaylichenko.SummaryTask4.db.entity;

import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.BillStatus;

/**
 * Bill entity
 * @author A.Mihaylichenko
 *
 */
public class Bill extends Entity {
	
	private static final long serialVersionUID = 7084383054439484296L;
	
	private int id;
	private double rent;
	private double repair;
	private BillStatus rentStatus;
	private BillStatus repairStatus;
	

	public BillStatus getRentStatus() {
		return rentStatus;
	}

	public void setRentStatus(BillStatus rentStatus) {
		this.rentStatus = rentStatus;
	}

	public BillStatus getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(BillStatus repairStatus) {
		this.repairStatus = repairStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public double getRepair() {
		return repair;
	}

	public void setRepair(double repair) {
		this.repair = repair;
	}


}
