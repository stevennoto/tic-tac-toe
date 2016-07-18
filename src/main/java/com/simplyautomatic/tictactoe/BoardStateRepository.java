package com.simplyautomatic.tictactoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A repository of tic-tac-toe moves/board states, using SQLite for storage.
 * For this simple app, SQLExceptions are reported but ignored.
 */
public class BoardStateRepository {
	private static final String DATABASE_FILENAME = "tictactoe.db";
	private final Connection repoConnection;

	/**
	 * Create a new repository.
	 */
	public BoardStateRepository() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILENAME);
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS losing_moves (board_state STRING PRIMARY KEY)");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			repoConnection = connection;
		}
	}
	
	/**
	 * Add a board state to repository
	 * @param boardState 
	 */
	public void add(String boardState) {
		if (contains(boardState)) {
			return;
		}
		try (Statement statement = repoConnection.createStatement()) {
			statement.executeUpdate("INSERT INTO losing_moves VALUES ('" + boardState + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check whether board state is in repository
	 * @param boardState 
	 * @return 
	 */
	public boolean contains(String boardState) {
		String query = "SELECT COUNT(board_state) FROM losing_moves "
				+ "WHERE board_state = '" + boardState + "'";
		try (Statement statement = repoConnection.createStatement(); ResultSet rs = statement.executeQuery(query);) {
			return rs.next() && rs.getInt(1) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Get a debug dump of entire repository contents
	 * @return 
	 */
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		String query = "SELECT * FROM losing_moves";
		try (Statement statement = repoConnection.createStatement(); ResultSet rs = statement.executeQuery(query);) {
			while(rs.next()) {
				text.append(rs.getString(1));
				text.append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return text.toString();
	}
	
	/**
	 * Main method, for testing
	 * @param args 
	 * @throws java.sql.SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		BoardStateRepository repo = new BoardStateRepository();
		repo.add("test1");
		repo.add("test2");
		System.out.println("1? " + repo.contains("test1"));
		System.out.println("2? " + repo.contains("test2"));
		System.out.println("3? " + repo.contains("test3"));
		System.out.println("all:\n" + repo.toString());
	}
}
