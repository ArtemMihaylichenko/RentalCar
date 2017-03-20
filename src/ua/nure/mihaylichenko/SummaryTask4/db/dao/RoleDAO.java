package ua.nure.mihaylichenko.SummaryTask4.db.dao;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.Role;

/**
 * Interface for implementation Role entity to DB
 * @author A.Mihaylichenko
 * 
 * @see Role
 * @see MySQLRoleDAO
 */
public interface RoleDAO {

	Role getRoleById(int id);

}
