package com.headtrixz.game;

import com.headtrixz.game.players.Player;
import com.headtrixz.ui.elements.GameGrid;

/**
 * The interface for game controllers.
 */
public abstract class GameMethods {
    protected GameModel game;
    protected GameGrid gameGrid;

    /**
     * Updates the tiles in the game grid.
     */
    protected void updateGrid() {
        int[] board = game.getBoard().getCells();
        gameGrid.clearBoard(board.length);

        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0) {
                gameGrid.setTile(i, game.getImage(board[i]));
            }
        }
    }

    /**
     * Gets called when a game has ended.
     */
    public abstract void endGame();

    /**
     * Gets called when a set is done on the board by either players.
     *
     * @param move the index of the move the player has done.
     * @param player the player who has set the move.
     */
    public abstract void update(int move, Player player);
}
