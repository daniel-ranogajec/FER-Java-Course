package hr.fer.oprpp1.hw04.db;

/**
 * Class that offers constants of type IFIeldValueGetter
 * 
 * @author Daniel_Ranogajec
 *
 */
public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = (s -> s.getFirstName());
	public static final IFieldValueGetter LAST_NAME = (s -> s.getLastName());
	public static final IFieldValueGetter JMBAG = (s -> s.getJMBAG());

	
}
