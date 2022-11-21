package com.headtrixz.game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a game board.
 */
public class GameBoard {
    public static final int EMPTY_CELL = 0;
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;

    private int[] cells;
    private final int size;

    public void setCells(int[] cells ) {
        this.cells = cells;
    }

    /**
     * Represents a game board.
     *
     * @param size The size of the game board.
     */
    public GameBoard(int size) {
        this.size = size;
        this.cells = new int[size * size];
        clear();
    }

    /**
     * Clears the game board.
     */
    public void clear() {
        Arrays.fill(cells, EMPTY_CELL);
    }

    /**
     * Returns a copy of the current game board.
     *
     * @return A copy of the current GameBoard object.
     */
    @Override
    public GameBoard clone() {
        GameBoard board = new GameBoard(size);
        board.cells = cells.clone();

        return board;
    }

    /**
     * Returns the number of cells.
     *
     * @return The number of cells.
     */
    public int getCellCount() {
        return cells.length;
    }

    /**
     * Given an x and y coordinate, return the value of the cell at that coordinate.
     *
     * @param x The x coordinate of the cell
     * @param y The y coordinate of the cell
     * @return The value of the cell at the given coordinates.
     */
    public int getMove(int x, int y) {
        return cells[y * size + x];
    }

    /**
     * Returns the value of the cell at the index.
     *
     * @param move The index of the move.
     * @return The value of the cell at the index.
     */
    public int getMove(int move) {
        return cells[move];
    }

    /**
     * Returns the size of the game board.
     *
     * @return The size of the game board.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns a list of all available cells on the board.
     *
     * @return A list of all available cells on the board.
     */
    public ArrayList<Integer> getValidMoves() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == EMPTY_CELL) {
                list.add(i);
            }
        }

        return list;
    }

    /**
     * Returns whether the board has no empty cells left.
     *
     * @return Whether the board has no empty cells left.
     */
    public boolean isFull() {
        for (int cell : cells) {
            if (cell == EMPTY_CELL) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether the move is within the bounds of the board and the cell is empty.
     *
     * @param move The move to be checked
     * @return Whether the move is valid.
     */
    public boolean isValidMove(int move) {
        return move >= 0
                && move < cells.length
                && cells[move] == EMPTY_CELL;
    }

    /**
     * Sets the value of the cell at the given index to the given player.
     *
     * @param move The move that the player wants to make.
     * @param player The player who is making the move.
     */
    public void setMove(int move, int player) {
        cells[move] = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameBoard gameBoard = (GameBoard) o;
        return Arrays.equals(cells, gameBoard.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cells);
    }
}
