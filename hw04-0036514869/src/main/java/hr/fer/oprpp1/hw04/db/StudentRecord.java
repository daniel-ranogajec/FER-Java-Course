package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents a records of students
 * 
 * @author Daniel_Ranogajec
 *
 */
public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/**
	 * Constructor method used for registering a new Student
	 * @param jmbag String (not <code>null</code>)
	 * @param lastName String (not <code>null</code>)
	 * @param firstName String (not <code>null</code>)
	 * @param finalGrade Integer between 1 and 5
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		if (jmbag == null || lastName == null || firstName == null)
			throw new NullPointerException("Can't give null in argument.");
		if (!(finalGrade >= 1 && finalGrade <= 5))
			throw new IllegalArgumentException("Final grade must be between 1 and 5.");
				
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter method that returns students JMBAG
	 * @return String jmbag
	 */
	public String getJMBAG() {
		return jmbag;
	}

	/**
	 * Getter method that returns students last name
	 * @return String lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter method that returns students first name
	 * @return String firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter method that returns students final grade
	 * @return int lastName
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "jmbag=" + jmbag + ", lastName=" + lastName + ", firstName=" + firstName + ", finalGrade="
				+ finalGrade;
	}
	
	
	
}
