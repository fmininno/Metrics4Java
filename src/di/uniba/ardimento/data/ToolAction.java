package di.uniba.ardimento.data;
/**
 * Pattern Command to execute a specific command
 * @author Francesco Mininno
 *
 */
public class ToolAction {
	
	Command theCommand;
	/**
	 * Constructor that initializes a ToolAction based on the command received
	 * @param newCommand command that will be instantiated
	 */
	public ToolAction(Command newCommand) {
		theCommand = newCommand;
	}
	
	public void action() {
		theCommand.execute();
	}
}