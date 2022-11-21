package com.headtrixz.minimax;

/**
 * record to be stored in the transposition table
 *
 * @param value the score of the boarded used as key in the table
 * @param depth the depth the entry was created at
 * @param flag
 */
record TranspositionEntry(int value, int depth, Flags flag) {
  enum Flags {
    EXACT,
    UPPER_BOUND,
    LOWER_BOUND
  }
}
