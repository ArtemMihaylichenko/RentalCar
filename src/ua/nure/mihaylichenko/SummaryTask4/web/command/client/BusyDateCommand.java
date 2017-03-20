package ua.nure.mihaylichenko.SummaryTask4.web.command.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.BusyDate;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * BusyDateCommand is client command.
 * Show busy dates for some car.
 * @author A.Mihaylichenko
 *
 */
public class BusyDateCommand extends Command {

	private static final long serialVersionUID = -3559893334642468673L;
	
	private static final Logger LOG = Logger.getLogger(BusyDateCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("BusyDateCommand starts");
		
		int carId = Integer.valueOf(request.getParameter("carId"));
		
		List<BusyDate> dates = DAOFactory.getFactory().getBusyDateDAO().getBusyDatesByCarId(carId);
		LOG.trace("Get from BD list of busy dates for car --> " + dates);
		
		request.setAttribute("dates", dates);	
		LOG.trace("Set the request attribute: dates --> " + dates);
		
		LOG.debug("BusyDateCommand finished");
		return Path.PAGE_BUSY_DATES;
	}

}
