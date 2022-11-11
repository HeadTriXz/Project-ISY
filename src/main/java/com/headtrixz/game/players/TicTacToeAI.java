package com.headtrixz.game.players;

import com.headtrixz.minimax.MiniMax;
import com.headtrixz.game.TicTacToe;

public class TicTacToeAI extends Player {
    private final MiniMax miniMax;

    public TicTacToeAI(TicTacToe game, String username) {
        super(username);
        this.miniMax = new MiniMax(game);
    }

    @Override
    public int getMove() {
        // return miniMax.getMoveIterative(5000);
        return miniMax.getMove();
    }
}
