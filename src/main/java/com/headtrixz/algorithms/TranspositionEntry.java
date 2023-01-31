package com.headtrixz.algorithms;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.players.Player;

/**
 * Record to be stored in the transposition table.
 *
 * @param value The score of the boarded used as key in the table.
 * @param depth The depth the entry was created at.
 * @param flag  The flag of the entry.
 */
public record TranspositionEntry(float value, int depth, Flags flag) {
    enum Flags {
        EXACT,
        UPPER_BOUND,
        LOWER_BOUND
    }

    public static int createHash(GameBoard gameBoard, Player player) {
        return (gameBoard.hashCode() * 10) + player.getId();
    }
}
