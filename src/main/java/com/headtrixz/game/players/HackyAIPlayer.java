package com.headtrixz.game.players;

import java.util.List;
import java.util.Random;

import com.headtrixz.game.GameModel;

public class HackyAIPlayer extends Player {
    GameModel game;
    Random rand;

    public HackyAIPlayer(GameModel game, String username) {
        super(username);
        this.game = game;
        this.rand = new Random();
    }

    @Override
    public int getMove() {
        List<Integer> moves = game.getValidMoves();
        if (moves.size() == 0) {
            return -1;
        }

        return moves.get(rand.nextInt(moves.size()));
    }
}
