package com.simplyautomatic.tictactoe;

import java.awt.Point;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Main class for tic-tac-toe game.
 */
public class TicTacToeGame {
	private final InputStream in;
	private final PrintStream out;
	private final Scanner scanner;
	
	// Game state:
	private GameBoard board;
	private Token playerToken;
	private Token cpuToken;
	private Token currentTurnToken;

	/**
	 * Construct a new tic-tac-toe game
	 * @param inStream input stream to use for user input of commands
	 * @param outStream output stream to print results and errors from commands
	 */
	public TicTacToeGame(InputStream inStream, PrintStream outStream) {
		in = inStream;
		out = outStream;
		scanner = new Scanner(in);
	}
	
	/**
	 * Start the main game loop
	 */
	public void start() {
		out.println("Welcome to tic-tac-toe. Shall we play a game?");
		
		// Get board size, create board
		out.println("Enter desired board size.");
		int boardSize = 0;
		while (true) {
			String input = scanner.nextLine();
			if (input != null && input.trim().matches("\\d+")) {
				boardSize = Integer.parseInt(input.trim());
			}
			if (boardSize > 1) {
				break;
			} else {
				out.println("Please enter a valid number, larger than 1.");
			}
		}
		board = new GameBoard(boardSize);
		
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
			
			// Place token, display board
			board.placeToken(currentTurnToken, (int)move.getX(), (int)move.getY());
			out.println(board.toString());
			
			// Check for win/draw
			if (board.isWon()) {
				Token winningToken = board.getWinningToken();
				if (winningToken == playerToken) {
					out.println("You won! Congratulations!");
				} else {
					out.println("You lost! How about a nice game of chess?");
				}
				break;
			} else if (board.isDrawn()) {
				out.println("The game is a draw. The only winning move is not to play.");
				break;
			}
			
			// Pass turn
			if (currentTurnToken == playerToken) {
				currentTurnToken = cpuToken;
			} else {
				currentTurnToken = playerToken;
			}
		}
	}
	
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
	
	private Point getCpuMove() {
		out.println("The computer places a token:");
		while (true) {
			// Choose a random (valid) move
			int row = (int) Math.floor(Math.random() * board.getBoardSize()) + 1;
			int column = (int) Math.floor(Math.random() * board.getBoardSize()) + 1;
			if (board.getTokenAt(row, column) != null) {
				continue;
			}
			return new Point(row, column);
		}
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
