package com.headtrixz.tictactoe;

import com.headtrixz.game.Player;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToeAI extends Player {
    private final TicTacToe game;

    public TicTacToeAI(TicTacToe game) {
        super("noobmaster69");
        this.game = game;
    }

    @Override
    public int getMove() {
        ArrayList<Integer> moves = game.getBoard().getValidMoves();

        Random random = new Random();
        return moves.get(random.nextInt(moves.size()));
    }
}
