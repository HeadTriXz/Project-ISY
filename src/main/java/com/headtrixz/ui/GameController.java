package com.headtrixz.ui;

import com.headtrixz.Utils;
import com.headtrixz.tictactoe.TicTacToeAI;
import com.headtrixz.game.HumanPlayer;
import com.headtrixz.game.GameModel;
import com.headtrixz.tictactoe.TicTacToe;
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

    @FXML
    private Text playerOneName;
    @FXML
    private Text playerTwoName;
    @FXML
    private StackPane container;

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
                boardGrid.add(sp, j, i);
            }
        }

        container.getChildren().add(boardGrid);
    }

    public void displayGameFinish() {
        Utils.goTo(playerOneName, "game-finish");
    }

    public void displayHome() {
        Utils.goTo(playerOneName, "home");
    }

    public void endGame() { // TODO: Demo only.
        SharedState sh = SharedState.getInstance();
        sh.winner = game.getState();
        sh.board = game.getBoard();
        displayGameFinish();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { // TODO: Replace because hardcoded.
        this.game = new TicTacToe();

        HumanPlayer playerOne = new HumanPlayer(this.game, "test");
        TicTacToeAI playerTwo = new TicTacToeAI((TicTacToe) this.game);

        playerOneName.setText(playerOne.getUsername() + " - X");
        playerTwoName.setText("O - " + playerTwo.getUsername());

        this.game.initialize(this, playerOne, playerTwo);
        this.createBoardGrid();
    }

    private void onMouseClick(int x, int y) {
        game.setGuiMove(y * game.getBoard().getSize() + x);
    }

    public void onUpdate(int move, int player) {
        String[] players = { "", "X", "O" };
        Text t = new Text(players[player]);
        StackPane pane = (StackPane) boardGrid.getChildren().get(move + 1);
        pane.getChildren().add(t);
    }
}
