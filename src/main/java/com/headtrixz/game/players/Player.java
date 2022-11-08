package com.headtrixz.game.players;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public abstract class Player {
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

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

    public void onTurn(Consumer<Integer> callback) {
        EXECUTOR.execute(() -> callback.accept(getMove()));
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract int getMove();
}
