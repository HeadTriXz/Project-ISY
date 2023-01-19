package com.headtrixz.algorithms;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import java.util.Arrays;

/**
 * Represents the MiniMax algorithm with Alpha-beta pruning.
 */
public class MiniMaxAlphaBeta implements MiniMax {
    private final GameModel baseGame;

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
        Player minPlayer = baseGame.getOpponent(maxPlayer);

        int bestMove = -1;
        int value = Integer.MIN_VALUE;

        for (int move : baseGame.getValidMoves()) {
            GameModel clone = baseGame.clone();
            clone.setMove(move, maxPlayer.getId());

            int score = minimax(clone, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, minPlayer);
            if (score >= value || bestMove == -1) {
                value = score;
                bestMove = move;
            }
        }

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
    private int minimax(GameModel game, int depth, int alpha, int beta, Player player) {
        game.setCurrentPlayer(player);

        Player maxPlayer = baseGame.getCurrentPlayer();
        if (depth == 0 || game.getState() != GameModel.GameState.PLAYING) {
            return game.getScore(maxPlayer, depth);
        }

        int maxScore = player == maxPlayer
                ? Integer.MIN_VALUE
                : Integer.MAX_VALUE;

        Player opponent = game.getOpponent(player);
        for (int move : game.getValidMoves()) {
            GameModel clone = game.clone();
            clone.setMove(move, player.getId());

            int score = minimax(clone, depth - 1, alpha, beta, opponent);
            if (player == maxPlayer) {
                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, score);
            } else {
                maxScore = Math.min(maxScore, score);
                beta = Math.min(beta, score);
            }

            if (alpha >= beta) {
                break;
            }
        }

        return maxScore;
    }
}
