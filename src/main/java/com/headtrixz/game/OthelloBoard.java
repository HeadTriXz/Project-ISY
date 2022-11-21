package com.headtrixz.game;

import java.util.ArrayList;
import java.util.Arrays;

public class OthelloBoard extends GameBoard {
  /**
   * Represents a game board.
   *
   * @param size The size of the game board.
   */
  public OthelloBoard(int size) {
    super(size);
    clear();
  }

  /**
   * Returns a copy of the current game board.
   *
   * @return A copy of the current GameBoard object.
   */
  @Override
  public OthelloBoard clone() {
    OthelloBoard board = new OthelloBoard(size);
    board.cells = cells.clone();

    return board;
  }

  /**
   * Returns a list of all available cells on the board.
   *
   * @return A list of all available cells on the board.
   */
  public ArrayList<Integer> getValidMoves() {
    // TODO
    return null;
  }

  /**
   * Returns whether the board has no empty cells left.
   *
   * @return Whether the board has no empty cells left.
   */
  public boolean isFull() {
    // TODO
    return false;
  }

  /**
   * Returns whether the move is within the bounds of the board and the cell is empty.
   *
   * @param move The move to be checked
   * @return Whether the move is valid.
   */
  public boolean isValidMove(int move) {
    // TODO
    return false;
  }

  /**
   * Sets the value of the cell at the given index to the given player.
   *
   * @param move The move that the player wants to make.
   * @param player The player who is making the move.
   */
  public void setMove(int move, int player) {
    // TODO
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OthelloBoard gameBoard = (OthelloBoard) o;
    return Arrays.equals(cells, gameBoard.cells);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(cells);
  }
}
