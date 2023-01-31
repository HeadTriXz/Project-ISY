package com.headtrixz.algorithms;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the MiniMax algorithm with Alpha-beta pruning and Transposition Tables.
 */
public class MiniMaxOptimized implements MiniMax {
    private final GameModel baseGame;
    private final Map<Integer, TranspositionEntry> transpositionTable;
    private Long endTime;
    private boolean hasTimedOut = false;

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
     * @param game   Current game state.
     * @param depth  The max depth the algorithm should search.
     * @param alpha  The alpha value used in the alpha-beta pruning algorithm. It represents the
     *               maximum value of the best option found so far for the maximizing player.
     * @param beta   The beta value used in the alpha-beta pruning algorithm. It represents the
     *               minimum value of the best option found so far for the minimizing player.
     * @param player The player for who to search.
     * @return The best (or worst) value of any board.
     */
    private float minimax(GameModel game, int depth, float alpha, float beta, Player player) {
        if (endTime != null && System.currentTimeMillis() >= endTime) {
            hasTimedOut = true;
            return Integer.MIN_VALUE;
        }

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

            if (beta <= alpha) {
                return ttEntry.value();
            }
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

        TranspositionEntry.Flags ttFlag = TranspositionEntry.Flags.EXACT;
        if (maxScore <= alpha) {
            ttFlag = TranspositionEntry.Flags.UPPER_BOUND;
        }
        if (maxScore >= beta) {
            ttFlag = TranspositionEntry.Flags.LOWER_BOUND;
        }

        transpositionTable.put(ttKey, new TranspositionEntry(maxScore, depth, ttFlag));

        return maxScore;
    }
}
