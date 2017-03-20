package ua.nure.mihaylichenko.SummaryTask4.web.command;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;

/**
 * Main interface for the Command pattern implementation.
 * @author A.Mihaylichenko
 *
 */
public abstract class Command implements Serializable {

	private static final long serialVersionUID = 6868514670667542840L;
	
	/** Main method for the commands */
	public abstract String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}

}
