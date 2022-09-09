package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command used for exiting the shell
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ExitShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Terminates the shell.");
	}

}
