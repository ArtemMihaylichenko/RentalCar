package ua.nure.mihaylichenko.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ua.nure.mihaylichenko.SummaryTask4.web.command.admin.AddCarCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.admin.AdminMainCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.admin.ChangeCarCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.admin.ChangeUserCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.admin.OpenAddCarFormCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.admin.OpenCarsCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.admin.OpenUserCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.admin.deleteCarCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.client.BusyDateCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.client.ClientMainCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.client.ClientOrdersCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.client.FeedBackCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.client.OrderCreateCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.client.OrderCreateFormCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.client.PayBillCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.client.SeeOrderCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.common.EditSettingsCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.common.ViewSettingsCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.manager.AllMessagesCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.manager.ChangeMessageCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.manager.ChangeOrderCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.manager.ManagerMainCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.manager.OpenOrderCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.manager.DeleteOrderCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.manager.SendAnswerCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol.ChooseLanguageCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol.LoginCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol.LoginForwardCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol.LogoutCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol.NoCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol.RegistrationCommand;
import ua.nure.mihaylichenko.SummaryTask4.web.command.outcontrol.UserRegistrationCommand;

/**
 * Main command container for controller 
 * all commands to "Controller" go throw this container.
 * @author A.Mihaylichenko
 *
 */
public class CommandContainer {
	
private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		
		/** out control commands */
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("chooseLanguage", new ChooseLanguageCommand());
		commands.put("registration", new RegistrationCommand());
		commands.put("userRegistration", new UserRegistrationCommand());
		commands.put("loginForward", new LoginForwardCommand());
		
		/** Common command */
		commands.put("viewSettings", new ViewSettingsCommand());
		commands.put("editSettings", new EditSettingsCommand());
		
		/** Client commands */
		commands.put("mainClient", new ClientMainCommand());
		commands.put("getBusyDate", new BusyDateCommand());
		commands.put("orderCreate", new OrderCreateFormCommand());
		commands.put("createOrder", new OrderCreateCommand());
		commands.put("clientOrders", new ClientOrdersCommand());
		commands.put("seeOrder", new SeeOrderCommand());
		commands.put("payBill", new PayBillCommand());
		commands.put("feedBack", new FeedBackCommand());
		
		/** Manager command */
		commands.put("mainManager", new ManagerMainCommand());
		commands.put("openOrder", new OpenOrderCommand());
		commands.put("changeOrder", new ChangeOrderCommand());
		commands.put("deleteOrder", new DeleteOrderCommand());
		commands.put("allMessages", new AllMessagesCommand());
		commands.put("changeMessage", new ChangeMessageCommand());
		commands.put("sendAnswer", new SendAnswerCommand());

		
		/** Admin command */
		commands.put("mainAdmin", new AdminMainCommand());
		commands.put("openUser", new OpenUserCommand());
		commands.put("changeUser", new ChangeUserCommand());
		commands.put("openCars", new OpenCarsCommand());
		commands.put("changeCar", new ChangeCarCommand());
		commands.put("openAddCarForm", new OpenAddCarFormCommand());
		commands.put("addCar", new AddCarCommand());
		commands.put("deleteCar", new deleteCarCommand());
		
		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}
	
	public static Command get(String command) {
		if (command == null || !commands.containsKey(command)) {
			LOG.trace("Command not found, name --> " + command);
			return commands.get("noCommand"); 
		}
		
		return commands.get(command);
	}

}
