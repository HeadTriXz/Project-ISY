package com.headtrixz.game;

import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard {
    public static final int EMPTY_CELL = 0;
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;

    private int[] cells;
    private final int size;

    public GameBoard(int size) {
        this.size = size;
        this.cells = new int[size * size];
        clear();
    }

    public void clear() {
        Arrays.fill(cells, EMPTY_CELL);
    }

    @Override
    public GameBoard clone() {
        GameBoard board = new GameBoard(size);
        board.cells = cells.clone();

        return board;
    }

    public int getCellCount() {
        return cells.length;
    }

    public int getMove(int x, int y) {
        return cells[y * size + x];
    }

    public int getMove(int move) {
        return cells[move];
    }

    public int getScore(int player) {
        int score = 0;
        for (int cell : cells) {
            if (cell == player) {
                score++;
            }
        }

        return score;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Integer> getValidMoves() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == EMPTY_CELL) {
                list.add(i);
            }
        }

        return list;
    }

    public boolean isFull() {
        for (int cell : cells) {
            if (cell == EMPTY_CELL) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidMove(int move) {
        return move >= 0
                && move < cells.length
                && cells[move] == EMPTY_CELL;
    }

    public void setMove(int x, int y, int player) {
        cells[y * size + x] = player;
    }

    public void setMove(int move, int player) {
        cells[move] = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameBoard gameBoard = (GameBoard) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(cells, gameBoard.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cells);
    }
}
