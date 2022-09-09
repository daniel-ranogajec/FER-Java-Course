package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command used for making a new directory
 * 
 * @author Daniel_Ranogajec
 *
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null)
			return ShellStatus.CONTINUE;
		
		File f = new File(arguments);
		if (f.exists()) {
			env.writeln("File already exists.");
			return ShellStatus.CONTINUE;
		}
		if (f.mkdir())
			env.writeln("Directory created.");
		else
			env.writeln("Directory not created.");
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Makes a directory.",
				"Expects a single argument as directory name.");
	}

}
