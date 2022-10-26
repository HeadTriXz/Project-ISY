package com.headtrixz.game;

public class HumanPlayer extends Player {
    private final GameModel game;

    public HumanPlayer(GameModel game, String username) {
        super(username);
        this.game = game;
    }

    @Override
    public int getMove() {
        while (true) {
            int move = game.getGuiMove();
            if (move != -1) {
                game.setGuiMove(-1);
                return move;
            }
        }
    }
}
