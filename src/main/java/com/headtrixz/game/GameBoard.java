package com.headtrixz.game;

import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;

public abstract class GameBoard {
  public static final int EMPTY_CELL = 0;
  public static final int PLAYER_ONE = 1;
  public static final int PLAYER_TWO = 2;

  protected int[] cells;
  protected final int size;

  public GameBoard(int size) {
    this.size = size;
    this.cells = new int[size * size];
  }

  /** Clears the game board. */
  public void clear() {
    Arrays.fill(cells, EMPTY_CELL);
  }

  /**
   * Returns the number of cells.
   *
   * @return The number of cells.
   */
  public int getCellCount() {
    return cells.length;
  }

  /**
   * Given an x and y coordinate, return the value of the cell at that coordinate.
   *
   * @param x The x coordinate of the cell
   * @param y The y coordinate of the cell
   * @return The value of the cell at the given coordinates.
   */
  public int getMove(int x, int y) {
    return cells[y * size + x];
  }

  /**
   * Returns the value of the cell at the index.
   *
   * @param move The index of the move.
   * @return The value of the cell at the index.
   */
  public int getMove(int move) {
    return cells[move];
  }

  /**
   * Returns the size of the game board.
   *
   * @return The size of the game board.
   */
  public int getSize() {
    return size;
  }

  public abstract ArrayList<Integer> getValidMoves();

  public abstract boolean isFull();

  public abstract boolean isValidMove(int move);

  public abstract void setMove(int move, int player);

  public abstract GameBoard clone();
}
