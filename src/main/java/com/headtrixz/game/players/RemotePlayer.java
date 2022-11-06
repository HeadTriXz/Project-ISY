package com.headtrixz.game.players;

public class RemotePlayer extends Player {
    public RemotePlayer(String username) {
        super(username);
    }

    @Override
    public int getMove() {
        return -1;
    }
}
