package com.headtrixz.tictactoe;

import com.headtrixz.MiniMax.MiniMax;
import com.headtrixz.game.Player;

public class TicTacToeAI extends Player {
    private final TicTacToe game;
    private final MiniMax miniMax;

    public TicTacToeAI(TicTacToe game) {
        super("noobmaster69");
        this.game = game;
        this.miniMax = new MiniMax(this.game);
    }

    @Override
    public int getMove() {
//        return miniMax.getMoveIterative(5000);
        return miniMax.getMove();
    }
}
