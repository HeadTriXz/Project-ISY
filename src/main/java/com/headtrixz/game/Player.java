package com.headtrixz.game;

public abstract class Player {
    private final int id;
    private final String username;

    public Player(String username, int id) {
        this.id = id;
        this.username = username;
    }

    public GameBoard.CellState getCellState() {
        return id == 1 ? GameBoard.CellState.PLAYER_ONE : GameBoard.CellState.PLAYER_TWO;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
