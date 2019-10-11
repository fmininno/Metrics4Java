package di.uniba.ardimento.codeanl;
/**
 * Pattern Command per eseguire un determinato comando 
 * @author franc
 *
 */
public class ToolAction {
	
	Command theCommand;
	/**
	 * Istanzia una ToolAction in base al comando ricevuto
	 * @param newCommand
	 */
	public ToolAction(Command newCommand) {
		theCommand = newCommand;
	}
	
	public void action() {
		theCommand.execute();
	}
}
