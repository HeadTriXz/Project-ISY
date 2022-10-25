package com.headtrixz.controllers;

import com.headtrixz.game.AIPlayer;
import com.headtrixz.game.HumanPlayer;
import com.headtrixz.models.GameModel;
import com.headtrixz.models.TicTacToe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private static final double PANE_SIZE = 300.0;

    private GridPane boardGrid;
    private GameModel game;

    @FXML private Text playerOneName;
    @FXML private Text playerTwoName;
    @FXML private StackPane container;

    private void createBoardGrid() {
        boardGrid = new GridPane();
        boardGrid.setGridLinesVisible(true);
        boardGrid.setMaxSize(PANE_SIZE, PANE_SIZE);

        final int boardSize = game.getBoard().getSize();
        final double paneSize = PANE_SIZE / boardSize;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                StackPane sp = new StackPane();
                sp.setMinWidth(paneSize);
                sp.setMaxWidth(paneSize);
                sp.setMinHeight(paneSize);
                sp.setMaxHeight(paneSize);
                sp.setCursor(Cursor.HAND);

                final int col = i;
                final int row = j;
                sp.setOnMouseClicked(a -> onMouseClick(row, col));
                boardGrid.add(sp, i, j);
            }
        }

        container.getChildren().add(boardGrid);
    }

    public void displayGameFinish() {
        displayScene("game-finish"); // TODO: Add actual state
    }

    public void displayHome() {
        displayScene("home");
    }

    private void displayScene(String name) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + name + ".fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) boardGrid.getScene().getWindow();
        stage.setScene(scene);
    }

    public void endGame() { // TODO: Debug only.
        displayGameFinish();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { // TODO: Replace because hardcoded.
        this.game = new TicTacToe();

        HumanPlayer playerOne = new HumanPlayer("test");
        AIPlayer playerTwo = new AIPlayer(this.game);

        this.game.initialize(this, playerOne, playerTwo);
        this.createBoardGrid();
    }

    private void onMouseClick(int x, int y) {
        int move = y * game.getBoard().getSize() + x;
        game.setMove(move, game.getCurrentPlayer().getId());
    }

    public void onUpdate(int move, int player) {
        Text t = new Text(Integer.toString(player));
        StackPane pane = (StackPane) boardGrid.getChildren().get(move + 1);
        pane.getChildren().add(t);
    }
}
