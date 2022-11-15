package com.headtrixz.game;

import com.headtrixz.game.players.Player;

public interface GameMethods {
  void endGame();

  void update(int move, Player player);
}
