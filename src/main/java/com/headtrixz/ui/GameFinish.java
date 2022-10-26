package com.headtrixz.ui;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameModel.GameState;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameFinish {
    @FXML
    private Text endText;

    @FXML
    private StackPane container;

    GameBoard gameBoard;

    public void goHome() throws Exception {
        Stage screen = (Stage) endText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("home.fxml"));
        screen.setScene(new Scene(fxmlLoader.load(), 600, 400));
    }

    public void initialize() {
        SharedState ss = SharedState.getInstance();
        this.gameBoard = ss.board;
        GameState gs = ss.winner;

        switch (gs) {
            case PLAYER_ONE_WON:
                endText.setText("Gefeliciteerd, je hebt van de AI gewonnen.");
                break;

            case PLAYER_TWO_WON:
                endText.setText("Helaas, je hebt van de AI verloren");
                break;

            case DRAW:
                endText.setText("Er is gelijk gespeeld, er zijn geen winnaars");
                break;

            case PLAYING:
                endText.setText("Deze uitkomst zou niet mogelijk moeten zijn");
                break;
        }

        createBoardGrid();
    }

    private void createBoardGrid() {
        double pane_size = 300.0;
        GridPane boardGrid = new GridPane();
        String[] players = { "", "X", "O" };
        boardGrid.setGridLinesVisible(true);
        boardGrid.setMaxSize(pane_size, pane_size);

        final int boardSize = gameBoard.getSize();
        final double paneSize = pane_size / boardSize;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                StackPane sp = new StackPane();
                sp.setMinWidth(paneSize);
                sp.setMaxWidth(paneSize);
                sp.setMinHeight(paneSize);
                sp.setMaxHeight(paneSize);

                sp.getChildren().add(new Text(players[gameBoard.getMove(i, j)]));

                boardGrid.add(sp, i, j);
            }
        }

        container.getChildren().add(boardGrid);
    }
}
