package com.headtrixz.game;

import com.headtrixz.game.players.Player;

public interface GameCommands {
    void endGame();

    void update(int move, Player playerId);
}
