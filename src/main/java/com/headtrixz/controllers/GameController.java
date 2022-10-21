package com.headtrixz.controllers;

import com.headtrixz.framework.Connection;
import com.headtrixz.game.GameBoard;
import com.headtrixz.game.HumanPlayer;
import com.headtrixz.game.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public abstract class GameController implements Initializable {
    public enum GameState {
        PLAYING,
        PLAYER_ONE_WON,
        PLAYER_TWO_WON,
        DRAW
    }

    protected GameBoard board;
    protected GridPane boardGrid;
    protected Connection connection; // TODO: Implement remote mode

    protected Player currentPlayer;
    protected Player playerOne;
    protected Player playerTwo;
    protected GameState state = null;

    @FXML private Text playerOneName;
    @FXML private Text playerTwoName;
    @FXML private StackPane container;

    public GameController(int boardSize) {
        this.board = new GameBoard(boardSize);
    }

    private void createBoardGrid() {
        boardGrid = new GridPane();
        boardGrid.setGridLinesVisible(true);
        boardGrid.setMaxSize(300, 300);

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                double paneSize = 300.0 / board.getSize();
                StackPane sp = new StackPane();
                sp.setMinWidth(paneSize);
                sp.setMaxWidth(paneSize);
                sp.setMinHeight(paneSize);
                sp.setMaxHeight(paneSize);
                sp.setCursor(Cursor.HAND);

                final int col = i;
                final int row = j;
                sp.setOnMouseClicked(a -> setMove(row, col));
                boardGrid.add(sp, i, j);
            }
        }

        container.getChildren().add(boardGrid);
    }

    public void displayWinAlert() { // TODO: Debug only! Check which player is local.
        ButtonType backButton = new ButtonType("Back", ButtonBar.ButtonData.BACK_PREVIOUS);
        Alert alert = new Alert(Alert.AlertType.NONE, null, backButton);
        alert.setTitle("Game");
        alert.setHeaderText(null);

        if (state == GameState.PLAYER_ONE_WON) { // TODO: Debug only, player one is not always local.
            alert.setContentText("Gefeliciteerd! Je hebt gewonnen.");
        }

        switch (getState()) {
            case PLAYER_ONE_WON -> alert.setContentText("Gefeliciteerd! Je hebt gewonnen.");
            case PLAYER_TWO_WON -> alert.setContentText("Helaas! Je hebt verloren.");
            case DRAW -> alert.setContentText("Volgende keer beter! Je hebt gelijkspel.");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() == backButton) {
            try {
                goHome();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void goHome() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        Stage stage = (Stage) boardGrid.getScene().getWindow();
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.connection = Connection.getInstance();
        this.playerOne = new HumanPlayer(playerOneName.getText(), 1);
        this.playerTwo = new HumanPlayer(playerTwoName.getText(), 2); // TODO: Debug only! Find a way to properly define players.
        this.currentPlayer = playerOne;
        this.createBoardGrid();
    }

    private void nextPlayer() {
        currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
    }

    private String getCellText(Player player) {
        return player.getCellState() == GameBoard.CellState.PLAYER_ONE ? "X" : "O";
    }

    private void setMove(int x, int y) {
        int move = y * board.getSize() + x;
        if (!board.isValidMove(move)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illegal move");
            alert.setHeaderText(null);
            alert.setContentText("That is an invalid move.");

            alert.show();
            return;
        }

        StackPane pane = (StackPane) boardGrid.getChildren().get(move + 1);
        pane.setCursor(Cursor.DEFAULT);

        Text text = new Text(getCellText(currentPlayer));
        pane.getChildren().add(text);

        board.setMove(x, y, currentPlayer.getCellState());
        if (getState() != GameState.PLAYING) {
            displayWinAlert();
            return;
        }

        nextPlayer();
        if (currentPlayer == playerTwo) { // TODO: Debug only! Replace with AI somewhere
            ArrayList<Integer> moves = board.getValidMoves();

            Random random = new Random();
            int randomMove = moves.get(random.nextInt(moves.size()));

            setMove(randomMove % board.getSize(), randomMove / board.getSize());
        }
    }

    public abstract GameState getState();
}
