package com.simplyautomatic.tictactoe;

import java.util.HashSet;
import java.util.Set;

/**
 * A repository of tic-tac-toe moves/board states.
 */
public class BoardStateRepository {
	private final Set<String> boardStateSet;

	public BoardStateRepository() {
		this.boardStateSet = new HashSet<>();
	}
	
	/**
	 * Add a board state to repository
	 * @param board 
	 */
	public void add(GameBoard board) {
		boardStateSet.add(board.toString());
	}
	
	/**
	 * Check whether board state is in repository
	 * @param board
	 * @return 
	 */
	public boolean contains(GameBoard board) {
		return boardStateSet.contains(board.toString());
	}
}
