package com.headtrixz.game;

import com.headtrixz.game.players.Player;

/** The interface for game controllers. */
public interface GameMethods {
  /** Gets called when a game has ended. */
  void endGame();

  /**
   * Gets called when a set is done on the board by either players.
   *
   * @param move the index of the move the player has done.
   * @param player the player who has set the move.
   */
  void update(int move, Player player);
}
