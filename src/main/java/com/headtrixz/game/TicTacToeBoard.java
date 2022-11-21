package com.headtrixz.game;

import java.util.ArrayList;
import java.util.Arrays;

/** Represents a game board. */
public class TicTacToeBoard extends GameBoard {
  /**
   * Represents a game board.
   *
   * @param size The size of the game board.
   */
  public TicTacToeBoard(int size) {
    super(size);
    clear();
  }

  /**
   * Returns a copy of the current game board.
   *
   * @return A copy of the current GameBoard object.
   */
  @Override
  public TicTacToeBoard clone() {
    TicTacToeBoard board = new TicTacToeBoard(size);
    board.cells = cells.clone();

    return board;
  }

  /**
   * Returns a list of all available cells on the board.
   *
   * @return A list of all available cells on the board.
   */
  public ArrayList<Integer> getValidMoves() {
    ArrayList<Integer> list = new ArrayList<>();
    for (int i = 0; i < cells.length; i++) {
      if (cells[i] == EMPTY_CELL) {
        list.add(i);
      }
    }

    return list;
  }

  /**
   * Returns whether the board has no empty cells left.
   *
   * @return Whether the board has no empty cells left.
   */
  public boolean isFull() {
    for (int cell : cells) {
      if (cell == EMPTY_CELL) {
        return false;
      }
    }

    return true;
  }

  /**
   * Returns whether the move is within the bounds of the board and the cell is empty.
   *
   * @param move The move to be checked
   * @return Whether the move is valid.
   */
  public boolean isValidMove(int move) {
    return move >= 0 && move < cells.length && cells[move] == EMPTY_CELL;
  }

  /**
   * Sets the value of the cell at the given index to the given player.
   *
   * @param move The move that the player wants to make.
   * @param player The player who is making the move.
   */
  public void setMove(int move, int player) {
    cells[move] = player;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TicTacToeBoard gameBoard = (TicTacToeBoard) o;
    return Arrays.equals(cells, gameBoard.cells);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(cells);
  }
}
