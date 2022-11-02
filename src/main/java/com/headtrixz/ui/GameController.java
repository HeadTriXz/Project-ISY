package com.headtrixz.ui;

import com.headtrixz.tictactoe.TicTacToeAI;
import com.headtrixz.ui.elements.GameGrid;
import com.headtrixz.game.HumanPlayer;
import com.headtrixz.game.GameModel;
import com.headtrixz.tictactoe.TicTacToe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private static final double PANE_SIZE = 300.0;

    private GameModel game;
    private GameGrid gameGrid;

    @FXML
    private Text playerOneName;
    @FXML
    private Text playerTwoName;
    @FXML
    private StackPane container;

    public void displayHome() {
        UIManager.switchScreen("home");
    }

    public void endGame() {
        UIManager.switchScreen("game-finish");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { // TODO: Replace because hardcoded.
        this.game = new TicTacToe();

        HumanPlayer playerOne = new HumanPlayer(this.game, "test");
        TicTacToeAI playerTwo = new TicTacToeAI((TicTacToe) this.game);

        this.game.initialize(this, playerOne, playerTwo);

        this.gameGrid = new GameGrid(this.game.getBoard().getSize(), PANE_SIZE);
        this.gameGrid.createBoardGrid();
        this.gameGrid.setCallback((index) -> onMouseClick(index));
        this.container.getChildren().add(this.gameGrid);
    }

    private void onMouseClick(int index) {
        game.setGuiMove(index);
    }

    public void onUpdate(int move, int player) {
        String[] players = { "", "X", "O" }; // TODO: Do this differently
        this.gameGrid.setTile(move, players[player]);
    }
}
