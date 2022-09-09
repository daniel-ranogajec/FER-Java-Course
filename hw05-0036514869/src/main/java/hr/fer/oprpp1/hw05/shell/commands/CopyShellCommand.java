package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command used for copying one File into another
 * 
 * @author Daniel_Ranogajec
 *
 */
public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null)
			return ShellStatus.CONTINUE;
		
		String[] spt = arguments.split(" ");
		File original = new File(spt[0]);
		File copy = new File(spt[1]);
		if (!original.exists()) {
			env.writeln("Original file not found.");
			return ShellStatus.CONTINUE;
		}
		if (original.isDirectory()) {
			env.writeln("Cannot copy a directory.");
			return ShellStatus.CONTINUE;
		}
		if (original.equals(copy)) {
			env.writeln("Cannot make a copy into the original.");
			return ShellStatus.CONTINUE;
		}
		
		Path path = null;
		File f = null;
		if (copy.exists()) {
			if (copy.isDirectory()) {
				String p = copy.getPath() + "/" + original.getName();
				f = new File(p);
				if (f.exists()) {
					String s = overwrite(f, env);
					if (!s.equalsIgnoreCase("yes"))
						return ShellStatus.CONTINUE;
					f.delete();
				}
			} else {
				String s = overwrite(copy, env);
				if (!s.equalsIgnoreCase("yes"))
					return ShellStatus.CONTINUE;
				f = copy;
				copy.delete();
			}
		} else {
			f = copy;
		}
		
		try {
			path = Files.createFile(f.toPath());
		} catch (IOException ex) {
			env.writeln(ex.toString());
			return ShellStatus.CONTINUE;
		}

		try {
			List<String> lines = Files.readAllLines(original.toPath());
			Files.write(path, lines);
		} catch (IOException ex) {
			env.writeln(ex.toString());
		}
			
		return ShellStatus.CONTINUE;
	}

	private String overwrite(File f, Environment env) {
		env.writeln("A File with the same name was found. Overwrite? Yes / No?");
		return env.readLine();
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Copy a file into given directory or overwrite given file.",
				"Expects two arguments: source file name and destination file name.",
				"If destination file exists, user is asked to overwrite it.",
				"Works only with files (no directories).");
	}

}
