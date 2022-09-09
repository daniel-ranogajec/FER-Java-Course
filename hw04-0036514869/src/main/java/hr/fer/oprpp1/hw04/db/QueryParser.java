package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a parser of query statement
 * 
 * @author Daniel_Ranogajec
 *
 */
public class QueryParser {

	private List<ConditionalExpression> queries;
	private char[] data;
	private int currentIndex;

	/**
	 * Constructor method which gets a String of query statement and parses it to List of ConditionalExpressions
	 * @param query statement String
	 */
	public QueryParser(String query) {
		super();
		this.data = query.toCharArray();
		this.currentIndex = 0;
		this.queries = new ArrayList<>(); 
		getQueries();
	}

	/**
	 * Method used for parsing queries
	 */
	private void getQueries() {
		while ((checkEOF())) {
			skipWhitespace();
			if (!(checkEOF()))
				break;
			
			if (currentIndex + 4 < data.length && 
					Character.toLowerCase(data[currentIndex]) == 'j' && 
					Character.toLowerCase(data[currentIndex + 1]) == 'm' &&
					Character.toLowerCase(data[currentIndex + 2]) == 'b' && 
					Character.toLowerCase(data[currentIndex + 3]) == 'a' && 
					Character.toLowerCase(data[currentIndex + 4]) == 'g') {
				currentIndex += 5;
				getConditionalExpression(FieldValueGetters.JMBAG);

			} else if (currentIndex + 7 < data.length && 
					Character.toLowerCase(data[currentIndex]) == 'l' && 
					Character.toLowerCase(data[currentIndex + 1]) == 'a' &&
					Character.toLowerCase(data[currentIndex + 2]) == 's' && 
					Character.toLowerCase(data[currentIndex + 3]) == 't' && 
					Character.toLowerCase(data[currentIndex + 4]) == 'n' &&
					Character.toLowerCase(data[currentIndex + 5]) == 'a' &&
					Character.toLowerCase(data[currentIndex + 6]) == 'm' && 
					Character.toLowerCase(data[currentIndex + 7]) == 'e') {
				currentIndex += 8;
				getConditionalExpression(FieldValueGetters.LAST_NAME);

			} else if (currentIndex + 8 < data.length && 
					Character.toLowerCase(data[currentIndex]) == 'f' && 
					Character.toLowerCase(data[currentIndex + 1]) == 'i' &&
					Character.toLowerCase(data[currentIndex + 2]) == 'r' && 
					Character.toLowerCase(data[currentIndex + 3]) == 's' && 
					Character.toLowerCase(data[currentIndex + 4]) == 't' &&
					Character.toLowerCase(data[currentIndex + 5]) == 'n' &&
					Character.toLowerCase(data[currentIndex + 6]) == 'a' && 
					Character.toLowerCase(data[currentIndex + 7]) == 'm' && 
					Character.toLowerCase(data[currentIndex + 8]) == 'e') {
				currentIndex += 9;
				getConditionalExpression(FieldValueGetters.FIRST_NAME);

			} else if (!(queries.isEmpty()) &&  currentIndex + 2 < data.length && 
					Character.toLowerCase(data[currentIndex]) == 'a' && 
					Character.toLowerCase(data[currentIndex + 1]) == 'n' &&
					Character.toLowerCase(data[currentIndex + 2]) == 'd') {
				currentIndex += 3;
				continue;
				
			} else {
				throw new QueryParserException("No such field value!");
			}
		}
	}

	/**
	 * Method used for getting a ConditionalExpression and stringLiteral and adding new ConditionalExpression to the List
	 */
	private void getConditionalExpression(IFieldValueGetter fieldValue) {
		skipWhitespace();
		if (!(checkEOF()))
			throw new QueryParserException("Illegal argument.");
		
		IComparisonOperator operator = getOperator();
		currentIndex++;
		skipWhitespace();
		if (!(checkEOF()))
			throw new QueryParserException();
		if (data[currentIndex++] != '"') 
			throw new QueryParserException();
		String value = "";
		while (checkEOF() && data[currentIndex] != '"') {
			value += data[currentIndex++];
		}
		if (!(checkEOF()))
			throw new QueryParserException("Illegal argument.");
		
		currentIndex++;
		this.queries.add(new ConditionalExpression(fieldValue, value, operator));
	}

	/**
	 * Method used for getting an Operator
	 * @return IComparisonOperator operator
	 */
	private IComparisonOperator getOperator() {
		if (currentIndex + 1 >= data.length)
			throw new QueryParserException("Illegal argument.");
		if (data[currentIndex] == '<') {
			if (data[currentIndex + 1] != '=')
				return ComparisonOperators.LESS;
			else {
				currentIndex++;
				return ComparisonOperators.LESS_OR_EQUALS;
			}
			
		} else if (data[currentIndex] == '>') {
			if (data[currentIndex + 1] != '=')
				return ComparisonOperators.GREATER;
			else {
				currentIndex++;
				return ComparisonOperators.GREATER_OR_EQUALS;
			}
			
		} else if (data[currentIndex] == '=') {
			return ComparisonOperators.EQUALS;
		} else if (data[currentIndex] == '!' && data[currentIndex + 1] == '=') {
			currentIndex++;
			return ComparisonOperators.NOT_EQUALS;
			
		} else if (currentIndex + 3 < data.length) {
			if (Character.toLowerCase(data[currentIndex]) == 'l' &&
					Character.toLowerCase(data[currentIndex + 1]) == 'i' &&
					Character.toLowerCase(data[currentIndex + 2]) == 'k' &&
					Character.toLowerCase(data[currentIndex + 3]) == 'e') {
				currentIndex += 3;
				return ComparisonOperators.LIKE;
			}
		}
		throw new QueryParserException("No such operator!");
	}

	/**
	 * Method used for checking if query is direct (form of jmbag="xxx")
	 * @return <code>true</code> if query was of the form jmbag="xxx", <code>false</code> otherwise
	 */
	public boolean isDirectQuery() {
		return (queries.size() == 1 && queries.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG)
				&& queries.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS));
	}

	/**
	 * Method that returns the string which was given by direct query
	 * @return String which was given in equality comparison in direct query
	 * @throws IllegalStateException if query was not direct
	 */
	public String getQueriedJMBAG() {
		if (!(isDirectQuery())) 
			throw new IllegalStateException("Not a direct query!");
		
		return queries.get(0).getStringLiteral();
	}

	/**
	 * Method that returns a list of all ConditionalExpressions from all queries
	 * @return
	 */
	public List<ConditionalExpression> getQuery() {
		return queries;
	}

	/**
	 * Method used for skipping whitespace
	 */
	private void skipWhitespace() {
		while (checkEOF() && (data[currentIndex] == ' ' || data[currentIndex] == '\r' 
				|| data[currentIndex] == '\n' || data[currentIndex] == '\t')) 
			currentIndex++;
	}

	/**
	 * Method used for checking if string has reached its end
	 * @return true if string hasn't reached its end, false otherwise
	 */
	private boolean checkEOF() {
		return (currentIndex < data.length);
	}

}
