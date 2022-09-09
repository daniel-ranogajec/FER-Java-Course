package hr.fer.oprpp1.hw02;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/**
 * Class used for testing SmartScriptParser 
 * 
 * @author Daniel_Ranogajec
 *
 */
public class SmartScriptTester {

	public static void main(String[] args) throws IOException {

		String filepath = "src/test/resources/extra/primjer6.txt";
		String docBody = new String(
				Files.readAllBytes(Paths.get(filepath)),
				StandardCharsets.UTF_8
				);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println("Original Document Body:");
		System.out.println(originalDocumentBody);
		System.out.println();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		System.out.println("Copy of Original Document Body:");
		System.out.println(document2);
		System.out.println();
		boolean same = document.equals(document2);
		System.out.print("Original Document Body equals Copy: ");
		System.out.println(same);
	}
}
