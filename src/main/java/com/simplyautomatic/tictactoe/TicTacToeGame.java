package com.simplyautomatic.tictactoe;

import java.awt.Point;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for tic-tac-toe game.
 */
public class TicTacToeGame {
	// I/O:
	private final InputStream in;
	private final PrintStream out;
	private final Scanner scanner;
	
	// Game state:
	private GameBoard board;
	private Token playerToken;
	private Token cpuToken;
	private Token currentTurnToken;
	
	// AI:
	private final BoardStateRepository losingMoves;

	/**
	 * Construct a new tic-tac-toe game
	 * @param inStream input stream to use for user input of commands
	 * @param outStream output stream to print results and errors from commands
	 */
	public TicTacToeGame(InputStream inStream, PrintStream outStream) {
		in = inStream;
		out = outStream;
		scanner = new Scanner(in);
		losingMoves = new BoardStateRepository();
	}
	
	/**
	 * Start the main game loop
	 */
	public void start() {
		out.println("Welcome to tic-tac-toe. Shall we play a game?");
		
		while (true) {
		
			// Get board size, create board
			out.println("Enter desired board size.");
			int boardSize = 0;
			while (true) {
				String input = scanner.nextLine();
				if (input != null && input.trim().matches("\\d+")) {
					boardSize = Integer.parseInt(input.trim());
				}
				if (boardSize > 2) {
					break;
				} else {
					out.println("Please enter a valid number, larger than 2.");
				}
			}
			board = new GameBoard(boardSize);
			GameBoard lastMoveBoard = new GameBoard(board);

			// Get token preference
			out.println("Would you like to play as X or O? (X goes first)");
			while (true) {
				String input = scanner.nextLine();
				if (input != null && input.equalsIgnoreCase("X")) {
					playerToken = Token.X;
					cpuToken = Token.O;
					break;
				} else if (input != null && input.equalsIgnoreCase("O")) {
					playerToken = Token.O;
					cpuToken = Token.X;
					break;
				} else {
					out.println("Please enter X or O.");
				}
			}
			currentTurnToken = Token.X;

			// Display initial empty board
			out.println("Let's play!");
			out.println(board.toString());
			
			// Main game loop
			while(true) {
				// Get move from player or CPU
				Point move;
				if (currentTurnToken == playerToken) {
					move = getPlayerMove();
				} else {
					move = getCpuMove();
				}
				
				// If CPU conceded, tell player
				if (move == null) {
					out.println("I concede! The only winning move is not to play.");
					break;
				}

				// Place token, display board
				board.placeToken(currentTurnToken, (int)move.getX(), (int)move.getY());
				out.println(board.toString());
				
				// If CPU move, remember board state, in case this is a losing move
				if (currentTurnToken == cpuToken) {
					lastMoveBoard = new GameBoard(board);
				}

				// Check for win/draw
				if (board.isWon()) {
					Token winningToken = board.getWinningToken();
					if (winningToken == playerToken) {
						out.println("You won! Congratulations!");
						// If player won, save last board state (the CPU's losing move)
						recordLosingMove(lastMoveBoard);
					} else {
						out.println("You lost! How about a nice game of chess?");
					}
					break;
				} else if (board.isDrawn()) {
					out.println("The game is a draw. Good game!");
					break;
				}

				// Pass turn
				if (currentTurnToken == playerToken) {
					currentTurnToken = cpuToken;
				} else {
					currentTurnToken = playerToken;
				}
			}
			
			out.println("Would you like to play again?");
			String input = scanner.nextLine();
			if (input == null || !input.trim().matches("[yY].*")) {
				return;
			}
		}
	}
	
	/**
	 * Get the player's move, ensuring it is a valid move
	 * @return 
	 */
	private Point getPlayerMove() {
		out.println("Where would you like to place your " + playerToken + "? Enter coordinates like 1, 1.");
		while (true) {
			String input = scanner.nextLine();
			// Parse entered coordinates
			String[] coordinates = input.split(",");
			if (coordinates == null || coordinates.length != 2
					|| !coordinates[0].trim().matches("\\d+") || !coordinates[1].trim().matches("\\d+")) {
				out.println("Please enter coordinates like 1, 1.");
				continue;
			}
			int row = Integer.parseInt(coordinates[0].trim());
			int column = Integer.parseInt(coordinates[1].trim());
			
			// Validate coordinates
			if (row <= 0 || row > board.getBoardSize()
					|| column <= 0 || column > board.getBoardSize()) {
				out.println("Please enter coordinates between 1 and " + board.getBoardSize() + ".");
				continue;
			}
			if (board.getTokenAt(row, column) != null) {
				out.println("Please enter coordinates of an empty position.");
				continue;
			}
			return new Point(row, column);
		}
	}
	
	/**
	 * Get the computer's move, random, but valid
	 * @return valid move, or null if conceding
	 */
	private Point getCpuMove() {
		int moveAttempts = 0;
		while (true) {
			// If cannot find a valid, non-losing move, then concede
			if (++moveAttempts > 10 * board.getBoardSize() * board.getBoardSize()) {
				return null;
			}
			
			// Choose a random (valid) move
			int row = (int) Math.floor(Math.random() * board.getBoardSize()) + 1;
			int column = (int) Math.floor(Math.random() * board.getBoardSize()) + 1;
			if (board.getTokenAt(row, column) != null) {
				continue;
			}
			
			// Check to see if this move is a losing move, that was previously recorded
			GameBoard proposedGameState = new GameBoard(board);
			proposedGameState.placeToken(cpuToken, row, column);
			if (isLosingMove(proposedGameState)) {
				//System.out.println("Avoiding losing move:\n" + proposedGameState.toString());
				continue;
			}
			
			out.println("The computer places a token:");
			return new Point(row, column);
		}
	}
	
	/**
	 * Record a board state as having lost, to avoid in the future.
	 * @param board Board state with most-recent losing CPU move
	 */
	private void recordLosingMove(GameBoard board) {
		System.out.println("Recording losing move:\n" + board.toString());
		losingMoves.add(board.getBoardString());
	}
	
	/**
	 * Check whether a proposed board state was previously recorded as a losing move
	 * @param board
	 * @return 
	 */
	private boolean isLosingMove(GameBoard board) {
		List<String> equivalentBoards = board.getAllEquivalentBoardStrings();
		return equivalentBoards.stream().anyMatch(equivalentBoard -> (losingMoves.contains(equivalentBoard)));
	}
	
	/**
	 * Main method: create and start the game
	 * @param args 
	 */
	public static void main(String[] args) {
		TicTacToeGame game = new TicTacToeGame(System.in, System.out);
		game.start();
	}
}
