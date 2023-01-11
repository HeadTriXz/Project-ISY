package com.headtrixz.minimax;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;

/**
 * Basic Minimax is a recreation of a simple version of the minimax algorithm.
 */
public class BasicMiniMax implements MiniMax {
    private final GameModel baseGame;
    private final Player maxPlayer;

    /**
     * Create a new BasicMiniMax object.
     */
    public BasicMiniMax(GameModel game, Player player) {
        this.baseGame = game;
        this.maxPlayer = player;
    }

    /**
     * Returns the best move the current player can play based on the current game state.
     */
    @Override
    public int getMove() {
        return getMove(baseGame.getValidMoves().size());
    }

    /**
     * Returns the best move the current player can play based on the current game state.
     *
     * @param maxDepth the max depth the algorithm should search.
     */
    @Override
    public int getMove(int maxDepth) {
        GameModel clone = baseGame.clone();
        Player minPlayer = clone.getOpponent(maxPlayer);

        int bestMove = -1;
        int value = Integer.MIN_VALUE;

        for (int move : clone.getValidMoves()) {
            clone.setMove(move, maxPlayer.getId());
            int score = minimax(clone, maxDepth, minPlayer);
            clone.setMove(move, GameBoard.EMPTY_CELL);

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
     * @param game Current game state.
     * @param depth The max depth the algorithm should search.
     * @param player The player for who to search.
     * @return The best (or worst) value of any board.
     */
    private int minimax(GameModel game, int depth, Player player) {
        if (depth == 0 || game.getState() != GameModel.GameState.PLAYING) {
            return game.getScore(maxPlayer, depth);
        }

        if (player == maxPlayer) {
            return max(game, depth, player);
        }

        return min(game, depth, player);
    }


    /**
     * The maximize calculations.
     *
     * @param game The current game state.
     * @param depth The depth it is allowed to travel.
     * @param player The current player.
     * @return The max value.
     */
    private int max(GameModel game, int depth, Player player) {
        Player opponent = game.getOpponent(player);
        int value = Integer.MIN_VALUE;

        for (int move : game.getValidMoves()) {
            game.setMove(move, player.getId());
            value = Math.max(value, minimax(game, depth - 1, opponent));
            game.setMove(move, GameBoard.EMPTY_CELL);
        }

        return value;
    }

    /**
     * The minimize calculations.
     *
     * @param game The current game state.
     * @param depth The depth it is allowed to travel.
     * @param player The current player.
     * @return The min value.
     */
    private int min(GameModel game, int depth, Player player) {
        Player opponent = game.getOpponent(player);
        int value = Integer.MAX_VALUE;

        for (int move : game.getValidMoves()) {
            game.setMove(move, player.getId());
            value = Math.min(value, minimax(game, depth - 1, opponent));
            game.setMove(move, GameBoard.EMPTY_CELL);
        }

        return value;
    }
}
