package com.headtrixz.tictactoe;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.Player;

import static com.headtrixz.game.GameBoard.PLAYER_ONE;
import static com.headtrixz.game.GameBoard.PLAYER_TWO;

public class TicTacToe extends GameModel {
    private static final int BOARD_SIZE = 3;

    public TicTacToe() {
        super(BOARD_SIZE);
    }

    @Override
    public GameState getState() {
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
    public int getScore(Player currentPlayer, int depth) {
        if (getState() == GameState.DRAW || getState() == GameState.PLAYING) return 0;
        if (hasPlayerWon(currentPlayer) && depth == 1) return -2;
        return -1; // player has lost
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
