package com.headtrixz.ui;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameModel.GameState;

class SharedState {
    // A hacky solution for ONE presentatiion, just delete this whole branch
    private static SharedState INSTANCE;

    private SharedState() {

    }

    public static SharedState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SharedState();
        }

        return INSTANCE;
    }

    public GameState winner;
    public GameBoard board;
}
