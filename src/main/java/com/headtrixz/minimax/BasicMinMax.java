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
            int score = minMax(clone, Integer.MAX_VALUE, false);
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

    private int minMax(GameModel game, int depth, boolean maxPlayer) {
        GameModel clone = game.clone();
        var validMoves = clone.getValidMoves();

        if (depth == 0 || validMoves.size() == 0) {
            return clone.getScore((maxPlayer) ? clone.getCurrentPlayer() : clone.getOpponent(), 1,
                    1);
        }

        if (maxPlayer) {
            int value = Integer.MIN_VALUE;

            for (int move : validMoves) {
                clone.setMove(move, clone.getCurrentPlayer().getId());
                value = Math.max(value, minMax(clone, depth - 1, false));
            }

            return value;
        }

        // Min player
        int value = Integer.MAX_VALUE;

        for (int move : validMoves) {
            clone.setMove(move, clone.getOpponent().getId());
            value = Math.min(value, minMax(clone, depth - 1, true));
        }

        return value;

    }
}

// function  minimax( node, depth, maximizingPlayer ) is
//     if depth = 0 or node is a terminal node then
//         return the heuristic value of node
//     if maximizingPlayer then
//         value := −∞
//         for each child of node do
//             value := max( value, minimax( child, depth − 1, FALSE ) )
//         return value
//     else (* minimizing player *)
//         value := +∞
//         for each child of node do
//             value := min( value, minimax( child, depth − 1, TRUE ) )
//         return value