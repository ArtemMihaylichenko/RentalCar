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
 * ChangeCarCommand is a admin command.
 * This command make changes for some car.
 * @author A.Miahylichenko
 *
 */
public class ChangeCarCommand extends Command {

	private static final long serialVersionUID = 7310468531016140162L;
	
	private static final Logger LOG = Logger.getLogger(ChangeCarCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ChangeCarFormCommand starts");
		
		Car car = null;
		List<CarColor> carColors = null;
		List<CarClass> carClasses = null;
		String message = "";
		HttpSession session = request.getSession(false);
		
		/** Error strings */
		String errMarkOrModel = "";
		String errYear = "";
		String errEngine = "";
		String errPrice = "";
		
		String forward = "";
		
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
			
			car = DAOFactory.getFactory().getCarDAO().
					getCarById(Integer.valueOf(request.getParameter("carId")));
			
			/** POST */
			/** 
			 * Change old car 
			 */
			if (request.getParameter("type") != null && request.getParameter("type").equals("edit")) {
				
				
				String mark = request.getParameter("mark");
				String model = request.getParameter("model");
				String engine = request.getParameter("engine");
				String year = request.getParameter("year");
				String price = request.getParameter("price");
				CarColor color = CarColor.valueOf(request.getParameter("carColor"));
				CarClass classCar = CarClass.valueOf(request.getParameter("carClass"));
				
				/** Expect mark */
				if (mark != null && !car.getMark().equals("mark")) {
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
				if (model != null && !car.getModel().equals("model")) {
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
				Car carn = new Car();
				carn.setMark(mark);
				carn.setModel(model);
				carn.setYear(Integer.valueOf(year));
				carn.setPrice(Double.valueOf(price));
				carn.setEngine(Double.valueOf(engine));
				carn.setCarClass(classCar);
				carn.setColor(color);
				carn.setId(car.getId());
				
				if (!car.equals(carn)) { 
					car = carn;
					DAOFactory.getFactory().getCarDAO().updateCarById(car.getId(), car);
					message = "success";
					session.setAttribute("message", message);
				}
				
				forward = Path.COMMAND_CARS_ADMIN;
				
			}
			
			/** GET */
			else {
				forward = Path.PAGE_EDIT_CAR_ADMIN;
			}
			
		}
		catch (Exception ex) {
			LOG.error("Exception in ChangeCarFormCommand " + ex.getMessage());
			message = "error";
			String answ = String.valueOf(answer);
			session.setAttribute("answer", answ);
			session.setAttribute("message", message);
			forward = Path.COMMAND_CARS_ADMIN;
		}
		request.setAttribute("carColors", carColors);
		LOG.debug("request set attribute carColors --> " + carColors);
		
		request.setAttribute("carColor", car.getColor().getName());
		LOG.debug("request set attribute carColor --> " + car.getColor().getName());
		
		request.setAttribute("carClasses", carClasses);
		LOG.debug("request set attribute carClasses --> " + carClasses);
		
		request.setAttribute("carClass", car.getCarClass().getName());
		LOG.debug("request set attribute carClass --> " + car.getCarClass().getName());
		
		request.setAttribute("car", car);
		LOG.debug("request set attribute car --> " + car);
		
		LOG.debug("ChangeCarFormCommand end");
		
		return forward;
	}

}
