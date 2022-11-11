package com.headtrixz.ui;

import com.headtrixz.ui.elements.GameGrid;
import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.players.TicTacToeAI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GameController implements GameMethods {
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
        game = new TicTacToe();
        helper = new OfflineHelper(game);

        Player playerOne = new HumanPlayer(game, "Humon");
        Player playerTwo = new TicTacToeAI((TicTacToe) game, "Compuper");

        game.initialize(this, helper, playerOne, playerTwo);

        gameGrid = new GameGrid(game.getBoard().getSize(), PANE_SIZE, true);
        gameGrid.setCallback(this::onMouseClick);
        container.getChildren().add(gameGrid);

        // Set visible usernames.
        playerOneName.setText(playerOne.getUsername());
        playerTwoName.setText(playerTwo.getUsername());
    }

    private void onMouseClick(int index) {
        game.setGuiMove(index);
    }

    @Override
    public void update(int move, Player player) {
        String[] players = { "", "X", "O" }; // TODO: Do this differently
        gameGrid.setTile(move, players[player.getId()]);
    }
}
