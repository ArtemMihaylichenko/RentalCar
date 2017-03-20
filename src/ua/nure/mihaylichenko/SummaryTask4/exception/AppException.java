package ua.nure.mihaylichenko.SummaryTask4.exception;

public class AppException extends Exception{
	
	
	private static final long serialVersionUID = 7643756516186875015L;
	
	public AppException() {
		super();
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

}
