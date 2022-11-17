package com.headtrixz.game;

import com.headtrixz.game.players.Player;

import static com.headtrixz.game.GameBoard.PLAYER_ONE;
import static com.headtrixz.game.GameBoard.PLAYER_TWO;

public class TicTacToe extends GameModel {
    private static final int BOARD_SIZE = 3;
    private static final String NAME = "Tic-Tac-Toe";

    public TicTacToe() {
        super(NAME, BOARD_SIZE);
    }

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
     * -2 if current player has won, -1 if current player has lost, 0 if game is still going or ended in draw
     *
     * @return the score of the board
     */
    public int getScore(Player currentPlayer, int depth, int colour) {
        if (getState() != GameModel.GameState.PLAYING) {
            // check if the current player has won in 1 move
            if (hasPlayerWon(currentPlayer) && depth == 1) {
                return -2;
            }
            return -1;
        }

        return 0;
    }

    public int getMaxScore() {
        return 1000;
    }

    public int getMinScore() {
        return -1000;
    }

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
