package ua.nure.mihaylichenko.SummaryTask4.db.entity;


/**
 * Role entity
 * @author A.Mihaylichenko
 *
 */
public class Role extends Entity {

	private static final long serialVersionUID = 6637069663737623643L;
	
	private int id;
	private String role;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}
}
