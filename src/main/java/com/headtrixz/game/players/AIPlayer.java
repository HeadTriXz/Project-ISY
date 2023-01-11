package com.headtrixz.game.players;

import com.headtrixz.factory.MiniMaxFactory;
import com.headtrixz.game.GameModel;
import com.headtrixz.minimax.MiniMax;

/**
 * The AI player that makes use of minimax.
 */
public class AIPlayer extends Player {
    private final MiniMax miniMax;

    /**
     * Create a new AI player.
     *
     * @param game The game the player is playing in.
     * @param username The username of the player.
     */
    public AIPlayer(GameModel game, String username) {
        super(username);
        this.miniMax = MiniMaxFactory.createMiniMax(MiniMaxFactory.MiniMaxType.MiniMax, game);
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
