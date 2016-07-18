package com.simplyautomatic.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of a tic-tac-toe game board, of arbitrary size.
 */
public class GameBoard {
	private final Matrix boardMatrix;
	private final int boardSize;
	private final int numBoardPositions;
	private int numMoves;
	
	/**
	 * Construct a game board of specified size.
	 * @param boardSize 
	 */
	public GameBoard(int boardSize) {
		this.boardMatrix = new Matrix(boardSize);
		this.numMoves = 0;
		this.boardSize = boardSize;
		this.numBoardPositions = boardSize * boardSize;
	}

	/**
	 * Copy-constructor, to make a new board identical to existing one
	 * @param other
	 */
	public GameBoard(GameBoard other) {
		this.boardMatrix = other.boardMatrix.deepCopy();
		this.numMoves = other.numMoves;
		this.boardSize = other.boardSize;
		this.numBoardPositions = other.numBoardPositions;
	}

	/**
	 * Get board size.
	 * @return 
	 */
	public int getBoardSize() {
		return boardSize;
	}
	
	/**
	 * Place a player token at specified (1-indexed) position.
	 * @param token
	 * @param row
	 * @param column 
	 * @throws IllegalArgumentException if position is occupied/illegal move
	 */
	public void placeToken(Token token, int row, int column) throws IllegalArgumentException {
		if (boardMatrix.getValue(row, column) != 0) {
			throw new IllegalArgumentException("Position already occupied");
		}
		boardMatrix.setValue(row, column, token.getValue());
		numMoves++;
	}
	
	/**
	 * Gets the token currently at specified (1-indexed) position.
	 * @param row
	 * @param column
	 * @return Token at position, or null if none
	 */
	public Token getTokenAt(int row, int column) {
		int value = boardMatrix.getValue(row, column);
		if (value != 0) {
			return Token.valueOf(value);
		}
		return null;
	}
	
	/**
	 * Gets the token of the player who has won, if any player has.
	 * @return Token of winner, or null if game not yet won
	 */
	public Token getWinningToken() {
		int  largestScore = getLargestRowColumnDiagonalScore();
		if (largestScore == Token.X.getValue() * boardSize) {
			return Token.X;
		} else if (largestScore == Token.O.getValue() * boardSize) {
			return Token.O;
		} else {
			return null;
		}
	}
	
	/**
	 * Determines whether the game has been won, by either player.
	 * @return 
	 */
	public boolean isWon() {
		return getWinningToken() != null;
	}
	
	// Helper to get largest-magnitude (regardless of sign) score from all rows/cols/diags
	public int getLargestRowColumnDiagonalScore() {
		int largestScore = 0;
		for (int index = 1; index <= boardSize; index++) {
			if (Math.abs(boardMatrix.getRowTotal(index)) > Math.abs(largestScore)) {
				largestScore = boardMatrix.getRowTotal(index);
			}
			if (Math.abs(boardMatrix.getColumnTotal(index)) > Math.abs(largestScore)) {
				largestScore = boardMatrix.getColumnTotal(index);
			}
		}
		if (Math.abs(boardMatrix.getMainDiagonalTotal()) > Math.abs(largestScore)) {
			largestScore = boardMatrix.getMainDiagonalTotal();
		}
		if (Math.abs(boardMatrix.getAntiDiagonalTotal()) > Math.abs(largestScore)) {
			largestScore = boardMatrix.getAntiDiagonalTotal();
		}
		return largestScore;
	}
	
	/**
	 * Determines whether the game is a draw/tie.
	 * @return 
	 */
	public boolean isDrawn() {
		return !isWon() && numMoves >= numBoardPositions;
	}
	
	/**
	 * Get a simple String representation of the board.
	 * @return 
	 */
	public String getBoardString() {
		return boardMatrix.toString();
	}
	
	/**
	 * Get a list of all equivalent board representations. Boards are equivalent 
	 * if their token positions match, after any number of rotations, mirrors, 
	 * or token-swaps (switching X and O).
	 * @return 
	 */
	public List<String> getAllEquivalentBoardStrings() {
		// Get all equivalent forms of board, and of opposite board (switch X and O)
		List<String> equivalentBoardStrings = boardMatrix.getAllEquivalentStrings();
		equivalentBoardStrings.addAll(boardMatrix.deepCopy().negate().getAllEquivalentStrings());
		return equivalentBoardStrings;
	}
	
	/**
	 * Convert the game board to a simple display string, suitable for printing.
	 * @return 
	 */
	@Override
	public String toString() {
		String rowDivider = IntStream.range(0, 4 * boardSize - 3).mapToObj(i -> "-").collect(Collectors.joining());
		return IntStream.range(1, boardSize + 1).mapToObj(row -> 
				IntStream.range(1, boardSize + 1).mapToObj(column -> Token.toDisplayString(boardMatrix.getValue(row, column))).collect(Collectors.joining(" | "))
		).collect(Collectors.joining("\n" + rowDivider + "\n"));
	}
	
	/**
	 * Main method, for testing
	 * @param args 
	 */
	public static void main(String[] args) {
		GameBoard gb = new GameBoard(3);
		gb.placeToken(Token.X, 1, 1);
		gb.placeToken(Token.X, 1, 2);
		System.out.println(gb.boardMatrix.toString() + "/" + gb.isWon() + ", winner=" + gb.getWinningToken());
		gb.placeToken(Token.O, 1, 3);
		gb.placeToken(Token.O, 2, 3);
		System.out.println(gb.boardMatrix.toString() + "/" + gb.isWon() + ", winner=" + gb.getWinningToken());
		gb.placeToken(Token.O, 3, 3);
		System.out.println(gb.boardMatrix.toString() + "/" + gb.isWon() + ", winner=" + gb.getWinningToken());
		System.out.println("board:\n" + gb.toString());
	}
}
