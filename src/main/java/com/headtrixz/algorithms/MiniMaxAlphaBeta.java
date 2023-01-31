package com.headtrixz.algorithms;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import java.util.List;

/**
 * Represents the MiniMax algorithm with Alpha-beta pruning.
 */
public class MiniMaxAlphaBeta implements MiniMax {
    private final GameModel baseGame;
    private Long endTime;
    private boolean hasTimedOut = false;

    /**
     * Represents the MiniMax algorithm with Alpha-beta pruning.
     *
     * @param game The game the AI is currently playing in.
     */
    public MiniMaxAlphaBeta(GameModel game) {
        this.baseGame = game;
    }

    /**
     * Returns the best move the current player can play based on the current game state.
     */
    @Override
    public int getMove() {
        return getMove(baseGame.getBoard().getCellCount());
    }

    /**
     * Returns the best move the current player can play based on the current game state.
     *
     * @param maxDepth The max depth the algorithm should search.
     */
    @Override
    public int getMove(int maxDepth) {
        Player maxPlayer = baseGame.getCurrentPlayer();
        Player minPlayer = baseGame.getOpponent();

        int bestMove = -1;
        float value = Integer.MIN_VALUE;

        for (int move : baseGame.getValidMoves(maxPlayer.getId())) {
            GameModel clone = baseGame.clone();
            clone.setMove(move, maxPlayer.getId());

            float score = minimax(clone, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, minPlayer);
            if (score > value || bestMove == -1) {
                value = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    /**
     * Returns the best move the current player can play based on the current game state.
     *
     * @param timeout The maximum amount of time in milliseconds to spend searching.
     * @return The best move of the board.
     */
    public int iterativeDeepening(int timeout) {
        endTime = System.currentTimeMillis() + timeout;

        Player maxPlayer = baseGame.getCurrentPlayer();
        Player minPlayer = baseGame.getOpponent();

        int bestMove = -1;
        List<Integer> moves = baseGame.getValidMoves(maxPlayer.getId());

        outer: for (int d = 1; d < baseGame.getBoard().getCellCount(); d++) {
            int tempMove = -1;
            float maxScore = Integer.MIN_VALUE;

            for (int move : moves) {
                if (System.currentTimeMillis() >= endTime) {
                    break outer;
                }

                GameModel clone = baseGame.clone();
                clone.setMove(move, maxPlayer.getId());

                float score = minimax(clone, d, Integer.MIN_VALUE, Integer.MAX_VALUE, minPlayer);
                if (score > maxScore) {
                    maxScore = score;
                    tempMove = move;
                }
            }

            if (!hasTimedOut || bestMove == -1) {
                bestMove = tempMove;
            }
        }

        endTime = null;
        hasTimedOut = false;

        return bestMove;
    }

    /**
     * The minimax algorithm.
     *
     * @param game Current game state.
     * @param depth The max depth the algorithm should search.
     * @param alpha The alpha value used in the alpha-beta pruning algorithm. It represents the
     *     maximum value of the best option found so far for the maximizing player.
     * @param beta The beta value used in the alpha-beta pruning algorithm. It represents the
     *     minimum value of the best option found so far for the minimizing player.
     * @param player The player for who to search.
     * @return The best (or worst) value of any board.
     */
    private float minimax(GameModel game, int depth, float alpha, float beta, Player player) {
        if (endTime != null && System.currentTimeMillis() >= endTime) {
            hasTimedOut = true;
            return Integer.MIN_VALUE;
        }

        Player maxPlayer = baseGame.getCurrentPlayer();

        List<Integer> moves = game.getValidMoves(player.getId());
        if (depth == 0 || moves.size() == 0 || game.getState() != GameModel.GameState.PLAYING) {
            return game.getScore(maxPlayer, depth);
        }

        float maxScore = player == maxPlayer
                ? Integer.MIN_VALUE
                : Integer.MAX_VALUE;

        Player opponent = game.getOpponent(player);
        for (int move : moves) {
            GameModel clone = game.clone();
            clone.setMove(move, player.getId());

            float score = minimax(clone, depth - 1, alpha, beta, opponent);
            if (player == maxPlayer) {
                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, maxScore);
            } else {
                maxScore = Math.min(maxScore, score);
                beta = Math.min(beta, maxScore);
            }

            if (beta <= alpha) {
                break;
            }
        }

        return maxScore;
    }
}
