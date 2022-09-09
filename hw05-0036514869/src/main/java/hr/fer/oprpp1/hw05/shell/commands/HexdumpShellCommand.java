package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command that writes the hexdump of a file
 * 
 * @author Daniel_Ranogajec
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null)
			return ShellStatus.CONTINUE;
		
		
		Path path = Paths.get(arguments);
		if (Files.isDirectory(path)) {
			env.writeln("Cannot produce hexdump on a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try (InputStream is = Files.newInputStream(path)) {
			
			int position = 0x0;
			byte[] b = new byte[16];
			
			int k = 0;
			while (true) {
				k = is.read(b);
				if (k == -1) break;
				if (k < 32 || k > 127) {
					
				}
			}
			int cnt = 0;
			byte[] allBytes = Files.readAllBytes(path);
			String hex = Util.bytetohex(allBytes);
			String s = "";
			String ascii = "";
			while(cnt < hex.length()) {
				for (int i = 0; i < 16; i++) {
					
					if (cnt >= hex.length()) {
						while (i < 16) {
							if (i == 7)
								s += "  |";
							else 
								s += "   ";
							i++;
						}
						break;						
					}
					
					if (Integer.parseInt(hex.substring(cnt, cnt+2), 16) < 32 || Integer.parseInt(hex.substring(cnt, cnt+2), 16) > 127) {
						s += "0a";
						ascii += ".";
					} else {
						s += hex.substring(cnt, cnt+2);
						ascii += (char)Integer.parseInt(hex.substring(cnt, cnt+2), 16);
					}
					cnt += 2;
					if (i == 7) 
						s += "|";
					else 
						s += " ";
				}
				env.writeln(String.format("%08x: %s| %s", position, s, ascii));
				position += 16;
				s = "";
				ascii = "";
			}			
			
		} catch (IOException ex) {
			env.writeln(ex.toString());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Produces hex-output.",
				"Expects a single argument as file name",
				"Replaces the bytes whose value is less than 32 or greater than 127 with '.'");
	}

}
