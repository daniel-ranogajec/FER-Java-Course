package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command used for checking current symbols or changing them.
 * 
 * @author Daniel_Ranogajec
 *
 */
public class SymbolShellCommand implements ShellCommand{

	private final static String splitter = "SPLITCONST";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null)
			return ShellStatus.CONTINUE;
		
		String[] spt = arguments.split(splitter);
		
		if (spt.length == 1) {
			switch(spt[0].toUpperCase()) {
				case "PROMPT" -> System.out.println("Symbol for " + spt[0] + " is '" + env.getPromptSymbol() + "'");
				case "MORELINES" -> System.out.println("Symbol for " + spt[0] + " is '" + env.getMorelinesSymbol() + "'");
				case "MULTILINE" -> System.out.println("Symbol for " + spt[0] + " is '" + env.getMultilineSymbol() + "'");
			}
		} else {
			
			switch(spt[0].toUpperCase()) {
				case "PROMPT" -> { System.out.println("Symbol for " + spt[0] + " changed from '" + env.getPromptSymbol() + "' to '" + spt[1] + "'");
				env.setPromptSymbol(spt[1].charAt(0));
				}
				case "MORELINES" -> { System.out.println("Symbol for " + spt[0] + " changed from '" + env.getMorelinesSymbol() + "' to '" + spt[1] + "'");
				env.setMorelinesSymbol(spt[1].charAt(0));
				}
				case "MULTILINE" -> { System.out.println("Symbol for " + spt[0] + " changed from '" + env.getMultilineSymbol() + "' to '" + spt[1] + "'");
				env.setMultilineSymbol(spt[1].charAt(0));
				}
			} 
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("On getting a single argument returns the symbol for given argument.",
				"On getting two arguments changes the symbol for first argument using second argument.");
	}

}
