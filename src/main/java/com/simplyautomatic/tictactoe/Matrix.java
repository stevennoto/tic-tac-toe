package com.simplyautomatic.tictactoe;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

/**
 * Simple implementation of a square matrix of Integers. Rows and columns are 1-indexed.
 */
public class Matrix {
	private final int size;
	private final Integer[][] matrix;
//TODO: convert back to 'int'? Remove mapToInt(Integer::intValue). But causes problems with mirror().
	
	public Matrix(int size) {
		this.size = size;
		this.matrix = new Integer[size][size];
		for (Integer[] row : matrix) {
			for (int column = 0; column < size; column++) {
				row[column] = 0;
			}
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public Integer getValue(int row, int column) throws ArrayIndexOutOfBoundsException {
		return matrix[row - 1][column - 1];
	}
	
	public void setValue(int row, int column, Integer value) throws ArrayIndexOutOfBoundsException {
		matrix[row - 1][column - 1] = value;
	}
	
	public Integer getRowTotal(int row) throws ArrayIndexOutOfBoundsException {
		return Arrays.stream(matrix[row - 1]).mapToInt(Integer::intValue).sum();
	}
	
	public Integer getColumnTotal(int column) throws ArrayIndexOutOfBoundsException {
		return Arrays.stream(matrix).map(rowArray -> rowArray[column - 1]).mapToInt(Integer::intValue).sum();
	}
	
	public Integer getMainDiagonalTotal() throws ArrayIndexOutOfBoundsException {
		return IntStream.range(0, size).map(index -> matrix[index][index]).sum();
	}
	
	public Integer getAntiDiagonalTotal() throws ArrayIndexOutOfBoundsException {
		return IntStream.range(0, size).map(index -> matrix[index][size - 1 - index]).sum();
	}
	
	public void flip() {
		Collections.reverse(Arrays.asList(matrix));
	}
	
	public void mirror() {
		for (Integer[] row : matrix) {
			Collections.reverse(Arrays.asList(row));
		}
	}
	
	public void rotate() {
		Integer[][] matrixOld = new Integer[size][size];
		for (int row = 0; row < size; row++) {
			System.arraycopy(matrix[row], 0, matrixOld[row], 0, size);
		}
		for (int row = 0; row < size; ++row) {
			for (int column = 0; column < size; ++column) {
				matrix[row][column] = matrixOld[size - column - 1][row];
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		Arrays.stream(matrix).forEach(row -> Arrays.stream(row).forEach(text::append));
		return text.toString();
	}
	
//	@Override
//	public int hashCode() {
//		int hash = 3;
//		hash = 79 * hash + Objects.hashCode(this.toString());
//		return hash;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == null) {
//			return false;
//		}
//		if (getClass() != obj.getClass()) {
//			return false;
//		}
//		final Matrix other = (Matrix) obj;
//		if (!Objects.equals(this.toString(), other.toString())) {
//			return false;
//		}
//		return true;
//	}
	
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
		m.flip();
		System.out.println(m);
		m.flip();
		System.out.println(m);
		m.mirror();
		System.out.println(m);
		m.mirror();
		System.out.println(m);
		System.out.println("row total " + m.getRowTotal(1));
		System.out.println("row total " + m.getRowTotal(2));
		System.out.println("row total " + m.getRowTotal(3));
		System.out.println("col total " + m.getColumnTotal(1));
		System.out.println("col total " + m.getColumnTotal(2));
		System.out.println("col total " + m.getColumnTotal(3));
		System.out.println("diag total " + m.getMainDiagonalTotal());
		System.out.println("diag total " + m.getAntiDiagonalTotal());
		m.rotate();
		System.out.println(m);
		m.rotate();
		System.out.println(m);
		m.rotate();
		System.out.println(m);
		m.rotate();
		System.out.println(m);
	}
}
