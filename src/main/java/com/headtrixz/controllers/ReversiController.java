package com.headtrixz.controllers;

public class ReversiController extends GameController {
    private static final int BOARD_SIZE = 8;

    public ReversiController() {
        super(ReversiController.BOARD_SIZE);
    }

    @Override
    public GameState getState() {
        return null;
    }
}
