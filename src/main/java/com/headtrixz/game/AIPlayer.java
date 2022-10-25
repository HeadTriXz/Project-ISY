package com.headtrixz.game;

import com.headtrixz.controllers.GameController;
import com.headtrixz.models.GameModel;

import java.util.ArrayList;
import java.util.Random;

public class AIPlayer extends Player {
    private final GameModel game;

    public AIPlayer(GameModel game) {
        super("noobmaster69");
        this.game = game;
    }

    @Override
    public void onTurn() {
        ArrayList<Integer> moves = game.getBoard().getValidMoves();

        Random random = new Random();
        int move = moves.get(random.nextInt(moves.size()));

        game.setMove(move, id);
    }
}
