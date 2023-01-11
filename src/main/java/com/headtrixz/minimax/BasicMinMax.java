package com.headtrixz.minimax;

import com.headtrixz.game.GameModel;

public class BasicMinMax implements MiniMax {
    private final GameModel baseGame;

    public BasicMinMax(GameModel baseGame) {
        this.baseGame = baseGame;
    }

    @Override
    public int getMove() {
        GameModel clone = baseGame.clone();
        int bestMove = -1;
        int value = Integer.MIN_VALUE;

        for (int move : baseGame.getValidMoves()) {
            clone.setMove(move, clone.getCurrentPlayer().getId());
            int score = minMax(clone, Integer.MAX_VALUE - 1, false, Integer.MAX_VALUE);
            clone.setMove(move, 0);
            if (value < score) {
                value = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    @Override
    public int getMove(int maxDepth) {
        return 0;
    }

    private int minMax(GameModel game, int depth, boolean maxPlayer, int maxdepth) {
        // GameModel clone = game.clone();
        var validMoves = game.getValidMoves();
        var hasWon = game.hasPlayerWon((maxPlayer) ? game.getCurrentPlayer() : game.getOpponent());

        if (depth == 0 || validMoves.size() == 0 || hasWon) {
            return game.getScore((maxPlayer) ? game.getCurrentPlayer() : game.getOpponent(), depth,
                    0);
        }

        if (maxPlayer) {
            int value = Integer.MIN_VALUE;

            for (int move : validMoves) {
                game.setMove(move, game.getCurrentPlayer().getId());
                value = Math.max(value, minMax(game, depth - 1, false, maxdepth));
                game.setMove(move, 0);
            }

            return value;
        }

        // Min player
        int value = Integer.MAX_VALUE;

        for (int move : validMoves) {
            game.setMove(move, game.getOpponent().getId());
            value = Math.min(value, minMax(game, depth - 1, true, maxdepth));
            game.setMove(move, 0);
        }

        return value;

    }
}
