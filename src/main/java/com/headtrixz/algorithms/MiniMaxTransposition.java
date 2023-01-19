package com.headtrixz.algorithms;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic Minimax is a recreation of a simple version of the minimax algorithm.
 */
public class MiniMaxTransposition implements MiniMax {
    private final GameModel baseGame;
    private final Map<Integer, TranspositionEntry> transpositionTable;
    /**
     * Create a new BasicMiniMax object.
     */
    public MiniMaxTransposition(GameModel game) {
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
     * @param maxDepth the max depth the algorithm should search.
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

            int score = minimax(clone, maxDepth, minPlayer);
            if (score > value) {
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
     * @param player The player for who to search.
     * @return The best (or worst) value of any board.
     */
    private int minimax(GameModel game, int depth, Player player) {
        game.setCurrentPlayer(player);
        Player maxPlayer = baseGame.getCurrentPlayer();

        int ttKey = TranspositionEntry.createHash(game.getBoard(), player);
        TranspositionEntry ttEntry = transpositionTable.get(ttKey);

        if (ttEntry != null && ttEntry.depth() >= depth) {
            return ttEntry.value();
        }

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

            int score = minimax(clone, depth - 1, opponent);
            maxScore = player == maxPlayer
                ? Math.max(maxScore, score)
                : Math.min(maxScore, score);
        }

        transpositionTable.put(ttKey,
            new TranspositionEntry(maxScore, depth, TranspositionEntry.Flags.EXACT));

        return maxScore;
    }
}
