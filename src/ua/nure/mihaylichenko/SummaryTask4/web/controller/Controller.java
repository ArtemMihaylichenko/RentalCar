package ua.nure.mihaylichenko.SummaryTask4.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;
import ua.nure.mihaylichenko.SummaryTask4.web.command.CommandContainer;

/**
 * Main servlet in app. Work with command container.
 * @author A.Mihaylichenko
 * @see CommandContainer
 *
 */
public class Controller  extends HttpServlet{

	private static final long serialVersionUID = 9120073577021868786L;
	
private static final Logger LOG = Logger.getLogger(Controller.class);
	
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.info("doGET start");
		process(request, response, "GET");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.info("doPost start");
		process(request, response, "POST");
	}
	
	/**
	 * Main method in the application
	 * Take command from request, initialize this command throw CommandContainer.
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void process(HttpServletRequest request,
			HttpServletResponse response, String type) throws IOException, ServletException {
		LOG.debug("Controller starts");
		
		String commandName = request.getParameter("command");
		LOG.trace("Request command --> " + commandName);
		
		Command command = CommandContainer.get(commandName);
		LOG.trace("Obtained command --> " + command);
		
		String path = Path.PAGE_ERROR;
		RequestDispatcher dispatcher = null;
		try {
			path = command.execute(request, response);
			if (path == null) {
				LOG.info ("forward to --> " + path);
				LOG.info ("Controller proccess end");
				dispatcher = request.getRequestDispatcher(Path.PAGE_ERROR);
				dispatcher.forward(request, response);
			} else if (type.equals("GET")) {
				LOG.info ("Forward to --> " + path);
				LOG.info ("Controller proccess and");
				dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			} else if (type.equals("POST")) {
				LOG.info ("Redirect to --> " + path);
				LOG.info ("Controller proccess end");
				response.sendRedirect(path);
			}	
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			request.getRequestDispatcher(Path.PAGE_ERROR).forward(request, response);
		}
		
	}

}