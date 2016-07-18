package com.simplyautomatic.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Simple implementation of a square matrix of Integers. Rows and columns are 1-indexed.
 */
public class Matrix {
	private final int size;
	private final Integer[][] matrix;
	
	/**
	 * Construct a square matrix, of width and height specified.
	 * @param size 
	 */
	public Matrix(int size) {
		this.size = size;
		this.matrix = new Integer[size][size];
		for (Integer[] row : matrix) {
			for (int column = 0; column < size; column++) {
				row[column] = 0;
			}
		}
	}
	
	/**
	 * Get matrix size.
	 * @return 
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Get current value at specified row/column. Row and column are 1-indexed.
	 * @param row
	 * @param column
	 * @return
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	public Integer getValue(int row, int column) throws ArrayIndexOutOfBoundsException {
		return matrix[row - 1][column - 1];
	}
	
	/**
	 * Set the current value at specified row/column. Row and column are 1-indexed.
	 * @param row
	 * @param column
	 * @param value
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	public void setValue(int row, int column, Integer value) throws ArrayIndexOutOfBoundsException {
		matrix[row - 1][column - 1] = value;
	}
	
	/**
	 * Get the total value of specified row. Row is 1-indexed.
	 * @param row
	 * @return
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	public Integer getRowTotal(int row) throws ArrayIndexOutOfBoundsException {
		return Arrays.stream(matrix[row - 1]).mapToInt(Integer::intValue).sum();
	}
	
	/**
	 * Get the total value of specified column. Column is 1-indexed.
	 * @param column
	 * @return
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	public Integer getColumnTotal(int column) throws ArrayIndexOutOfBoundsException {
		return Arrays.stream(matrix).map(rowArray -> rowArray[column - 1]).mapToInt(Integer::intValue).sum();
	}
	
	/**
	 * Get the total value of main diagonal.
	 * @return
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	public Integer getMainDiagonalTotal() throws ArrayIndexOutOfBoundsException {
		return IntStream.range(0, size).map(index -> matrix[index][index]).sum();
	}
	
	/**
	 * Get the total value of anti diagonal.
	 * @return
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	public Integer getAntiDiagonalTotal() throws ArrayIndexOutOfBoundsException {
		return IntStream.range(0, size).map(index -> matrix[index][size - 1 - index]).sum();
	}
	
	/**
	 * Flip the matrix vertically.
	 * @return this matrix, for method chaining
	 */
	public Matrix flip() {
		Collections.reverse(Arrays.asList(matrix));
		return this;
	}
	
	/**
	 * Mirror the matrix horizontally
	 * @return this matrix, for method chaining
	 */
	public Matrix mirror() {
		for (Integer[] row : matrix) {
			Collections.reverse(Arrays.asList(row));
		}
		return this;
	}
	
	/**
	 * Rotate the matrix 90 degrees clockwise
	 * @return this matrix, for method chaining
	 */
	public Matrix rotate() {
		Integer[][] matrixOld = new Integer[size][size];
		for (int row = 0; row < size; row++) {
			System.arraycopy(matrix[row], 0, matrixOld[row], 0, size);
		}
		for (int row = 0; row < size; ++row) {
			for (int column = 0; column < size; ++column) {
				matrix[row][column] = matrixOld[size - column - 1][row];
			}
		}
		return this;
	}
	
	/**
	 * Negate all entries in matrix
	 * @return this matrix, for method chaining
	 */
	public Matrix negate() {
		for (Integer[] row : matrix) {
			for (int column = 0; column < size; column++) {
				row[column] = -row[column];
			}
		}
		return this;
	}
	
	/**
	 * Convert the matrix to a simple string, for debugging
	 * @return 
	 */
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		Arrays.stream(matrix).forEach(row -> Arrays.stream(row).forEach(text::append));
		return text.toString();
	}
	
	/**
	 * Return a deep copy/clone of the matrix
	 * @return 
	 */
	public Matrix deepCopy() {
		Matrix clone = new Matrix(size);
		for (int row = 0; row < size; row++) {
			System.arraycopy(matrix[row], 0, clone.matrix[row], 0, size);
		}
		return clone;
	}
	
	/**
	 * Get a list of all equivalent matrix strings. For this usage, matrices are 
	 * equivalent if their values match after any number of rotations, mirrors, 
	 * or flips. There are 8 such Strings for any matrix (ignoring possibility of 
	 * duplicates): 4 rotations, mirrored horizontally and vertically, and
	 * transposed along main diagonal and antidiagonal.
	 * @return 
	 */
	public List<String> getAllEquivalentStrings() {
		List<String> equivalentMatrixStrings = new ArrayList<>();
		Matrix matrixCopy = deepCopy();
		// 4 standard rotations
		equivalentMatrixStrings.add(matrixCopy.toString());
		equivalentMatrixStrings.add(matrixCopy.rotate().toString());
		equivalentMatrixStrings.add(matrixCopy.rotate().toString());
		equivalentMatrixStrings.add(matrixCopy.rotate().toString());
		
		// Mirror horizontally and vertically
		equivalentMatrixStrings.add(matrixCopy.mirror().toString());
		matrixCopy = deepCopy();
		equivalentMatrixStrings.add(matrixCopy.flip().toString());
		
		// Transpose over diagonal and antidiagonal axes
		matrixCopy = deepCopy();
		equivalentMatrixStrings.add(matrixCopy.rotate().mirror().toString());
		matrixCopy = deepCopy();
		equivalentMatrixStrings.add(matrixCopy.rotate().flip().toString());
		
		return equivalentMatrixStrings;
	}
	
	/**
	 * Main method, for testing
	 * @param args 
	 */
	public static void main(String[] args) {
		Matrix m = new Matrix(3);
		System.out.println(m);
		int num = 1;
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				m.setValue(i, j, num++);
			}
		}
		System.out.println(m);
		System.out.println(m.flip());
		System.out.println(m.flip());
		System.out.println(m.mirror());
		System.out.println(m.mirror());
		System.out.println("row total " + m.getRowTotal(1));
		System.out.println("row total " + m.getRowTotal(2));
		System.out.println("row total " + m.getRowTotal(3));
		System.out.println("col total " + m.getColumnTotal(1));
		System.out.println("col total " + m.getColumnTotal(2));
		System.out.println("col total " + m.getColumnTotal(3));
		System.out.println("diag total " + m.getMainDiagonalTotal());
		System.out.println("diag total " + m.getAntiDiagonalTotal());
		System.out.println(m.rotate());
		System.out.println(m.rotate());
		System.out.println(m.rotate());
		System.out.println(m.rotate());
		System.out.println(m.negate());
		System.out.println(m.negate());
		List<String> allStrings = m.getAllEquivalentStrings();
		for (String s : allStrings) {
			System.out.println("s: " + s);
		}
	}
}
