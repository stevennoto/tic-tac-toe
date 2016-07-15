package com.simplyautomatic.tictactoe;

/**
 * Enum for tic-tac-toe token types.
 */
public enum Token {
	X(1),
	O(-1);
	
	private final int value;
	Token(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static Token valueOf(int value) {
		for (Token token : Token.values()) {
			if (token.getValue() == value) {
				return token;
			}
		}
		return null;
	}
	
	public static String toDisplayString(int value) {
		Token token = valueOf(value);
		return token != null ? token.name() : " ";
	}
}
