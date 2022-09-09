package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Interface that each command uses to communicate with user
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface Environment {
	
	/**
	 * Method used for reading next line in the shell
	 * @return String nextLine
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Method used to write a text without newline in the shell
	 * @param text String that you want to write
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Method used to write a line in the shell
	 * @param text String that you want to write
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Method that returns all available commands.
	 * @return SortedMap<String, ShellCommand>
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Method used for getting the Multiline symbol
	 * @return Multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Method used for setting the Multiline symbol
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	  * Method used for getting the Prompt symbol
	 * @return Prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Method used for setting the Prompt symbol
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Method used for getting the Morelines symbol
	 * @return Morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Method used for setting the Morelines symbol
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
