package com.headtrixz.game.players;

import com.headtrixz.game.GameModel;

public class HumanPlayer extends Player {
    private final GameModel game;

    /**
     * A new human player.
     *
     * @param game The game the player is playing at.
     * @param username The name of the player.
     */
    public HumanPlayer(GameModel game, String username) {
        super(username);
        this.game = game;
    }

    /**
     * Get the best move from the UI.
     *
     * @return the move the player set.
     */
    @Override
    public int getMove() {
        if (game.getValidMoves().size() == 0) {
            return -1;
        }

        while (game.getState() == GameModel.GameState.PLAYING) {
            int move = game.getGuiMove();
            if (move != -1) {
                game.setGuiMove(-1);
                return move;
            }
        }

        return -1;
    }
}
