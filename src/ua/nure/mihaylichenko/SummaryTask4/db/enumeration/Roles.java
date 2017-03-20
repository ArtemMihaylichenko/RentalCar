package ua.nure.mihaylichenko.SummaryTask4.db.enumeration;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;


public enum Roles {
	ADMIN, MANAGER, CLIENT;
	
	public static Roles getRole(User user) {
		int roleId = user.getRoleId() - 1;
		return Roles.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}

}
