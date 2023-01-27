package com.headtrixz.game.players;

import com.headtrixz.game.GameModel;
import java.util.List;
import java.util.Random;

/**
 * A simple hacky Ai.
 */
public class HackyAIPlayer extends Player {
    GameModel game;
    Random rand;

    /**
     * Creates a new AI player.
     *
     * @param game the gameboard.
     * @param username the username of the ai.
     */
    public HackyAIPlayer(GameModel game, String username) {
        super(username);
        this.game = game;
        this.rand = new Random();
    }

    @Override
    public int getMove() {
        try {
            Thread.sleep(750);
        } catch (Exception e) {
            System.out.println("Could not sleep the thread.");
            e.printStackTrace();
        }

        List<Integer> moves = game.getValidMoves(getId());
        if (moves.size() == 0) {
            return -1;
        }

        return moves.get(rand.nextInt(moves.size()));
    }
}
