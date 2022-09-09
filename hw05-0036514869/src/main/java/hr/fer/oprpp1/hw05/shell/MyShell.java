package hr.fer.oprpp1.hw05.shell;

/**
 * Main program that represents a shell
 * 
 * @author Daniel_Ranogajec
 *
 */
public class MyShell{
	
	private final static String splitter = "SPLITCONST";

	public static void main(String[] args) {
		System.out.println("Welcome to MyShell v 1.0");

		MyShellEnvironment env = new MyShellEnvironment();		

		ShellStatus status = ShellStatus.CONTINUE;
		while (status != ShellStatus.TERMINATE) {
			env.write(env.getPromptSymbol() + " ");
			String s = env.readLine();
			while(s.endsWith(env.getMorelinesSymbol() + "")) {
				env.write(env.getMultilineSymbol() + " ");		
				s = s.substring(0, s.length()-1) + " " +  env.readLine();
			}

			String[] spt = s.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			for (int i = 0; i < spt.length; i++) {
				spt[i] = spt[i].replaceAll("\"", "").trim();
			}			
			
			switch (spt[0].toLowerCase()) {
				case "exit", "charset" -> {
					if (spt.length != 1) {System.out.println("Command " + spt[0].toLowerCase() + " takes no arguments.");
					break;
					}
					status = env.commands().get(spt[0].toLowerCase()).executeCommand(env, "");
				}
				case "cat", "symbol" -> {
					if (!(spt.length == 2 || spt.length == 3)) {System.out.println("Command " + spt[0].toLowerCase() + " takes one or two arguments.");
					break;
					}
					status = env.commands().get(spt[0].toLowerCase()).executeCommand(env, spt.length == 2 ? spt[1] : spt[1] + splitter + spt[2]);
				}
				case "ls", "tree", "mkdir", "hexdump"-> {
					if (spt.length != 2) {System.out.println("Command " + spt[0].toLowerCase() + " takes one argument.");
					break;
					}
					status = env.commands().get(spt[0].toLowerCase()).executeCommand(env, spt[1]);
				}
				case "copy" -> {
					if (spt.length != 3) {System.out.println("Command " + spt[0].toLowerCase() + " takes two arguments.");
					break;
					}
					status = env.commands().get(spt[0].toLowerCase()).executeCommand(env, spt[1] + " " + spt[2]);
				}
				case "help" -> {
					if (!(spt.length == 1 || spt.length == 2)) {System.out.println("Command " + spt[0].toLowerCase() + " takes one or zero arguments.");
					break;
					}
					status = env.commands().get(spt[0].toLowerCase()).executeCommand(env, spt.length == 1 ? "" : spt[1]);
				}
			}
			
		}

	}



}
