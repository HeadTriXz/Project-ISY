package com.headtrixz.minimax;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;

public class BasicMiniMax implements MiniMax {
    private final GameModel baseGame;
    private final Player maxPlayer;

    public BasicMiniMax(GameModel game, Player player) {
        this.baseGame = game;
        this.maxPlayer = player;
    }

    @Override
    public int getMove() {
        return getMove(baseGame.getValidMoves().size());
    }

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

    private int minimax(GameModel game, int depth, Player player) {
        if (depth == 0 || game.getState() != GameModel.GameState.PLAYING) {
            return game.getScore(maxPlayer, depth);
        }

        if (player == maxPlayer) {
            return max(game, depth, player);
        }

        return min(game, depth, player);
    }

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
