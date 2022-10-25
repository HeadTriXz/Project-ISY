package com.headtrixz.game;

public abstract class Player {
    protected int id;
    protected final String username;

    public Player(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract void onTurn();
}
