package ua.nure.mihaylichenko.SummaryTask4.web.command.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.BillStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;
import ua.nure.mihaylichenko.SummaryTask4.web.util.ChooseLanguage;

/**
 * PayBillCommand client command.
 * By this command client paid his bills.
 * @author A.Mihaylichenko
 *
 */
public class PayBillCommand extends Command {

	private static final long serialVersionUID = -2041602902212303168L;
	private static final Logger LOG = Logger.getLogger(PayBillCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("PayBillCommand starts");
		int billId = 0;
		String billType = null;
		String answer = null;
		HttpSession session = request.getSession(false);
		
		try {
			
			billType = request.getParameter("billType");
			billId = Integer.valueOf(request.getParameter("billId"));
			
			if (billType.equals("billRent")) {
				DAOFactory.getFactory().getBillDAO().updateBillStatusForRent(billId, BillStatus.PAID);
				LOG.debug(billType + " paid, bill status is" + BillStatus.PAID);
				answer = ChooseLanguage.getValue("bill.paid", session);
			}
			else if (billType.equals("billRepair")) {
				DAOFactory.getFactory().getBillDAO().updateBillStatusForRepair(billId, BillStatus.PAID);
				LOG.debug(billType + " paid, bill status is" + BillStatus.PAID);
				answer = ChooseLanguage.getValue("bill.paid", session);
			}
			session.setAttribute("answer", answer);
		}
		catch (Exception ex) {
			LOG.error("Cannot pay bill for " + billType + " " + ex.getMessage());
			answer = ChooseLanguage.getValue("bill.nopaid", session);
			session.setAttribute("answer", answer);
			
		}
		
		return Path.COMMAND_CLIENT_ORDERS;
	}

}
