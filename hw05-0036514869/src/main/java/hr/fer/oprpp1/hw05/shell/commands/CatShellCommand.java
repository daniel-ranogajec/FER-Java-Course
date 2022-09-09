package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command that opens given file and writes its content to console
 * 
 * @author Daniel_Ranogajec
 *
 */
public class CatShellCommand implements ShellCommand {
	
	private final static String splitter = "SPLITCONST";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null)
			return ShellStatus.CONTINUE;
		
		String[] spt = arguments.split(splitter);

		Path path;
		try {
			path = Paths.get(spt[0]);
		}catch (InvalidPathException ex) {
			env.writeln(ex.toString());
			return ShellStatus.CONTINUE;
		}
		
		Charset charset = null;
		if (spt.length == 1) 
			charset = Charset.defaultCharset();
		else {
			try {
			charset = Charset.forName(spt[1]);
			} catch (IllegalArgumentException ex) {
				env.writeln(ex.toString());
			}
		}
		
		try {
			List<String> lines = Files.readAllLines(path, charset);
			for (String line : lines) {
				env.writeln(line);
			}
		} catch (IOException ex) {
			env.writeln(ex.toString());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Command that opens given file and writes its content to console.",
				"Takes one or two arguments.", 
				"The first argument is path to some file and is mandatory.",
				"The second argument is charset name that should be used to interpret chars from bytes.",
				"If the second argument isnt provided, default platvorm charset is used.");
	}

}
