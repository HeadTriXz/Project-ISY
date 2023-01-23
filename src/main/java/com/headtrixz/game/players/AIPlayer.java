package com.headtrixz.game.players;

import com.headtrixz.algorithms.MiniMax;
import com.headtrixz.factory.MiniMaxFactory;
import com.headtrixz.factory.MiniMaxFactory.MiniMaxType;
import com.headtrixz.game.GameModel;

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
        this.miniMax = MiniMaxFactory.createMiniMax(MiniMaxType.MiniMaxOptimized, game);
    }

    public AIPlayer(GameModel game, String username, MiniMaxType algorithm) {
        super(username);
        this.miniMax = MiniMaxFactory.createMiniMax(algorithm, game);
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

    @Override
    public int getMove(int depth) {
        return miniMax.getMove(depth);
    }
}
