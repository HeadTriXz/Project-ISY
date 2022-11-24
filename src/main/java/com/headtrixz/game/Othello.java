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
  private static final int[][] DIRECTIONS = {
          {0, -1},  // UP
          {0, 1},   // DOWN
          {1, 0},   // RIGHT
          {-1, 0},  // LEFT
          {1, -1},  // UP RIGHT
          {-1, -1}, // UP LEFT
          {1, 1},   // DOWN RIGHT
          {-1, 1}   // DOWN LEFT
  };
  private static final String NAME = "Othello";

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
   * Show the tiles that will get fliped to the player when a stone is placed.
   *
   * @param move   the possition on the board.
   * @param player the player who's turn it is.
   * @return A list with the tiles that will be flipped when a move is set.
   */
  public List<Integer> getFlips(int move, int player) {
    final int finalX = move % BOARD_SIZE;
    final int finalY = move / BOARD_SIZE;

    List<Integer> flips = new ArrayList<>();
    for (int[] direction : DIRECTIONS) {
      int x = finalX + direction[0];
      int y = finalY + direction[1];

      while (inBounds(x, y) && board.getMove(x, y) != EMPTY_CELL) {
        if (board.getMove(x, y) == player) {
          while (x - direction[0] != finalX || y - direction[1] != finalY) {
            x -= direction[0];
            y -= direction[1];

            flips.add(x + y * BOARD_SIZE);
          }

          break;
        }

        x += direction[0];
        y += direction[1];
      }
    }

    return flips;
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
   * Get the score for a player by counting the number of cells that are owned by that player.
   *
   * @param player The player to get the score for.
   * @return The number of cells that the player has taken.
   */
  private int getPlayerScore(int player) {
    int score = 0;
    for (int cell : board.getCells()) {
      if (cell == player) {
        score++;
      }
    }
    return score;
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

    if (isPlaying()) {
      return GameState.PLAYING;
    }

    int p1 = getPlayerScore(PLAYER_ONE);
    int p2 = getPlayerScore(PLAYER_TWO);

    if (p1 > p2) {
      return GameState.PLAYER_ONE_WON;
    }

    if (p2 > p1) {
      return GameState.PLAYER_TWO_WON;
    }

    return GameState.DRAW;
  }

  /**
   * Returns a list of all available cells on the board.
   *
   * @return A list of all available cells on the board.
   */
  public List<Integer> getValidMoves() {
    return getValidMoves(getCurrentPlayer().getId());
  }

  /**
   * Returns a list of all available cells on the board.
   *
   * @param player The player to get the valid moves for.
   * @return A list of all available cells on the board.
   */
  public List<Integer> getValidMoves(int player) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < board.getCellCount(); i++) {
      if (isValidMove(i, player)) {
        list.add(i);
      }
    }

    return list;
  }

  /**
   * Returns true if the given move is within the bounds of the board.
   *
   * @param x The x coordinate of the tile to check.
   * @param y The y coordinate of the tile to check.
   * @return Whether the move is in bounds.
   */
  private boolean inBounds(int x, int y) {
    return x >= 0 && x < board.getSize() && y >= 0 && y < board.getSize();
  }

  /**
   * If there are more than one valid moves, the game is still playing.
   *
   * @return Whether the players are still playing.
   */
  private boolean isPlaying() {
    List<Integer> playerOneMoves = getValidMoves(PLAYER_ONE);
    List<Integer> playerTwoMoves = getValidMoves(PLAYER_TWO);

    return playerOneMoves.size() + playerTwoMoves.size() > 0;
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
}
