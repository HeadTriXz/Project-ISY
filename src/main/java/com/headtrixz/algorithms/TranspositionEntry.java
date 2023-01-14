package com.headtrixz.algorithms;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.players.Player;

/**
 * record to be stored in the transposition table.
 *
 * @param value the score of the boarded used as key in the table
 * @param depth the depth the entry was created at
 * @param flag the flag of the entry
 */
record TranspositionEntry(int value, int depth, Flags flag) {
    enum Flags {
        EXACT,
        UPPER_BOUND,
        LOWER_BOUND
    }

    public static int createHash(GameBoard gameBoard, Player player) {
        int hash = gameBoard.hashCode() * 10;
        hash += player.getId();
        return hash;
    }
}
