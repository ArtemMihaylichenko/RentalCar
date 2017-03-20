package ua.nure.mihaylichenko.SummaryTask4.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.Roles;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;

/**
 * Security filter.
 * @author A.Mihaylichenko
 *
 */
public class CommandAccessFilter implements Filter {
	
	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	private Map<Roles, List<String>> accessMap = new HashMap<Roles, List<String>>();
	private List<String> commons = new ArrayList<String>();	
	private List<String> outOfControl = new ArrayList<String>();
	
	public void destroy() {
		LOG.debug("Filter destruction starts");
		
		LOG.debug("Filter destruction finished");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		LOG.info("CommandAccessFilter starts");
		
		if (accessAllowed(servletRequest)) {
			LOG.info("CommandAccessFilter finished");
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			String errorMessage = "You do not have permission to access the requested page!";
			servletRequest.setAttribute("errorMessage", errorMessage);
			LOG.info("errorMessage: " + errorMessage);
			servletRequest.getRequestDispatcher(Path.PAGE_ERROR).forward(servletRequest, servletResponse);
		}		
	}
	
	private boolean accessAllowed(ServletRequest request) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty()) {
			return false;
		}
		
		if (outOfControl.contains(commandName)) {
			return true;
		}
		
		HttpSession session = httpRequest.getSession(false);
		if (session == null) { 
			return false;
		}
		
		Roles role = (Roles) session.getAttribute("role");
		if (role == null) {
			return false;
		}
		
		return accessMap.get(role).contains(commandName)
				|| commons.contains(commandName);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		LOG.info("CommandAccessFilter initialization starts");
		
		/**
		 * Command for roles
		 */
		accessMap.put(Roles.ADMIN, asList(filterConfig.getInitParameter("admin")));
		accessMap.put(Roles.MANAGER, asList(filterConfig.getInitParameter("manager")));
		accessMap.put(Roles.CLIENT, asList(filterConfig.getInitParameter("client")));
		LOG.info("accessMap: " + accessMap);
		
		/**
		 * Common command.
		 * Command for all roles
		 */
		commons = asList(filterConfig.getInitParameter("common"));
		LOG.info("common commands: " + commons);
		
		/**
		 * Outcontrol commands 
		 * commands for unregister users
		 */
		outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
		LOG.info("out-of-control: " + outOfControl);		
	}
	
	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;		
	}

}
