package com.headtrixz.ui.elements;

import com.headtrixz.game.GameModel;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * The GameGrid component/element.
 */
public class GameGrid extends GridPane {
    private Consumer<Integer> callback;
    private final double paneSize;

    /**
     * Create a fancy new board! With some options.
     *
     * @param size The amount of the squares it has each direction.
     * @param gridSize The size that the grid is allowed to be.
     * @param backgroundColor The color of the grid's background.
     */
    public GameGrid(int size, double gridSize, Color backgroundColor) {
        super();

        setBackground(Background.fill(backgroundColor));
        setMaxSize(gridSize, gridSize);

        paneSize = gridSize / size;
        for (int i = 0; i < size * size; i++) {
            StackPane sp = new StackPane();
            sp.setMinSize(paneSize, paneSize);
            sp.setMaxSize(paneSize, paneSize);
            sp.setStyle("-fx-border-color: #1e1e2e");

            final int index = i; // Java is stupid (╯°□°）╯︵ ┻━┻
            sp.setOnMouseClicked(a -> onClick(index));

            add(sp, i % size, i / size);
        }
    }

    /**
     * Set a symbol in a square and disable the cursor.
     *
     * @param move The index to fill in.
     * @param path The symbol to put into the square.
     */
    public void setTile(int move, String path) {
        if (path == null) {
            return;
        }

        Image image = new Image(path, paneSize, paneSize, false, true);
        ImageView view = new ImageView(image);
        view.setFitWidth(paneSize);
        view.setFitHeight(paneSize);

        StackPane pane = (StackPane) this.getChildren().get(move);
        pane.setCursor(Cursor.DEFAULT);
        pane.getChildren().add(view);
    }

    /**
     * On click event for when a square gets pressed.
     *
     * @param index The index of the pressed square.
     */
    private void onClick(int index) {
        if (callback == null) {
            return;
        }

        callback.accept(index);
    }

    /**
     * Set the callback method to send back which square was pressed.
     *
     * @param callback The callback method.
     */
    public void setCallback(Consumer<Integer> callback) {
        this.callback = callback;
    }

    /**
     * Clear the complete board.
     *
     * @param len the length of the board.
     */
    public void clearBoard(int len) {
        for (int i = 0; i < len; i++) {
            StackPane pane = (StackPane) this.getChildren().get(i);
            pane.getChildren().clear();
            pane.setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * Set suggestion tiles.
     *
     * @param suggestions the suggestions.
     * @param path The path to the image for suggestions.
     */
    public void setSuggestions(List<Integer> suggestions, String path) {
        for (int move : suggestions) {
            StackPane pane = (StackPane) this.getChildren().get(move);
            pane.setCursor(Cursor.HAND);

            if (path != null) {
                Image image = new Image(path, paneSize, paneSize, false, true);
                ImageView view = new ImageView(image);
                view.setOpacity(0.2);

                pane.getChildren().add(view);
            }
        }
    }

    /**
     * Updates the tiles in the game grid.
     */
    public void update(GameModel game) {
        int[] board = game.getBoard().getCells();
        clearBoard(board.length);

        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0) {
                setTile(i, game.getImage(board[i]));
            }
        }
    }
}
