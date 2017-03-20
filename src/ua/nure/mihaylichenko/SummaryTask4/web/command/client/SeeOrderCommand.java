package ua.nure.mihaylichenko.SummaryTask4.web.command.client;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Bill;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Order;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.BillStatus;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * SeeOrderCommand client command.
 * Open information about bills in some client order.
 * @author A.Mihaylichenko
 *
 */
public class SeeOrderCommand extends Command {

	private static final long serialVersionUID = -4065487968060759600L;
	private static final Logger LOG = Logger.getLogger(SeeOrderCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("SeeOrderCommand starts");
		
		List<String> answerBillRent = null;
		List<String> answerBillRepair = null;
		Boolean billRentPay = false;
		Boolean billRepairPay = false;
		int billId = 0;
		int carId = 0;
		int orderId = 0;
		
		
		try {
			billId = Integer.valueOf(request.getParameter("billId"));
			orderId = Integer.valueOf(request.getParameter("orderId"));
			carId = Integer.valueOf(request.getParameter("carId"));
			
			if (billId == 0 || orderId == 0 || carId == 0) {
				billId = (int) request.getAttribute("billId");
				orderId = (int) request.getAttribute("orderId");
				carId = (int) request.getAttribute("carId");
			}
			LOG.debug("bill id --> " + billId);
			LOG.debug("car id --> " + carId);
			LOG.debug("order id --> " + orderId);
			
			Car car = DAOFactory.getFactory().getCarDAO().getCarById(carId);
			Order order =  DAOFactory.getFactory().getOrderDAO().getOrderById(orderId);
			Bill bill =  DAOFactory.getFactory().getBillDAO().getBillById(billId);
			
			String carMark = car.getMark();
			String carModel = car.getModel();
			int driver = order.getDriver();
			Date startDate = order.getDateOfStart();
			Date endDate = order.getDateOfEnd();
			double billRent = bill.getRent();
			double billRepair = bill.getRepair();
			String billRentStatus = bill.getRentStatus().getName();
			String billRepairStatus = bill.getRepairStatus().getName();
			
			/**
			 * Creating answer
			 */
			answerBillRent = new ArrayList<>();
			
			long days = (endDate.getTime() - startDate.getTime()) / Order.MS_IN_DAY;
			answerBillRent.add(carMark);
			answerBillRent.add(carModel);
			
			if (driver == 1) {
				answerBillRent.add("yes");
			}
			else if (driver == 1){
				answerBillRent.add("no");
			}
			
			double driverPrice = days * 100;
			double autoPrice = billRent - driverPrice;
			answerBillRent.add(String.valueOf(days));
			answerBillRent.add(String.valueOf(driverPrice));
			answerBillRent.add(String.valueOf(autoPrice));
			answerBillRent.add(String.valueOf(billRent));
			answerBillRent.add(String.valueOf(billRentStatus));
			
			if (billRentStatus.equalsIgnoreCase(BillStatus.PAID.getName())) {
				billRentPay = true;
			}
			if (billRepairStatus.equalsIgnoreCase(BillStatus.PAID.getName())) {
				billRepairPay = true;
			}
			
			/** Creating bill repair answer if repair cost != 0 */
			if (billRepair != 0.0) {
				answerBillRepair = new ArrayList<>();
				answerBillRepair.add(carMark);
				answerBillRepair.add(carModel);
				answerBillRepair.add(String.valueOf(days));
				answerBillRepair.add(String.valueOf(billRepair));
				answerBillRepair.add(String.valueOf(billRepairStatus));
			}
			
		}
		catch (Exception ex) {
			LOG.error("Cannot see bill" + ex.getMessage());
			throw new AppException("Cannot open bill");
		}
		request.setAttribute("answerBillRepair", answerBillRepair);
		LOG.debug("Request set bill repair list --> " + answerBillRepair);
		
		request.setAttribute("answerBillRent", answerBillRent);
		LOG.debug("Request set bill rent list --> " + answerBillRent);
		
		request.setAttribute("billRentPay", billRentPay);
		LOG.debug("Request set bill rent status --> " + billRentPay);
		
		request.setAttribute("billRepairPay", billRepairPay);
		LOG.debug("Request set bill repair status --> " + billRepairPay);
		
		request.setAttribute("billId", billId);
		LOG.debug("Request set bill id --> " + billId);
		
		request.setAttribute("carId", carId);
		LOG.debug("Request set car id --> " + carId);
		
		request.setAttribute("orderId", orderId);
		LOG.debug("Request set order id --> " + orderId);
		
		return Path.PAGE_BILL_CLIENT;
	}

}
