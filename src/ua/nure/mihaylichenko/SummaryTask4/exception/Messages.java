package ua.nure.mihaylichenko.SummaryTask4.exception;

/**
 * 
 * This class created for contains constants.
 * This constants intended for exceptions messages. 
 * @author A.Mihaylchenko
 * 
 *
 */

public class Messages {

	private Messages() {
		
	}
	
	/** Exceptions messages for CarDAO */
	public static final String CANNOT_INSERT_CAR = "Cannot insert car";
	public static final String CANNOT_GET_ALL_CARS = "Can not get cars";
	public static final String CANNOT_GET_CAR_BY_ID = "Can not get car by id";
	public static final String CANNOT_DELETE_CAR = "Can not delete car from DB";
	public static final String CANNOT_UPDATE_CAR = "Can not update car";
	
	/** Exceptions messages for UserDAO */
	public static final String CANNOT_INSERT_USER = "Cannot insert user";
	public static final String CANNOT_GET_ALL_USERS = "Can not get users";
	public static final String CANNOT_GET_USER_BY_ID = "Can not get user by id";
	public static final String CANNOT_GET_USER_BY_LOGIN_AND_PASSWORD = "Can not get user by login and password";
	public static final String CANNOT_DELETE_USER = "Can not delete user";
	public static final String CANNOT_UPDATE_USER = "Can not update user";
	public static final String USER_STATUS_BLOCKER = "The status of this user is blocked!";
	
	/** Exceptions messages for OrderDAO */
	public static final String CANNOT_INSERT_ORDER = "Cannot insert order";
	public static final String CANNOT_GET_ALL_ORDERS = "Can not get orders";
	public static final String CANNOT_GET_ORDER_BY_ID = "Can not get order by id";
	public static final String CANNOT_GET_ORDER_BY_USER_ID = "Can not get order by user id";
	public static final String CANNOT_GET_ORDER_BY_CAR_ID = "Can not get order by car id";
	public static final String CANNOT_DELETE_ORDER = "Can not delete order";
	public static final String CANNOT_UPDATE_ORDER = "Can not update order";
	public static final String ERR_CANNOT_REGISTER_THIS_DATES = "Cannot choose the car for this dates";
	public static final String START_DATE_MUST_BE_LESS_END_DATE = "Start date must be less end date";
	public static final String CANNOT_CREATE_ORDER = "Cannot create order";
	public static final String CANNOT_CREATE_ORDER_WITH_TRANSACTION = "Cannot create order with transaction";
	public static final String CANNOT_UPDATE_ORDER_STATUS = "Cannot update order status";
	public static final String CANNOT_GET_ORDER_BY_STATUS = "Cannot get order by status";
	public static final String CANNOT_GET_ORDER_BY_BILL_STATUS = "Cannot get order by bill status";
	public static final String ROLLBACK_IN_ORDER_IN_MAKE_ORDER_METHOD = "rollback in order in makeOrder method";
	public static final String USER_HAVE_STATUS_BLOCK = "this cannot have opportunity to make order";
	
	/** Exceptions messages for commands */
	public static final String EMPTY_FIELD = "Fields cannot be empty!";
	public static final String PASSWORD_DIFFERENT = "Registration is aborted, because passwors are different!";
	
	/** Validate exceptions */
	public static final String INVALID_LOGIN = "login is invalid!";
	public static final String INVALID_MAIL = "e-mail is invalid!";
	public static final String INVALID_NAME = "input name or surname is invalid!";
	public static final String INVALID_DATE = "input date is invalid!";
	public static final String INVALID_PASSWORD = "input password is invalid!";
	public static final String INVALID_PASSPORT = "input passport is invalid!";
	public static final String INVALID_CARNAME = "input mark or model is invalid!";
	public static final String INVALID_CAR_YEAR = "input year is invalid!";
	public static final String INVALID_CAR_PRICE = "input price is invalid!";
	public static final String INVALID_CAR_ENGINE = "input price is invalid!";
}
