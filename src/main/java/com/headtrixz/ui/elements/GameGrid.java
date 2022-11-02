package com.headtrixz.ui.elements;

import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GameGrid extends GridPane {
    private int size;
    private double boardSize;
    private Callback callback;

    public GameGrid(int size, double boardSize) {
        super();

        this.size = size;
        this.boardSize = boardSize;
    }

    public void createBoardGrid() {
        this.createBoardGrid(true);
    }

    public void createBoardGrid(boolean renderCursor) {
        this.setGridLinesVisible(true);
        this.setMaxSize(boardSize, boardSize);

        final double paneSize = boardSize / size;

        for (int i = 0; i < size * size; i++) {
            StackPane sp = new StackPane();
            sp.setMinSize(paneSize, paneSize);
            sp.setMaxSize(paneSize, paneSize);

            final int row = i / size;
            final int col = i % size;

            if (renderCursor) {
                this.setCursor(Cursor.HAND);
            }

            final int index = i; // because java is stupid
            sp.setOnMouseClicked(a -> this.onClick(index));
            this.add(sp, col, row);
        }
    }

    public void setTile(int move, String player) {
        Text t = new Text(player);
        StackPane pane = (StackPane) this.getChildren().get(move + 1);
        pane.setCursor(Cursor.DEFAULT);
        pane.getChildren().add(t);
    }

    private void onClick(int index) {
        if (this.callback == null) {
            return;
        }

        this.callback.cb(index);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
