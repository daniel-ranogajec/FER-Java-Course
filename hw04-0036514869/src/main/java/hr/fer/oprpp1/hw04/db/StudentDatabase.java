package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used for storing StudentRecords
 * 
 * @author Daniel_Ranogajec
 *
 */
public class StudentDatabase {

	private List<String> rows;
	private List<StudentRecord> records;
	private Map<String, Integer> indexMap;
	private int index = 0;
	
	/**
	 * Constructor method that gets a list of String objects and fills internal list of StudentRecords
	 * @param rows List<String> that represents each StudentRecord
	 */
	public StudentDatabase(List<String> rows) {
		super();
		this.rows = rows;
		this.records = new ArrayList<>();
		this.indexMap = new LinkedHashMap<>();
		fillRecords();
	}
	
	/**
	 * Constructor method that gets name of file that will be read and fills internal list of StudentRecords
	 * @param fileName
	 */
	public StudentDatabase(String fileName) {
		this(readFile("src/main/resources/database.txt"));
	}
	
	/**
	 * Method used for filling student records
	 */
	private void fillRecords() {
		rows.forEach(s -> {
			String[] data = s.split("\t");
			StudentRecord student = new StudentRecord(data[0], data[1], data[2], Integer.parseInt(data[3]));
			if (indexMap.containsKey(data[0]))
				throw new IllegalArgumentException("Student with this JMBAG was already registered.");
				
			records.add(student);
			indexMap.put(data[0], index++);
		});
				
	}

	/**
	 * Method used for getting StudentRecord from its jmbag, returns <code>null</code> if StudentRecord was not found
	 * @param jmbag
	 * @return StudentRecord, <code>null</code> if StudentRecord was not found
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (indexMap.get(jmbag) == null)
			return null;
		
		return records.get(indexMap.get(jmbag));
	}
	
	/**
	 * Method that filters list of StudentRecords with given filter, returns new filtered list of StudentRecords
	 * @param filter IFiler
	 * @return new filtered list of StudentRecords
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> recordsFiltered = new ArrayList<>();
		records.forEach(s -> {
			if (filter.accepts(s))
				recordsFiltered.add(s);
		});
		return recordsFiltered;
	}
	
	/**
	 * Method used for reading the file
	 * @param fileName
	 * @return List<String> file parsed to Strings
	 */
	private static List<String> readFile(String fileName) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
				Paths.get(fileName),
				StandardCharsets.UTF_8);
		} catch (IOException ex) {
			throw new IllegalArgumentException("Can't find given file.");
		}
		return lines;

	}
	
	
}
