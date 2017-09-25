package de.arlab.sudoku;

import java.util.Arrays;

/**
 * A class representing a sudoku.
 */
public final class Sudoku {

  private final int base;
  private final int length;
  private final int[][] content;

  /**
   * Creates a new sudoku with a given base.
   * The base is the length of a subgrid (which is equal the the square root of the sudoku's length).
   * All entries of the sudoku are initialized to -1 representing empty entries.
   * @param base the base
   */
  public Sudoku(final int base) {
    this.base = base;
    this.length = base * base;
    this.content = new int[length][length];
    for (final int[] row : content)
      Arrays.fill(row, -1);
  }

  /**
   * Returns the base (length of a subgrid) of the sudoku.
   * @return the base of the sudoku
   */
  public int base() {
    return base;
  }

  /**
   * Returns the length of the sudoku.
   * @return the length of the sudoku
   */
  public int length() {
    return length;
  }

  /**
   * Returns the number of the entry at a given row and column.
   * -1 indicates an empty entry.
   * @param row the row
   * @param col the column
   * @return the number of the entry
   */
  public int get(final int row, final int col) {
    return content[row][col];
  }

  /**
   * Sets the number of the entry at a given row and column to a given value.
   * @param row   the row
   * @param col   the column
   * @param value the value
   */
  public void set(final int row, final int col, final int value) {
    content[row][col] = value;
  }

  @Override
  public int hashCode() {
    int result = 0;
    for (final int[] row : content)
      result += Arrays.hashCode(row);
    return result;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other)
      return true;
    if (other == null || !(other instanceof Sudoku))
      return false;
    final Sudoku o = (Sudoku) other;
    if (this.base != o.base())
      return false;
    for (int i = 0; i < content.length; i++)
      if (!Arrays.equals(this.content[i], o.content[i]))
        return false;
    return true;
  }
}
