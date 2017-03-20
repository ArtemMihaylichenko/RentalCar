package ua.nure.mihaylichenko.SummaryTask4.web.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.entity.Order;
import ua.nure.mihaylichenko.SummaryTask4.exception.Messages;
import ua.nure.mihaylichenko.SummaryTask4.exception.ValidateException;

/**
 * Class for validate input parameters 
 * for registration new client or new car in system. 
 * And for login, password and dates on the service.
 * @author A.Mihaylichenko
 */

public class Validator {
	
	/** Constants REGEXP */
	public static final String VALIDATE_LOGIN = "^[a-zA-Z0-9_-]{4,15}$";
	public static final String VALIDATE_MAIL = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
	public static final String VALIDATE_NAME_AND_SURNAME = "^[a-zA-Zа-яА-Я]{3,35}$";
	public static final String VALIDATE_CAR_MARK_AND_MODEL = "^[a-zA-Zа-яА-Я0-9]{2,25}$";
	public static final String VALIDATE_CAR_YEAR = "^[0-9]{4}$";
	public static final String VALIDATE_PASSWORD = "^[a-zA-Zа-яА-Я0-9_-]{4,15}$";
	public static final String VALIDATE_PASSPORT = "^[A-ZА-Я]{2}[0-9]{6}";
	
	private static final Logger LOG = Logger.getLogger(Validator.class);
	
	public static void validateLogin(String login) throws ValidateException {
		Pattern pattern = Pattern.compile(VALIDATE_LOGIN);
		Matcher matcher = pattern.matcher(login);
		if (!matcher.matches()) {
			LOG.error(Messages.INVALID_LOGIN);
			throw new ValidateException(Messages.INVALID_LOGIN, new Exception());
		}	
		LOG.info("validateLogin success");
	}
	
	public static void validatePassword(String password) throws ValidateException {
		Pattern pattern = Pattern.compile(VALIDATE_PASSWORD);
		Matcher matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			LOG.error(Messages.INVALID_PASSWORD);
			throw new ValidateException(Messages.INVALID_PASSWORD, new Exception());
		}	
		LOG.info("validatePassword success");
	}
	
	public static void validatePassport(String passport) throws ValidateException {
		Pattern pattern = Pattern.compile(VALIDATE_PASSPORT);
		Matcher matcher = pattern.matcher(passport);
		if (!matcher.matches()) {
			LOG.error(Messages.INVALID_PASSPORT);
			throw new ValidateException(Messages.INVALID_PASSPORT, new Exception());
		}	
		LOG.info("validatePassport success");
	}
	
	public static void validateMail(String mail) throws ValidateException {
		Pattern pattern = Pattern.compile(VALIDATE_MAIL);
		Matcher matcher = pattern.matcher(mail);
		if (!matcher.matches()) {
			LOG.error(Messages.INVALID_MAIL);
			throw new ValidateException(Messages.INVALID_MAIL, new Exception());
		}
		LOG.info("validateMail success");
	}
	
	public static void validateNames(String name) throws ValidateException {
		Pattern pattern = Pattern.compile(VALIDATE_NAME_AND_SURNAME);
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			LOG.error(Messages.INVALID_NAME);
			throw new ValidateException(Messages.INVALID_NAME, new Exception());
		}
		LOG.info("validateNames success");
	}
	
	public static void validateDate(String date) throws ValidateException {
		String[] dateArr = date.split("\\.");
		if (dateArr.length != 3) {
			LOG.error(Messages.INVALID_DATE);
			throw new ValidateException(Messages.INVALID_DATE, new Exception());
		}
		if (Integer.valueOf(dateArr[0]) > 31 || Integer.valueOf(dateArr[0]) < 0 ||
				Integer.valueOf(dateArr[1]) > 12 || Integer.valueOf(dateArr[1]) < 0 ||
				Integer.valueOf(dateArr[2]) > 2050 || Integer.valueOf(dateArr[2]) < 1900) {
			LOG.error(Messages.INVALID_DATE);
			throw new ValidateException(Messages.INVALID_DATE, new Exception());
		}
		LOG.info("validateDate success");
	}
	
	public static void validateUserDate(String date) throws ValidateException {
		validateDate(date);
		Date dateBirth = Parser.stringToDate(date);
		long currentDate = new Date().getTime()/(Order.MS_IN_DAY * 365);
		long age = currentDate - (dateBirth.getTime()/(Order.MS_IN_DAY *365));
		if (age < 16) {
			LOG.error(Messages.INVALID_DATE);
			throw new ValidateException(Messages.INVALID_DATE, new Exception());
		}
		LOG.info("validateUserDate success");
	}
	
	public static void validateCarMarkAndModel(String carItem) throws ValidateException {
		Pattern pattern = Pattern.compile(VALIDATE_CAR_MARK_AND_MODEL);
		Matcher matcher = pattern.matcher(carItem);
		if (!matcher.matches()) {
			LOG.error(Messages.INVALID_CARNAME);
			throw new ValidateException(Messages.INVALID_CARNAME, new Exception());
		}
		LOG.info("validateCarNames success");
	}
	
	public static void validateCarYear(String year) throws ValidateException {
		Pattern pattern = Pattern.compile(VALIDATE_CAR_YEAR);
		Matcher matcher = pattern.matcher(year);
		if (!matcher.matches()) {
			LOG.error(Messages.INVALID_CAR_YEAR);
			throw new ValidateException(Messages.INVALID_CAR_YEAR, new Exception());
		}
		LOG.info("validateCarYear success");
	}
	public static void validateCarPrice(String value) throws ValidateException {
		try {
			double d = Double.valueOf(value);
			if (d < 0 || d > 5000) {
				throw new Exception("incorrect price " + d);
			}
		}
		catch (Exception ex) {
			LOG.error(Messages.INVALID_CAR_PRICE);
			throw new ValidateException(Messages.INVALID_CAR_PRICE, new Exception());
		}
		LOG.info("validateCarYear success");
	}
	
	public static void validateCarEngine(String value) throws ValidateException {
		try {
			double d = Double.valueOf(value);
			if (d < 0.5 || d > 10.0) {
				throw new Exception("incorrect engine " + d);
			}
		}
		catch (Exception ex) {
			LOG.error(Messages.INVALID_CAR_PRICE);
			throw new ValidateException(Messages.INVALID_CAR_PRICE, new Exception());
		}
		LOG.info("validateCarYear success");
	}
}
