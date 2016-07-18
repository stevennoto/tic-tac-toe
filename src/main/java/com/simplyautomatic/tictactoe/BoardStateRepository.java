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
	 * @param boardState 
	 */
	public void add(String boardState) {
		boardStateSet.add(boardState);
	}
	
	/**
	 * Check whether board state is in repository
	 * @param boardState 
	 * @return 
	 */
	public boolean contains(String boardState) {
		return boardStateSet.contains(boardState);
	}
}
