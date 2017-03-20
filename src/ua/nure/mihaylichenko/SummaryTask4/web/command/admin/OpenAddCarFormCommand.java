package ua.nure.mihaylichenko.SummaryTask4.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * OpenAddCarFormCommand - admin command.
 * Open page with form to add new car.
 * @author A.Miahylichenko
 *
 */
public class OpenAddCarFormCommand extends Command {

	private static final long serialVersionUID = 5979768599925876842L;
	
	private static final Logger LOG = Logger.getLogger(OpenAddCarFormCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.info("Start RegistrationCommand");
		
		LOG.info("End RegistrationCommand");
		return Path.PAGE_CREATE_CAR_ADMIN;
	}

}
