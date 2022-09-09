package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumeration that is representing types of SmartScriptTokens
 * 
 * @author Daniel_Ranogajec
 *
 */
public enum SmartScriptTokenType {
	TEXT,
	TAG,
	ENDTAG,
	VARIABLE,
	DOUBLE,
	INTEGER,
	FUNCTION,
	OPERATOR,
	STRING,
	EOF
}
