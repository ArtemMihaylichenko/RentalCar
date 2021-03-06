package ua.nure.mihaylichenko.SummaryTask4.web.command.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.db.dao.DAOFactory;
import ua.nure.mihaylichenko.SummaryTask4.db.entity.Car;
import ua.nure.mihaylichenko.SummaryTask4.exception.AppException;
import ua.nure.mihaylichenko.SummaryTask4.web.Path;
import ua.nure.mihaylichenko.SummaryTask4.web.command.Command;

/**
 * ClientMainCommand is a client command.
 * Show all cars, sort this cars for parameters.
 * Get this cars from DB.
 * @author A.Mihaylichenko
 *
 */
public class ClientMainCommand extends Command {

	private static final long serialVersionUID = -1762349503450264247L;
	
	private static final Logger LOG = Logger.getLogger(ClientMainCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ClientMainCommand starts");
		
		List<Car> cars = null;
		List<String> carNames = null;
		List<String> carClasses = null;
		List<String> sortValues = new ArrayList<>();
			sortValues.add("Mark");
			sortValues.add("Price");
			sortValues.add("Year");
		try {
			cars = DAOFactory.getFactory().getCarDAO().getAllCars();
			carNames = new ArrayList<>();
			carClasses = new ArrayList<>();
			
			for (int i = 0; i < cars.size(); i++) {
				if (!carNames.contains(cars.get(i).getMark())) {
					carNames.add(cars.get(i).getMark());
				}
				if (!carClasses.contains(cars.get(i).getCarClass().getName())) {
					carClasses.add(cars.get(i).getCarClass().getName());
				}
			}
		/**
		 * Choose by Mark
		 */
			String carName = request.getParameter("carName");
			if (request.getParameter("carName") != null) {
				carName = new String(request.getParameter("carName").getBytes("ISO-8859-1"), "UTF-8");
				if (carName != null && !carName.equals("All") && !carName.equals("Все")) {
					List<Car> newCars = new ArrayList<>();
					for (int i = 0; i < cars.size(); i++) {
						Car car = cars.get(i);
						if (car.getMark().equals(carName)) {
							newCars.add(car);
						}
					}
					cars = newCars;
					request.setAttribute("carName", carName);
					LOG.trace("Choose cars by Mark --> " + carName);
				}
			}
		/**
		 * Choose cars by class
		 */
			String carClass = request.getParameter("carClass");
			if (request.getParameter("carClass") != null) {
				carClass = new String(request.getParameter("carClass").getBytes("ISO-8859-1"), "UTF-8");
				if (carClass != null && !carClass.equals("All") && !carClass.equals("Все")) {
					List<Car> newCars = new ArrayList<>();
					for (int i = 0; i < cars.size(); i++) {
						Car car = cars.get(i);
						if (car.getCarClass().getName().equalsIgnoreCase(carClass)) {
							newCars.add(car);
						}
					}
					cars = newCars;
					request.setAttribute("carClass", carClass);
					LOG.trace("Choose cars by class --> " + carClass);
				} 
			}
		/**
		 * Sort cars
		 */
			String sort = request.getParameter("sort");
			Collections.sort(cars, new Comparator<Car>() {
				@Override
				public int compare(Car o1, Car o2) {
					return o1.getMark().compareTo(o2.getMark());
				}
			
			});
			if (sort != null && sort.equals("Price")) {
				Collections.sort(cars, new Comparator<Car>() {
					@Override
					public int compare(Car o1, Car o2) {
						return (int) (o1.getPrice() - o2.getPrice());
					}
				});
				request.setAttribute("sort", sort);
				LOG.trace("Sort cars by --> " + sort);
			}
			else if (sort != null && sort.equals("Year")) {
				Collections.sort(cars, new Comparator<Car>() {
					@Override
					public int compare(Car o1, Car o2) {
						return (int) (o1.getYear() - o2.getYear());
					}
				});
				request.setAttribute("sort", sort);
				LOG.trace("Sort cars by --> " + sort);
			}
		}
		catch (Exception ex) {
			LOG.error("Exception in ClientMainCommand " + ex.getMessage());
			request.setAttribute("error", "error");
		}
		request.setAttribute("cars", cars);	
		LOG.trace("Set the request attribute: cars --> " + cars);
		request.setAttribute("carNames", carNames);
		request.setAttribute("carClasses", carClasses);
		request.setAttribute("sortValues", sortValues);
		
		LOG.debug("ClientMainCommand finished");
		return Path.PAGE_MAIN_CLIENT;
	}

}
