package ua.nure.mihaylichenko.SummaryTask4.web.tag;

import java.sql.Date;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.User;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Parser;

/**
 * This class contains methods for tagOutput.tld
 * @author A.Mihaylichenko
 *
 */
public class Output {
	
	public static String userFullName(User user) {
		return user.getName() + " " + user.getSurName();
	}
	
	public static String carName(Car car) {
		return car.getMark() + " " + car.getModel();
	}
	
	public static String dateToString(Date date) {
		return Parser.dateToString(date);
	}
	
	public static String userStatusToString(User user) {
		return user.getUserStatus().getName();
		
	}

}
