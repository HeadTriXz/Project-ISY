package com.headtrixz.game.players;

import com.headtrixz.game.TicTacToe;
import com.headtrixz.minimax.MiniMax;

public class TicTacToeAI extends Player {
  private final MiniMax miniMax;

  /**
   * Create a new Tic Tac Toe AI.
   *
   * @param game The game the player is playing in.
   * @param username The username of the player.
   */
  public TicTacToeAI(TicTacToe game, String username) {
    super(username);
    this.miniMax = new MiniMax(game);
  }

  /**
   * Get the Best Move™️ from the Minimax algorithm.
   *
   * @return the best possible move.
   */
  @Override
  public int getMove() {
    return miniMax.getMove();
  }
}
