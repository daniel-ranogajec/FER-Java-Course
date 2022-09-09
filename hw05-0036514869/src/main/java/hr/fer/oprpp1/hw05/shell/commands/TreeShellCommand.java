package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command used for printing a tree
 * 
 * @author Daniel_Ranogajec
 *
 */
public class TreeShellCommand implements ShellCommand{
	
	private static int level = 0;
	private Environment env;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null)
			return ShellStatus.CONTINUE;
		
		File dir = new File(arguments);
		if (!dir.exists()) {
			env.writeln("Directory not found.");
			return ShellStatus.CONTINUE;
		}
		if (!dir.isDirectory()) {
			env.writeln("Not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		this.env = env;
		tree(dir);
				
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Frame method
	 * @param f
	 */
	private void tree(File f) {
		if (f.isFile()) {
			write(f);
			return;
		}
		if (!f.isDirectory()) {
			return;
		}
		
		File[] children = f.listFiles();
		if (children == null) 
			return;
		
		for (File c : children) {
			write(c);
			level++;
			tree(c);
			level--;
		}
	}
	
	/**
	 * Method used for writing in Environment
	 * @param f
	 */
	private void write(File f) {
		env.writeln(String.format("%s%s", " ".repeat(level*2), f.getName()));
		env.write(env.getPromptSymbol() + "");
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Prints a tree", 
				"Each directory level shifts two characters to the right");
	}

}
