package com.headtrixz.game.helpers;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;

/**
 * The interface for the GameModel helpers.
 */
public interface GameModelHelper {
    /**
     * Ends the game when the player forfeits.
     */
    void forfeit();

    /**
     * Returns the current state of the game.
     *
     * @return The current state of the game.
     */
    GameModel.GameState getState();

    /**
     * Initializes the helper.
     */
    void initialize();

    /**
     * Tells the player it's their turn and handles their move.
     *
     * @param player The player whose turn it is.
     */
    void nextTurn(Player player);
}
