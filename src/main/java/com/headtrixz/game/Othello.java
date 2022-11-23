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
      if (isValidMove(i, getCurrentPlayer().getId())) {
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
    // int leftCell = move > 1 ? cells[move - 1] : -1;
    // int rightCell = (move + 1) % 8 > 0 ? cells[move + 1] : -1;
    // int diagLeft = (move % 8 > 0 && move > 8) ? cells[move - 9] : -1;
    // int diagRight = (move % 7 > 0 && move <= 54) ? cells[move + 9] : -1;
    // if (leftCell != -1 && leftCell != EMPTY_CELL && leftCell != getCurrentPlayer().getId()) {
    //   return true;
    // }
    // if (leftCell != -1 && rightCell != EMPTY_CELL && rightCell != getCurrentPlayer().getId()) {
    //   return true;
    // }
    // if (leftCell != -1 && diagLeft != EMPTY_CELL && diagLeft != getCurrentPlayer().getId()) {
    //   return true;
    // }
    // if (leftCell != -1 && diagRight != EMPTY_CELL && diagRight != getCurrentPlayer().getId()) {
    //   return true;
    // }
    boolean otherStoneFound = false;
    for (int i = move; i > 7; i -= 8) {
      if (cells[i] != EMPTY_CELL && cells[i] != player) {
        otherStoneFound = true;
      }
      if (cells[i] == player && otherStoneFound) {
        return true;
      }
    }
    otherStoneFound = false;
    for (int i = move; i % 8 != 0 && i > 0; i--) {
      if (cells[i] != EMPTY_CELL && cells[i] != player) {
        otherStoneFound = true;
      }
      if (cells[i] == player && otherStoneFound) {
        return true;
      }
    }
    otherStoneFound = false;
    for (int i = move; i <= 55; i += 8) {
      if (cells[i] != EMPTY_CELL && cells[i] != player) {
        otherStoneFound = true;
      }
      if (cells[i] == player && otherStoneFound) {
        return true;
      }
    }
    otherStoneFound = false;
    for (int i = move; i % 8 != 0 && i > 0; i++) {
      if (cells[i] != EMPTY_CELL && cells[i] != player) {
        otherStoneFound = true;
      }
      if (cells[i] == player && otherStoneFound) {
        return true;
      }
    }
    otherStoneFound = false;
    for (int i = move; i % 8 != 0 && i > 8; i -= 9) {
      if (cells[i] != EMPTY_CELL && cells[i] != player) {
        otherStoneFound = true;
      }
      if (cells[i] == player && otherStoneFound) {
        return true;
      }
    }
    otherStoneFound = false;
    for (int i = move; i + 1 % 8 != 0 && i <= 54; i += 9) {
      if (cells[i] != EMPTY_CELL && cells[i] != player) {
        otherStoneFound = true;
      }
      if (cells[i] == player && otherStoneFound) {
        return true;
      }
    }
    otherStoneFound = false;
    for (int i = move; i + 1 % 8 != 0 && i <= 55; i += 7) {
      if (cells[i] != EMPTY_CELL && cells[i] != player) {
        otherStoneFound = true;
      }
      if (cells[i] == player && otherStoneFound) {
        return true;
      }
    }
    otherStoneFound = false;
    for (int i = move; i % 8 != 0 && i > 7; i -= 7) {
      if (cells[i] != EMPTY_CELL && cells[i] != player) {
        otherStoneFound = true;
      }
      if (cells[i] == player && otherStoneFound) {
        return true;
      }
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
