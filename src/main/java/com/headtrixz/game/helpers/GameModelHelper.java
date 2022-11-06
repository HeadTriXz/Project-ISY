package com.headtrixz.game.helpers;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;

public interface GameModelHelper {
    void forfeit();
    GameModel.GameState getState();
    void initialize();
    void nextTurn(Player player);
}
