package com.headtrixz.game;

import static com.headtrixz.game.GameBoard.*;

import com.headtrixz.game.players.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game of Othello.
 */
public class Othello extends GameModel {
  private static final int BOARD_SIZE = 8;
  private static final String NAME = "OTHELLO";

  /**
   * Represents a game of Othello.
   */
  public Othello() {
    super(NAME, BOARD_SIZE);
    board.setMove(27, PLAYER_ONE);
    board.setMove(28, PLAYER_TWO);
    board.setMove(35, PLAYER_TWO);
    board.setMove(36, PLAYER_ONE);
  }

  /**
   * Returns the current state of the game.
   *
   * @return The current state of the game.
   */
  @Override
  public GameState getState() {
    GameState state = helper.getState();
    if (state != null) {
      return state;
    }
    if (hasWon(PLAYER_ONE)) {
      return GameState.PLAYER_ONE_WON;
    }
    if (hasWon(PLAYER_TWO)) {
      return GameState.PLAYER_TWO_WON;
    }
    return GameState.PLAYING;
  }

  /**
   * get the score of the game in its current state. the scoring is -2 if current
   * player has won, -1
   * if current player has lost, 0 if game is still going or ended in draw
   *
   * @return the score of the board
   */
  public int getScore(Player currentPlayer, int depth) {
    if (getState() == GameState.DRAW || getState() == GameState.PLAYING) {
      return 0;
    }

    if (hasPlayerWon(currentPlayer)) {
      return getMaxScore() / depth;
    }

    return getMinScore() / depth; // player has lost
  }

  /**
   * Returns the maximum score used for MiniMax.
   *
   * @return The maximum score.
   */
  public int getMaxScore() {
    return 1000;
  }

  /**
   * Returns the minimum score used for MiniMax.
   *
   * @return The minimum score.
   */
  public int getMinScore() {
    return -1000;
  }

  /**
   * Returns a list of all available cells on the board.
   *
   * @return A list of all available cells on the board.
   */
  public List<Integer> getValidMoves() {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < board.getCellCount(); i++) {
      if (isValidMove(i, getCurrentPlayer().getId())) {
        list.add(i);
      }
    }

    return list;
  }

  /**
   * Check whether player has won.
   *
   * @param player the player to check.
   * @return has player won.
   */
  private boolean hasWon(int player) {
    int checkPlayer = getCurrentPlayer().getId() == 1 ? 2 : 1;
    for (int i = 0; i < board.getCellCount(); i++) {
      if (isValidMove(i, checkPlayer)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns whether the move flips a stone of another player.
   *
   * @param move The move to be checked
   * @return Whether the move is valid.
   */
  @Override
  public boolean isValidMove(int move) {
    return isValidMove(move, getCurrentPlayer().getId());
  }

  /**
   * Returns whether the move flips a stone of another player.
   *
   * @param move   The move to be checked.
   * @param player The player in question.
   * @return Whether the move is valid.
   */
  public boolean isValidMove(int move, int player) {
    if (move < 0) {
      return false;
    }

    if (board.getMove(move) != EMPTY_CELL) {
      return false;
    }

    List<Integer> flips = getFlips(move, player);
    return flips.size() > 0;
  }

  /**
   * Updates the board to show the move that was just played.
   *
   * @param move   the possition on the board
   * @param player the player who is setting the set.
   */
  @Override
  public void setMove(int move, int player) {
    List<Integer> flips = getFlips(move, player);
    board.setMove(move, player);

    for (int cell : flips) {
      board.setMove(cell, player);
    }
  }

  /**
   * Show the tiles that will get fliped to the player when a stone is placed.
   *
   * @param move   the possition on the board.
   * @param player the player who's turn it is.
   * @return
   */
  public List<Integer> getFlips(int move, int player) {
    List<List<Integer>> directions = new ArrayList<>();

    List<Integer> ls = new ArrayList<>();
    // up
    for (int i = move; i > 7; i -= 8) {
      ls.add(i);
    }
    directions.add(new ArrayList<>(ls));
    ls.clear();

    // left
    for (int i = move; i % 8 != 0 && i > 0; i--) {
      ls.add(i);
    }
    directions.add(new ArrayList<>(ls));
    ls.clear();

    // down
    for (int i = move; i <= 55; i += 8) {
      ls.add(i);
    }
    directions.add(new ArrayList<>(ls));
    ls.clear();

    // right
    for (int i = move; (i + 1) % 8 != 0 && i > 0; i++) {
      ls.add(i);
    }
    directions.add(new ArrayList<>(ls));
    ls.clear();

    // left up
    for (int i = move; i % 8 != 0 && i > 8; i -= 9) {
      ls.add(i);
    }
    directions.add(new ArrayList<>(ls));
    ls.clear();

    // right down
    for (int i = move; (i + 1) % 8 != 0 && i <= 54; i += 9) {
      ls.add(i);
    }
    directions.add(new ArrayList<>(ls));
    ls.clear();

    // left down
    for (int i = move; i % 8 != 0 && i <= 55; i += 7) {
      ls.add(i);
    }
    directions.add(new ArrayList<>(ls));
    ls.clear();

    // right up
    for (int i = move; (i + 1) % 8 != 0 && i > 7; i -= 7) {
      ls.add(i);
    }
    directions.add(new ArrayList<>(ls));
    ls.clear();

    int[] cells = board.getCells();
    List<Integer> flips = new ArrayList<>();
    for (List<Integer> direction : directions) {
      if (direction.size() == 0) {
        continue;
      }
      direction.remove(0);

      // When it comes across a other value that isn't player, remove
      List<Integer> flipsRow = new ArrayList<>();

      for (int set : direction) {
        if (cells[set] == EMPTY_CELL) {
          break;
        }

        if (cells[set] != player) {
          flipsRow.add(set);
        }

        if (cells[set] == player) {
          flips.addAll(flipsRow);
          break;
        }
      }
    }

    return flips;
  }

  /**
   * Check wether the board is full.
   *
   * @return if the board is full or not.
   */
  @Override
  public boolean isFull() {
    for (int cell : board.getCells()) {
      if (cell == EMPTY_CELL) {
        return false;
      }
    }
    return true;
  }
}
