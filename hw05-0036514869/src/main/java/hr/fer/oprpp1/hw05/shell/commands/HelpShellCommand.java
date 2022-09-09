package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command used for listing names of other commands or for giving description of one
 * 
 * @author Daniel_Ranogajec
 *
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null)
			return ShellStatus.CONTINUE;
		
		SortedMap<String, ShellCommand> commands = env.commands();
		if (arguments.isEmpty()) {
	        for (Map.Entry<String, ShellCommand> entry : commands.entrySet()){
				env.writeln(entry.getValue().getCommandName());
			}
		} else {
			if (commands.containsKey(arguments.trim().toLowerCase())) {
				ShellCommand com = commands.get(arguments.trim().toLowerCase());
				env.write(com.getCommandName() + ": ");
				env.writeln(com.getCommandDescription().stream().collect(Collectors.joining(" ")).toString());
				
			} else {
				env.writeln("Unknown command.");
			}
		}
		
		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("On getting one argument lists all commands.", 
				"On getting two arguments gives command description of given command.");
	}

}
