package hr.fer.oprpp1.hw05.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.*;

/**
 * Method that implements Environment interface
 * 
 * @author Daniel_Ranogajec
 *
 */
public class MyShellEnvironment implements Environment{
	
	private char PROMPTSYMBOL = '>';
	private char MULTILINESYMBOL = '|';
	private char MORELINESSYMBOL = '\\';
	
	private final SortedMap<String, ShellCommand> commands;
	private final Scanner sc;
	
	/**
	 * Constructor method that initializes Scanner and stores all commands
	 */
	public MyShellEnvironment() {
		sc = new Scanner(System.in);
		commands = new TreeMap<>();
		addAllCommands(commands);
	}
	
	/**
	 * Private method used for storing all available commands
	 * @param commands
	 */
	private static void addAllCommands(SortedMap<String, ShellCommand> commands) {
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("charset", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("help", new HelpShellCommand());
	}
	
	@Override
	public String readLine() throws ShellIOException {
		return sc.nextLine();
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return this.MULTILINESYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.MULTILINESYMBOL = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.PROMPTSYMBOL = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.MORELINESSYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.MORELINESSYMBOL = symbol;
	}

}
