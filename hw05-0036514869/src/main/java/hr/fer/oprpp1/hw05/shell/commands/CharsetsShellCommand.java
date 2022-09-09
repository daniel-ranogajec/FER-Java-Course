package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command charsets takes no arguments and lists names of supported charsets for your Java platform
 * 
 * @author Daniel_Ranogajec
 *
 */
public class CharsetsShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, Charset> map = Charset.availableCharsets();
		for (Map.Entry<String, Charset> entry: map.entrySet()) {
			env.writeln(entry.getValue().toString());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Lists names of supported charsets.",
				"Takes no arguments.");
	}

}
