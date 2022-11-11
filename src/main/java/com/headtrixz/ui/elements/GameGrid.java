package com.headtrixz.ui.elements;

import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class GameGrid extends GridPane {
    private Consumer<Integer> callback;

    public GameGrid(int size, double gridSize, boolean renderCursor) {
        super();

        setGridLinesVisible(true);
        setMaxSize(gridSize, gridSize);

        final double paneSize = gridSize / size;
        for (int i = 0; i < size * size; i++) {
            StackPane sp = new StackPane();
            sp.setMinSize(paneSize, paneSize);
            sp.setMaxSize(paneSize, paneSize);

            if (renderCursor) {
                this.setCursor(Cursor.HAND);
            }

            final int index = i; // Java is stupid (╯°□°）╯︵ ┻━┻
            sp.setOnMouseClicked(a -> onClick(index));

            add(sp, i % size, i / size);
        }
    }

    public void setTile(int move, String player) {
        Text text = new Text(player);
        StackPane pane = (StackPane) this.getChildren().get(move + 1);
        pane.setCursor(Cursor.DEFAULT);
        pane.getChildren().add(text);
    }

    private void onClick(int index) {
        if (callback == null) {
            return;
        }

        callback.accept(index);
    }

    public void setCallback(Consumer<Integer> callback) {
        this.callback = callback;
    }
}
