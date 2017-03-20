package ua.nure.mihaylichenko.SummaryTask4.db.dao;

import java.util.List;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.Message;
import ua.nure.mihaylichenko.SummaryTask4.exception.DBException;


/**
 * Interface for implementation Messages entity to DB
 * @author A.Mihaylichenko
 * 
 * @see Message
 * @see MySQLMessagesDAO
 */
public interface MessageDAO {
	
	void insertMessage(Message busy) throws DBException;
	
	Message getMessageById(int id) throws DBException;
	
	void deleteMessageById(int id) throws DBException;
	
	List<Message> getAllMessage() throws DBException;

}
