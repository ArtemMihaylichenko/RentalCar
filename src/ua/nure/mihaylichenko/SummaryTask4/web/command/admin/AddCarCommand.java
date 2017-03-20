package ua.nure.mihaylichenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.CarClass;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.CarColor;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;
import ua.nure.mihaylichenko.SummaryTask4.web.util.ChooseLanguage;
import ua.nure.mihaylichenko.SummaryTask4.web.util.Validator;

/**
 * AddCarCommand is a admin command.
 * Command for adding new cars in DB and system. 
 * @author A.Mihaylichenko
 *
 */
public class AddCarCommand extends Command {

	private static final long serialVersionUID = -7742574038116859043L;
	private static final Logger LOG = Logger.getLogger(ChangeCarCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ChangeCarFormCommand starts");
		
		Car car = null;
		List<Car> cars = null;
		List<CarColor> carColors = null;
		List<CarClass> carClasses = null;
		String message = "";
		
		/** Error strings */
		String errMarkOrModel = "";
		String errYear = "";
		String errEngine = "";
		String errPrice = "";
		String errDuplicate = "";
		String forward = "";
		
		HttpSession session = request.getSession(false);
		
		StringBuilder answer = new StringBuilder("");
		
		try {
			carColors = new ArrayList<>();
			carColors.add(CarColor.BLACK);
			carColors.add(CarColor.BLUE);
			carColors.add(CarColor.GREEN);
			carColors.add(CarColor.RED);
			carColors.add(CarColor.WHITE);
			carColors.add(CarColor.YELLOW);
			
			carClasses = new ArrayList<>();
			carClasses.add(CarClass.ECONOM);
			carClasses.add(CarClass.STANDART);
			carClasses.add(CarClass.VIP);
			
			cars = DAOFactory.getFactory().getCarDAO().getAllCars();
			
			/** POST */
			/** 
			 * Add new car 
			 */
			if (request.getParameter("type") != null && request.getParameter("type").equals("add")) {
				
				
				String mark = request.getParameter("mark");
				String model = request.getParameter("model");
				String engine = request.getParameter("engine");
				String year = request.getParameter("year");
				String price = request.getParameter("price");
				CarColor color = CarColor.valueOf(request.getParameter("carColor"));
				CarClass classCar = CarClass.valueOf(request.getParameter("carClass"));
				
				/** Expect mark */
				if (mark != null) {
					try {
						Validator.validateCarMarkAndModel(mark);
					}
					catch (Exception ex) {
						LOG.error("invalid mark");
						errMarkOrModel = ChooseLanguage.getValue("admin.cars.error.mark", session);
						answer.append(errMarkOrModel + ". ");
					}
				}
				
				/** Expect model */
				if (model != null) {
					try {
						Validator.validateCarMarkAndModel(model);
					}
					catch (Exception ex) {
						LOG.error("invalid model ");
						errMarkOrModel = ChooseLanguage.getValue("admin.cars.error.mark", session);
						answer.append(errMarkOrModel + ". ");
					}
				}
				
				/** Expect year */
				if (year != null) {
					try {
						Validator.validateCarYear(year);
					}
					catch (Exception ex) {
						LOG.error("invalid year");
						errYear = ChooseLanguage.getValue("admin.cars.error.year", session);
						answer.append(errYear + ". ");
					}
				}
				
				/** Expect price */
				if (price != null) {
					try {
						Validator.validateCarPrice(price);
					}
					catch (Exception ex) {
						LOG.error("invalid price");
						errPrice = ChooseLanguage.getValue("admin.cars.error.price", session);
						answer.append(errPrice + ". ");
					}
				}
				
				/** Expect engine */
				if (price != null) {
					try {
						Validator.validateCarEngine(engine);
					}
					catch (Exception ex) {
						LOG.error("invalid engine");
						errEngine = ChooseLanguage.getValue("admin.cars.error.engine", session);
						answer.append(errEngine + ". ");
					}
				}
				
				/** If command has validate error forward to catch **/
				if (!errMarkOrModel.equals("") || !errYear.equals("") || 
						!errPrice.equals("") || !errEngine.equals("")) {
					throw new AppException("Invalid input parameters");
				}
				
				/** Create new car */
				car = new Car();
				car.setMark(mark);
				car.setModel(model);
				car.setYear(Integer.valueOf(year));
				car.setPrice(Double.valueOf(price));
				car.setEngine(Double.valueOf(engine));
				car.setCarClass(classCar);
				car.setColor(color);
				car.setId(car.getId());
				
				/** 
				 * Expect all cars in DB. If new car == some car from DB
				 *	no add new car.
				 */
				for (int i = 0; i < cars.size(); i++) {
					Car carDB = cars.get(i);
					if (car.equals(carDB)) {
						errDuplicate = ChooseLanguage.getValue("admin.cars.error.duplicate", session);
						answer.append(errDuplicate + ". ");
						throw new AppException("duplicate car");
					}
				}
				
				/** 
				 * If collection cars no has equals car. Add new car in DB.
				 */
					DAOFactory.getFactory().getCarDAO().insertNewCar(car);
					message = "success";
					session.setAttribute("message", message);
					forward = Path.COMMAND_ADD_CAR_ADMIN;
			}
			/** GET */
			else {
				String mes = (String) session.getAttribute("message");
				
				request.setAttribute("message", mes);
				session.removeAttribute("message");
				session.removeAttribute("answer");
				
				forward = Path.PAGE_CREATE_CAR_ADMIN;
			}
			
		}
		catch (Exception ex) {
			LOG.error("Exception in ChangeCarFormCommand " + ex.getMessage());
			message = "error";
			session.setAttribute("message", message);
			session.setAttribute("answer", answer.toString());
			forward = Path.COMMAND_ADD_CAR_ADMIN;
		}
		
		session.setAttribute("carColors", carColors);
		session.setAttribute("carClasses", carClasses);
		
		LOG.debug("ChangeCarFormCommand end");
		
		return forward;
	}

}