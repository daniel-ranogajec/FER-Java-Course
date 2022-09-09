package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Command that writes a directory listing.
 * 
 * @author Daniel_Ranogajec
 *
 */
public class LsShellCommand implements ShellCommand{

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
		File[] children = dir.listFiles();
		
		final Comparator<File> NAME = (f1, f2) -> f1.getName().compareTo(f2.getName());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Arrays.stream(children).sorted(NAME).forEach(file -> {
			BasicFileAttributeView faView = Files.getFileAttributeView(file.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = null;
			try {
				attributes = faView.readAttributes();
			} catch (IOException ex) {
				
			}
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			
			env.writeln(String.format("%s%s%s%s%10d %s %s", file.isDirectory() ? "d" : "-", file.canRead() ? "r" : "-",
					file.canWrite() ? "w" : "-", file.canExecute() ? "x" : "-", file.length(), formattedDateTime, file.getName()));
		});
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Writes a directory listing.",
				"Expects a single argument as directory.",
				"Output consists of 4 columns.",
				"First column indicates if current object is directory ( d ), readable ( r ), writable ( w ) and executable ( x ).",
				"Second column contains object size in bytes.",
				"Third column shows file creation date/time.",
				"Final column shows file name.");
	}

}
