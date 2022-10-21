package com.headtrixz.controllers;

import com.headtrixz.game.GameBoard;

public class TicTacToeController extends GameController {
    private static final int BOARD_SIZE = 3;

    public TicTacToeController() {
        super(TicTacToeController.BOARD_SIZE);
    }

    @Override
    public GameState getState() {
        if (this.state != null) {
            return this.state;
        }

        if (hasWon(playerOne.getCellState())) {
            return GameState.PLAYER_ONE_WON;
        }

        if (hasWon(playerTwo.getCellState())) {
            return GameState.PLAYER_TWO_WON;
        }

        if (board.isFull()) {
            return GameState.DRAW;
        }

        return GameState.PLAYING;
    }

    private boolean hasWon(GameBoard.CellState player) {
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
