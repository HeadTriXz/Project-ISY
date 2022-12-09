package com.headtrixz.game.helpers;

import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;

/**
 * Base for the GameModel helpers.
 */
public abstract class GameModelHelper {
    protected GameMethods controller;
    protected GameModel game;

    /**
     * Represents a helper class that handles the game logic for an offline game.
     *
     * @param game The game the helper is for.
     */
    protected GameModelHelper(GameMethods controller, GameModel game) {
        this.controller = controller;
        this.game = game;
    }

    /**
     * Creates a clone of the helper.
     *
     * @param game The game the helper is for.
     * @return A clone of the helper.
     */
    public GameModelHelper clone(GameModel game) {
        try {
            return this
                .getClass()
                .getDeclaredConstructor(GameMethods.class, GameModel.class)
                .newInstance(controller, game);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Ends the game when the player forfeits.
     */
    public abstract void forfeit();

    /**
     * Returns the local player (you).
     *
     * @return The local player.
     */
    public abstract Player getLocalPlayer();

    /**
     * Returns the current state of the game.
     *
     * @return The current state of the game.
     */
    public abstract GameModel.GameState getState();

    /**
     * Initializes the helper.
     */
    public abstract void initialize();

    /**
     * Tells the player it's their turn and handles their move.
     *
     * @param player The player whose turn it is.
     */
    public abstract void nextTurn(Player player);
}
