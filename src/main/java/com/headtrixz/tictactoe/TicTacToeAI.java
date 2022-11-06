package com.headtrixz.tictactoe;

import com.headtrixz.MiniMax.MiniMax;
import com.headtrixz.game.Player;

public class TicTacToeAI extends Player {
    private final TicTacToe game;

    public TicTacToeAI(TicTacToe game, String username) {
        super(username);
        this.game = game;
    }

    @Override
    public int getMove() {
        return MiniMax.getMove(game);
    }
}
