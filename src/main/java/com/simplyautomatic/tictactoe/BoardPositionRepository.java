package com.simplyautomatic.tictactoe;

import java.util.HashSet;
import java.util.Set;

/**
 * A repository of tic-tac-toe moves/board positions.
 */
public class BoardPositionRepository {
	private final Set<String> boardPositionSet;

	public BoardPositionRepository() {
		this.boardPositionSet = new HashSet<>();
	}
	
	public void add(String boardPosition) {
		boardPositionSet.add(boardPosition);
	}
	
	public boolean contains(String boardPosition) {
		return boardPositionSet.contains(boardPosition);
	}
}
