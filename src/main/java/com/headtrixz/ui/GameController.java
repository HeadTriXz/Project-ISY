package com.headtrixz.ui;

import com.headtrixz.ui.elements.GameGrid;
import com.headtrixz.game.GameCommands;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.tictactoe.TicTacToe;
import com.headtrixz.tictactoe.TicTacToeAI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable, GameCommands {
    private static final double PANE_SIZE = 300.0;

    private GameModel game;
    private OfflineHelper helper;
    private GameGrid gameGrid;

    @FXML
    private Text playerOneName;
    @FXML
    private Text playerTwoName;
    @FXML
    private StackPane container;

    public void displayHome() {
        helper.forfeit();
        UIManager.switchScreen("home");
    }

    @Override
    public void endGame() {
        try {
            GameFinishController gfController = new GameFinishController();
            gfController.setPlayerTwoName(this.game.getPlayer(1).getUsername());
            gfController.setState(this.game.cloneBoard());
            gfController.setWinner(this.game.getState());
            Stage screen = (Stage) playerOneName.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("game-finish.fxml"));
            fxmlLoader.setController(gfController);
            screen.setScene(new Scene(fxmlLoader.load(), 600, 400));
        } catch (IOException e) {
            System.out.println("Something went wrong whilst switching screens");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Turn this into a something else.
        this.game = new TicTacToe();
        this.helper = new OfflineHelper(this.game);

        Player playerOne = new HumanPlayer(this.game, "Humon");
        Player playerTwo = new TicTacToeAI((TicTacToe) this.game, "Compuper");

        this.game.initialize(this, this.helper, playerOne, playerTwo);

        this.gameGrid = new GameGrid(this.game.cloneBoard().getSize(), PANE_SIZE);
        this.gameGrid.createBoardGrid();
        this.gameGrid.setCallback((index) -> onMouseClick(index));
        this.container.getChildren().add(this.gameGrid);

        // Set visible usernames.
        playerOneName.setText(playerOne.getUsername());
        playerTwoName.setText(playerTwo.getUsername());
    }

    private void onMouseClick(int index) {
        game.setGuiMove(index);
    }

    @Override
    public void update(int move, int player) {
        String[] players = { "", "X", "O" }; // TODO: Do this differently
        this.gameGrid.setTile(move, players[player]);
    }
}
