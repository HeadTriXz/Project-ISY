package com.headtrixz.minimax;

/**
 * Represents a MiniMax algorithm.
 */
public interface MiniMax {
    /**
     * Get the best next move for any given player based on the current game state.
     * WARNING: This will use the amount of valid moves as max depth.
     *
     * @return The best move.
     */
    int getMove();

    /**
     * Get the best next move for any given player based on the current game state.
     *
     * @param maxDepth The max depth the algorithm will go.
     * @return The best move.
     */
    int getMove(int maxDepth);
}
