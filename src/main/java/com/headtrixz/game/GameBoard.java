package com.headtrixz.game;

import javafx.scene.control.Cell;

import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard {
    public enum CellState {
        EMPTY,
        PLAYER_ONE,
        PLAYER_TWO
    }

    private CellState[] cells;
    private final int size;

    public GameBoard(int size) {
        this.size = size;
        this.cells = new CellState[size * size];
        clear();
    }

    public void clear() {
        Arrays.fill(cells, CellState.EMPTY);
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

    public CellState getMove(int x, int y) {
        return cells[y * size + x];
    }

    public CellState getMove(int cell) {
        return cells[cell];
    }

    public int getScore(CellState player) {
        int score = 0;
        for (CellState cell : cells) {
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
            if (cells[i] == CellState.EMPTY) {
                list.add(i);
            }
        }

        return list;
    }

    public boolean isFull() {
        for (CellState cell : cells) {
            if (cell == CellState.EMPTY) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidMove(int cell) {
        return cell >= 0
                && cell < cells.length
                && cells[cell] == CellState.EMPTY;
    }

    public void setMove(int x, int y, CellState player) {
        cells[y * size + x] = player;
    }

    public void setMove(int cell, CellState player) {
        cells[cell] = player;
    }
}
