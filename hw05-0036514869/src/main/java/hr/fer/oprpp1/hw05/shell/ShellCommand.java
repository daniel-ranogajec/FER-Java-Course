package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Interface that represents a shell command
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface ShellCommand {
	
	/**
	 * Method that is used for executing the command
	 * @param env Environment which is currently in use
	 * @param arguments parsed String
	 * @return ShellStatus.CONTINUE or ShellStatus.TERMINATE if exit is called
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Method used for getting a command name
	 * @return String command name
	 */
	String getCommandName();
	
	/**
	 * Method used for listing command descriptions
	 * @return List<String> of commands descriptions
	 */
	List<String> getCommandDescription();
	
}
