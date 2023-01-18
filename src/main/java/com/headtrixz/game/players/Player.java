package com.headtrixz.game.players;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Represents a player.
 */
public abstract class Player {
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    protected int id;
    protected final String username;

    /**
     * Create a new player.
     *
     * @param username the name of the player.
     */
    public Player(String username) {
        this.username = username;
    }

    /**
     * Get the id of the player.
     *
     * @return id of the player.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the username of the player.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Send the next player a message that it is their turn to play.
     *
     * @param callback The callback to be called when the player calculated it's move.
     */
    public void onTurn(Consumer<Integer> callback) {
        EXECUTOR.execute(() -> callback.accept(getMove()));
    }

    /**
     * Set the ID of the player.
     *
     * @param id the id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Request a move from the player.
     *
     * @return the move the player has chosen, or -1.
     */
    public abstract int getMove();

    /**
     * Request a move from the player with a depth.
     *
     * @return the move the player has chosen, or -1.
     */
    public int getMove(int depth) {
        return -1;
    }
}
