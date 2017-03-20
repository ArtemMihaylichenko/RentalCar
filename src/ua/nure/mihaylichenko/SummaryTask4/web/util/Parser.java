package ua.nure.mihaylichenko.SummaryTask4.web.util;

import java.security.MessageDigest;
import java.sql.Date;

/**
 * Date parser. Contains methods date to string and string to date. 
 * @author A.Mihaylichenko
 *
 */

public class Parser {
	
	/**
	 * Convert date to string xx-xx-xxxx
	 * @param date xxxx-xx-xx
	 * @return string xx.xx.xxxx
	 */
	public static String dateToString(Date date) {
		String currentDate = date.toString();
		String[] dateValues = currentDate.split("-");
		String resultDate = dateValues[2] + "." + dateValues[1] + "." + dateValues[0];
		return resultDate;
	}
	
	/**
	 * Convert string format xx.xx.xxxx to date
	 * @param strDate xx.xx.xxxx
	 * @return date xxxx-xx-xx
	 */
	public static Date stringToDate(String strDate) {
		String[] dateValues = strDate.split("\\.");
		Date resultDate = Date.valueOf(dateValues[2] + "-" + dateValues[1] + "-" + dateValues[0]);
		return resultDate;
	}
	
	/**
	 * Password to MD5
	 * @param password
	 * @return
	 */
	public static String hash(String password) {
		StringBuilder result = new StringBuilder("");
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			String in = password;
			digest.update(in.getBytes("UTF-8"));
			byte[] hash = digest.digest();
			for (int i = 0; i < hash.length; i++) {
				result.append(Integer.toHexString((hash[i] & 0xff) + 0x100).substring(1));
			}
		}
		catch (Exception ex) {
				
		}
		return result.toString();
	}

}
