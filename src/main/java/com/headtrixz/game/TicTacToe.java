package com.headtrixz.game;

import com.headtrixz.game.players.Player;

import static com.headtrixz.game.GameBoard.PLAYER_ONE;
import static com.headtrixz.game.GameBoard.PLAYER_TWO;

/**
 * Represents a game of Tic Tac Toe.
 */
public class TicTacToe extends GameModel {
    private static final int BOARD_SIZE = 3;
    private static final String NAME = "Tic-Tac-Toe";

    /**
     * Represents a game of Tic Tac Toe.
     */
    public TicTacToe() {
        super(NAME, BOARD_SIZE);
    }

    /**
     * Returns the current state of the game.
     *
     * @return The current state of the game.
     */
    @Override
    public GameState getState() {
        GameState state = helper.getState();
        if (state != null) {
            return state;
        }

        if (hasWon(PLAYER_ONE)) {
            return GameState.PLAYER_ONE_WON;
        }

        if (hasWon(PLAYER_TWO)) {
            return GameState.PLAYER_TWO_WON;
        }

        if (board.isFull()) {
            return GameState.DRAW;
        }

        return GameState.PLAYING;
    }

    /**
     * get the score of the game in its current state. the scoring is
     * -2 if current player has won, -1 if current player has lost,
     * 0 if game is still going or ended in draw.
     *
     * @return the score of the board
     */
    public int getScore(Player currentPlayer, int depth, int maxDepth) {
        double depthPenalty = depth / (double) maxDepth;

        switch (getState()) {
            case DRAW, PLAYING -> {
                if (getBoard().getMove(1, 1) == currentPlayer.getId()) {
                    return (int) (40 * depthPenalty);
                }
                return 0;
            }
            case PLAYER_ONE_WON, PLAYER_TWO_WON -> {
                return (int) ((hasPlayerWon(currentPlayer) ? 80 : -80) * depthPenalty);
            }
            default -> {
                System.out.println("there was a error. unknown state");
                return 0;
            }
        }
    }

    /**
     * If the player has three of their moves in a row, column, or diagonal, then they have won.
     *
     * @param player The player who is being checked for a win.
     * @return Whether the player has won.
     */
    private boolean hasWon(int player) {
        return player == board.getMove(0, 0) && player == board.getMove(1, 0) && player == board.getMove(2, 0)
                || player == board.getMove(0, 1) && player == board.getMove(1, 1) && player == board.getMove(2, 1)
                || player == board.getMove(0, 2) && player == board.getMove(1, 2) && player == board.getMove(2, 2)

                || player == board.getMove(0, 0) && player == board.getMove(0, 1) && player == board.getMove(0, 2)
                || player == board.getMove(1, 0) && player == board.getMove(1, 1) && player == board.getMove(1, 2)
                || player == board.getMove(2, 0) && player == board.getMove(2, 1) && player == board.getMove(2, 2)

                || player == board.getMove(0, 0) && player == board.getMove(1, 1) && player == board.getMove(2, 2)
                || player == board.getMove(0, 2) && player == board.getMove(1, 1) && player == board.getMove(2, 0);
    }
}
