package ua.nure.mihaylichenko.SummaryTask4.db.dao.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.dao.RoleDAO;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Role;

/**
 * Implementation for RoleDAO.
 * Using with MySQL
 * @author A.Mihaylcihenko
 * @see Role
 *
 */
public class MySQLRoleDAO implements RoleDAO {

	private final String getRoleById = "SELECT * FROM `rental_car`.`roles` WHERE 'id'=?;";
	
	@Override
	public Role getRoleById(int id) {
		
		Role role = new Role();
		ResultSet rs = 	null;
			
		try (Connection connection = DAOFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getRoleById)) {
			ps.setInt(1, id);
			ps.execute();
			rs = ps.getResultSet();
			while (rs.next()) {
				role.setId(rs.getInt(1));
				role.setRole(rs.getString(2));
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return role;
	}

}
