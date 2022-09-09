package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Class used for formatting StudentRecords
 * 
 * @author Daniel_Ranogajec
 *
 */
public class RecordFormatter {

	/**
	 * Method that turns given List of StudentRecords to List of formatted Strings
	 * @param records List of StudentRecords
	 * @return new List of formatted Strings
	 */
	public static List<String> format(List<StudentRecord> records) {
		List<String> list = new ArrayList<>();
		if (records.size() > 0) {
			int jmbagSize = getSize(records, StudentRecord::getJMBAG);
			int lastNameSize = getSize(records, StudentRecord::getLastName);
			int firstNameSize = getSize(records, StudentRecord::getFirstName);
						
			String framework = constructFramework(jmbagSize, lastNameSize, firstNameSize);
			list.add(framework);
			
			for (StudentRecord sr : records) 
				list.add(String.format("| %s | %s | %s | %d |", 
						fixedLength(sr.getJMBAG(), jmbagSize), 
						fixedLength(sr.getLastName(), lastNameSize), 
						fixedLength(sr.getFirstName(), firstNameSize),
						sr.getFinalGrade()));
			
			list.add(framework);
			
		}
		list.add(String.format("Records selected: " + records.size()));
		return list;
	}
	
	/**
	 * Method used for adding whitespace on system output
	 * @return String with fixed length
	 */
	private static String fixedLength(String string, int length) {
	    return String.format("%-"+length+ "s", string);
	}
	
	/**
	 * Method used for constructing the framework
	 * @return String framework
	 */
	private static String constructFramework(int jmbagSize, int lastNameSize, int firstNameSize) {
		String s = "+=";
		for (int i = 0; i < jmbagSize; i++)
			s += "=";
		s += "=+=";
		for (int i = 0; i < lastNameSize; i++)
			s += "=";
		s += "=+=";
		for (int i = 0; i < firstNameSize; i++)
			s += "=";
		s += "=+===+";
		return s;
	}

	/**
	 * Method used for getting the size of biggest Object from the List
	 * @return int size
	 */
	private static int getSize(List<StudentRecord> records, Function<? super StudentRecord, String> mapper) {
		Optional<String> max = records.stream()
				.map(mapper)
				.max(Comparator.comparingInt(String::length));
		
		return max.get().length();
	}




}
