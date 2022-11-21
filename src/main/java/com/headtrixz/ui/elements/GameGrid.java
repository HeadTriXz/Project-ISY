package com.headtrixz.ui.elements;

import java.util.function.Consumer;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GameGrid extends GridPane {
  private Consumer<Integer> callback;

  /**
   * Create a fancy new board! With some options.
   *
   * @param size The amount of the squares it has each direction.
   * @param gridSize The size that the grid is allowed to be.
   * @param renderCursor Render a pointy cursor when someone hover over a open square.
   */
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

  /**
   * Set a symbol in a square and disable the cursor.
   *
   * @param move The index to fill in.
   * @param player The symbol to put into the square.
   */
  public void setTile(int move, String player) {
    Text text = new Text(player);
    StackPane pane = (StackPane) this.getChildren().get(move + 1);
    pane.setCursor(Cursor.DEFAULT);
    pane.getChildren().add(text);
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
}
