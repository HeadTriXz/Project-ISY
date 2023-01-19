package com.headtrixz.algorithms;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the MiniMax algorithm with Alpha-beta pruning and Transposition Tables.
 */
public class MiniMaxOptimized implements MiniMax {
    private final GameModel baseGame;
    private final Map<Integer, TranspositionEntry> transpositionTable;

    /**
     * Represents the MiniMax algorithm with Alpha-beta pruning and Transposition Tables.
     *
     * @param game The game the AI is currently playing in.
     */
    public MiniMaxOptimized(GameModel game) {
        this.baseGame = game;
        this.transpositionTable = new HashMap<>();
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
            if (score > value || bestMove == -1) {
                value = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    /**
     * The minimax algorithm.
     *
     * @param game   Current game state.
     * @param depth  The max depth the algorithm should search.
     * @param alpha  The alpha value used in the alpha-beta pruning algorithm. It represents the
     *               maximum value of the best option found so far for the maximizing player.
     * @param beta   The beta value used in the alpha-beta pruning algorithm. It represents the
     *               minimum value of the best option found so far for the minimizing player.
     * @param player The player for who to search.
     * @return The best (or worst) value of any board.
     */
    private int minimax(GameModel game, int depth, int alpha, int beta, Player player) {
        final int oldAlpha = alpha;

        game.setCurrentPlayer(player);
        Player maxPlayer = baseGame.getCurrentPlayer();

        int ttKey = TranspositionEntry.createHash(game.getBoard(), player);
        TranspositionEntry ttEntry = transpositionTable.get(ttKey);

        if (ttEntry != null && ttEntry.depth() >= depth) {
            switch (ttEntry.flag()) {
                case EXACT -> {
                    return ttEntry.value();
                }
                case LOWER_BOUND -> alpha = Math.max(alpha, ttEntry.value());
                case UPPER_BOUND -> beta = Math.min(beta, ttEntry.value());
                default -> throw new IllegalStateException("Invalid flag");
            }

            if (alpha >= beta) {
                return ttEntry.value();
            }
        }

        if (depth == 0 || game.getState() != GameModel.GameState.PLAYING) {
            return game.getScore(player, depth);
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

        TranspositionEntry.Flags ttFlag = TranspositionEntry.Flags.EXACT;
        if (maxScore < oldAlpha) {
            ttFlag = TranspositionEntry.Flags.UPPER_BOUND;
        } else if (maxScore > beta) {
            ttFlag = TranspositionEntry.Flags.LOWER_BOUND;
        }

        transpositionTable.put(ttKey, new TranspositionEntry(maxScore, depth, ttFlag));

        return maxScore;
    }
}
