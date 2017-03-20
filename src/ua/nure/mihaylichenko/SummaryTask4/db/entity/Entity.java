package ua.nure.mihaylichenko.SummaryTask4.db.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
	
	private static final long serialVersionUID = 7044802827626795429L;
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
