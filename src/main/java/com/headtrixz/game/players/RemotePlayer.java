package com.headtrixz.game.players;

/**
 * Represents a remote player.
 */
public class RemotePlayer extends Player {
    /**
     * Create a new remote player instance.
     * Make sure that the name is the same as on the server, otherwise
     * everything will break and burn to the ground. But if you do decide to
     * burn it all down make sure to take Java and PHP with you.
     *
     * @param username the username of the other player.
     */
    public RemotePlayer(String username) {
        super(username);
    }

    /**
     * Gets the move of remote player.
     *
     * @return -1, just -1.
     */
    @Override
    public int getMove() {
        return -1;
    }
}
