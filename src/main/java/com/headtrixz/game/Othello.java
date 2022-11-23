package com.headtrixz.game;

import static com.headtrixz.game.GameBoard.*;

import com.headtrixz.game.players.Player;
import java.util.ArrayList;
import java.util.List;

/** Represents a game of Othello. */
public class Othello extends GameModel {
  private static final int BOARD_SIZE = 8;
  private static final String NAME = "OTHELLO";

  /** Represents a game of Othello. */
  public Othello() {
    super(NAME, BOARD_SIZE);
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
   * get the score of the game in its current state. the scoring is -2 if current player has won, -1
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
      if (board.getMove(i) == EMPTY_CELL) {
        list.add(i);
      }
    }

    return list;
  }

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
  public boolean isValidMove(int move) {
    return validMoveLogic(move, getCurrentPlayer().getId());
  }

  public boolean isValidMove(int move, int player) {
    return validMoveLogic(move, player);
  }

  private boolean validMoveLogic(int move, int player) {
    if (move < 0) {
      return false;
    }
    int[] cells = board.getCells();
    int leftCell = move > 1 ? cells[move - 1] : -1;
    int rightCell = (move + 1) % 8 > 0 ? cells[move + 1] : -1;
    int diagLeft = (move % 8 > 0 && move > 8) ? cells[move - 9] : -1;
    int diagRight = (move % 7 > 0 && move <= 54) ? cells[move + 9] : -1;
    if (leftCell != -1 && leftCell != getCurrentPlayer().getId()) {
      return true;
    }
    if (rightCell != -1 && rightCell != getCurrentPlayer().getId()) {
      return true;
    }
    if (diagLeft != -1 && diagLeft != getCurrentPlayer().getId()) {
      return true;
    }
    if (diagRight != -1 && diagRight != getCurrentPlayer().getId()) {
      return true;
    }
    return false;
  }

  public void setMove(int move, int player) {
    board.setMove(move, player);
  }

  public boolean isFull() {
    for (int cell : board.getCells()) {
      if (cell == EMPTY_CELL) {
        return false;
      }
    }
    return true;
  }
}
