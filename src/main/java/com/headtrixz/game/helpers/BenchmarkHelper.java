package com.headtrixz.game.helpers;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.GameModel.GameState;
import com.headtrixz.game.players.Player;

/**
 * Represents a helper class that handles the game logic for the benchmark.
 */
public class BenchmarkHelper extends GameModelHelper{
    private GameModel.GameState state;

    /**
     * Represents a helper class that handles the game logic for the benchmark.
     *
     * @param controller The controller of the game.
     * @param game       The game the helper is for.
     */
    public BenchmarkHelper(GameModel game) {
        super(null, game);
    }

    /**
     * Ends a game when the player forfeits.
     */
    @Override
    public void forfeit() {
        this.state = GameModel.GameState.PLAYER_TWO_WON;
    }

    /**
     * Returns the local player (you).
     *
     * @return The local player.
     */
    @Override
    public Player getLocalPlayer() {
        return game.getPlayer(0);
    }

    /**
     * Returns the current state of the game.
     *
     * @return The current state of the game.
     */
    @Override
    public GameState getState() {
        return state;
    }

    /**
     * Initializes the helper.
     */
    @Override
    public void initialize() { }

    /**
     * Sets the next player and starts a new turn.
     */
    @Override
    public void nextTurn(Player player) { }
}
