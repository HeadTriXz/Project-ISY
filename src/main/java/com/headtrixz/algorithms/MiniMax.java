package com.headtrixz.algorithms;

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

    /**
     * Returns the best move the current player can play based on the current game state.
     *
     * @param timeout The maximum amount of time in milliseconds to spend searching.
     * @return The best move of the board.
     */
    int iterativeDeepening(int timeout);
}
