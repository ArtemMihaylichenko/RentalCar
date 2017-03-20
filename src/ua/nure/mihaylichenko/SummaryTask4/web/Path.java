package ua.nure.mihaylichenko.SummaryTask4.web;

/**
 * 
 * Class for links
 * @author A.Mihaylichenko
 *
 */
public final class Path {
	/**
	 * Pages client
	 */
	public static final String PAGE_LOGIN = "login.jsp";
	public static final String PAGE_ERROR = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_MAIN_CLIENT = "/WEB-INF/jsp/client/main_page_client.jsp";
	public static final String PAGE_BUSY_DATES = "/WEB-INF/jsp/client/busy_dates.jsp";
	public static final String PAGE_CLIENT_ORDERS = "/WEB-INF/jsp/client/client_orders.jsp";
	public static final String PAGE_CREATE_ORDER_FORM = "/WEB-INF/jsp/client/order_create.jsp";
	public static final String PAGE_BILL_CLIENT = "/WEB-INF/jsp/client/bill_client.jsp";
	public static final String PAGE_FEEDBACK_CLIENT = "/WEB-INF/jsp/client/feed_back_page.jsp";
	
	/**
	 * Command client
	 */
	public static final String COMMAND_MAIN_CLIENT = "controller?command=mainClient";
	public static final String COMMAND_BUSY_DATE = "controller?command=getBusyDate";
	public static final String COMMAND_ORDER_CREATE = "controller?command=createOrder";
	public static final String COMMAND_ORDER_CREATE_FORM = "controller?command=orderCreate";
	public static final String COMMAND_SEE_ORDER = "controller?command=seeOrder";
	public static final String COMMAND_CLIENT_ORDERS = "controller?command=clientOrders";
	public static final String COMMAND_CLIENT_FEEDBACK = "controller?command=feedBack";
	
	/**
	 * Pages manager
	 */
	public static final String PAGE_MAIN_MANAGER = "/WEB-INF/jsp/manager/main_page_manager.jsp";
	public static final String PAGE_ORDER_VIEW_MANAGER = "/WEB-INF/jsp/manager/order_view_page.jsp";
	public static final String PAGE_ALL_MESSAGES_MANAGER = "/WEB-INF/jsp/manager/all_messages_page.jsp";
	public static final String PAGE_CHANGE_MESSAGE_MANAGER = "/WEB-INF/jsp/manager/change_message.jsp";
	
	/**
	 * Command manager
	 */
	public static final String COMMAND_MAIN_MANAGER = "controller?command=mainManager";
	public static final String COMMAND_OPEN_ORDER_MANAGER = "controller?command=mainManager";
	public static final String COMMAND_ALL_MESSAGES_MANAGER = "controller?command=allMessages";
	public static final String COMMAND_CHANGE_ORDER_MANAGER = "controller?command=changeOrder";
	
	
	/**
	 * Command admin
	 */
	public static final String COMMAND_MAIN_ADMIN = "controller?command=mainAdmin";
	public static final String COMMAND_CARS_ADMIN = "controller?command=openCars";
	public static final String COMMAND_ADD_CAR_ADMIN = "controller?command=addCar";
	public static final String COMMAND_ALL_CARS_ADMIN = "controller?command=openCars";
	public static final String COMMAND_CHANGE_CAR_ADMIN = "controller?command=changeCar";
	public static final String COMMAND_CHANGE_USER_ADMIN = "controller?command=changeUser";
	
	/**
	 * Pages admin
	 */
	public static final String PAGE_MAIN_ADMIN = "/WEB-INF/jsp/admin/main_page_admin.jsp";
	public static final String PAGE_USER_PAGE_ADMIN = "/WEB-INF/jsp/admin/users_page.jsp";
	public static final String PAGE_ALL_CARS_ADMIN = "/WEB-INF/jsp/admin/all_cars_page.jsp";
	public static final String PAGE_EDIT_CAR_ADMIN = "/WEB-INF/jsp/admin/edit_car_page.jsp";
	public static final String PAGE_CREATE_CAR_ADMIN = "/WEB-INF/jsp/admin/create_car_page.jsp";
	public static final String PAGE_DELETE_CAR_ADMIN = "/WEB-INF/jsp/admin/delete_car.jsp";
	
	/**
	 * Common commands and pages
	 */
	public static final String COMMAND_VIEW_SETTINGS = "controller?command=viewSettings";
	public static final String COMMAND_EDIT_SETTINGS = "controller?command=editSettings";
	public static final String PAGE_SETTINGS = "/WEB-INF/jsp/settings_page.jsp";
	
	/** out control command */
	public static final String COMMAND_LOGIN = "controller?command=login";
	public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/registration_page.jsp";
	public static final String COMMAND_REGISTRATION = "controller?command=registration";
}