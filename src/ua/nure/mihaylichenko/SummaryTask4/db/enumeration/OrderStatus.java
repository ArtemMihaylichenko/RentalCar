package ua.nure.mihaylichenko.SummaryTask4.db.enumeration;

public enum OrderStatus {
	
	OPENED, CONFIRMED, ABORTED, CLOSED;
	
	public String getName() {
		return name();
	}
}
