package ua.nure.mihaylichenko.SummaryTask4.web.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;

/**
 * Class with static methods for search items.
 * @author A.Mihaylcihenko
 *
 */
public class Search {
	
	/**
	 * This method for search users in collection.
	 * by parameters login or name or surname or date.
	 * 
	 * @param users
	 * @param searchBy : parameter for search
	 * @param search  : value for search
	 * @return users 
	 */
	public static List<User> searchUsers(List<User> users, String searchBy, String search) {
		
		List<User> newUsers = new ArrayList<>();
		
		/** search users by name */
		if (searchBy.equals("name")) {
			
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				if (user.getName().equals(search)) {
					newUsers.add(user);
				}
			}
		}
		
		/** search by surname */
		else if (searchBy.equals("surname")) {
			
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				if (user.getSurName().equals(search)) {
					newUsers.add(user);
				}
			}
		}
		
		/** search by login */
		else if (searchBy.equals("login")) {
			
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				if (user.getLogin().equals(search)) {
					newUsers.add(user);
					break;
				}
			}
		}
		
		/** search by date */
		else if (searchBy.equals("date")) {
			
			String date = Parser.dateToString(Date.valueOf(search));
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				String userBirth = Parser.dateToString(user.getBirthDay());
				if (userBirth.equals(date)) {
					newUsers.add(user);
				}
			}
		}
		
		return newUsers;
		
	}

}
