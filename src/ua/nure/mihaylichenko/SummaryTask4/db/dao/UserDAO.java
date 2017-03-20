package ua.nure.mihaylichenko.SummaryTask4.db.dao;

import java.util.List;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;

/**
 * Interface for implementation user entity to DB
 * @author A.Mihaylichenko
 *
 * @see User
 * @see MySQLUserDAO
 */

public interface UserDAO {

	void insertUser(User user) throws DBException;

	User getUserById(int userId) throws DBException;

	List<User> getAllUsers() throws DBException;

	User getUserByLoginAndPassword(String login, String password) throws DBException;

	void updateUser(User user) throws DBException;

	void updateUserById(int id, User user) throws DBException;

	void deleteUserById(int id) throws DBException;
}
