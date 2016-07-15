package com.simplyautomatic.tictactoe;

/**
 * Enum for tic-tac-toe token types.
 */
public enum Token {
	X(1),
	O(-1);
	
	private final int value;
	
	/**
	 * Construct a Token
	 * @param value 
	 */
	Token(int value) {
		this.value = value;
	}

	/**
	 * Get numerical value of token, used for record-keeping
	 * @return 
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Get the token that matches specified value
	 * @param value
	 * @return 
	 */
	public static Token valueOf(int value) {
		for (Token token : Token.values()) {
			if (token.getValue() == value) {
				return token;
			}
		}
		return null;
	}
	
	/**
	 * Convert the token matching the specified value to a display string
	 * @param value
	 * @return "X", "O", or " "
	 */
	public static String toDisplayString(int value) {
		Token token = valueOf(value);
		return token != null ? token.name() : " ";
	}
}
