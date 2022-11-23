package com.headtrixz.game.players;

import com.headtrixz.minimax.MiniMax;
import com.headtrixz.game.GameModel;

public class AiPlayer extends Player {
    private final MiniMax miniMax;

    /**
     * Create a new AI player.
     *
     * @param game The game the player is playing in.
     * @param username The username of the player.
     */
    public AiPlayer(GameModel game, String username) {
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
