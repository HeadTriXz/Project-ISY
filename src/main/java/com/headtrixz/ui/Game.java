package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game {
    @FXML
    private Text playerOneName;

    @FXML
    private Text playerTwoName;

    @FXML
    private StackPane container;

    private GridPane board;

    public void initialize() {
        board = this.createTicTacToeBoard();
        container.getChildren().addAll(board);
    }

    private GridPane createTicTacToeBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.setMaxSize(300, 300);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                StackPane sp = new StackPane();
                sp.setMinWidth(100);
                sp.setMaxWidth(100);
                sp.setMinHeight(100);
                sp.setMaxHeight(100);
                sp.setCursor(Cursor.HAND);
                final int col = i;
                final int row = j;
                sp.setOnMouseClicked(a -> setMove(col, row));
                gridPane.add(sp, i, j);
            }
        }

        return gridPane;
    }

    private void setMove(int y, int x) {
        StackPane pane = (StackPane) board.getChildren().get(1 + x + y * 3);
        pane.setCursor(Cursor.DEFAULT);
        Text aah = new Text("X");
        pane.getChildren().add(aah);
    }

    public void goHome() throws Exception {
        Stage screen = (Stage) board.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("home.fxml"));
        screen.setScene(new Scene(fxmlLoader.load(), 600, 400));
    }
}
