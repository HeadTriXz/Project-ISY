package com.headtrixz.ui;

import com.headtrixz.ui.elements.GameGrid;
import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GameController implements GameMethods {
    private static final double PANE_SIZE = 300.0;

    private GameModel game;
    private GameGrid gameGrid;

    @FXML
    private Text playerOneName;
    @FXML
    private Text playerTwoName;
    @FXML
    private StackPane container;

    /**
     * Create a new controller
     *
     * @param game the game this controller will host
     */
    public GameController(GameModel game) {
        this.game = game;
    }

    public void displayHome() {
        game.getHelper().forfeit();
        UIManager.switchScreen("home");
    }

    @Override
    public void endGame() {
        UIManager.switchScreen("game-finish", new GameFinishController(game));
    }

    public void initialize() {
        gameGrid = new GameGrid(game.getBoard().getSize(), PANE_SIZE, true);
        gameGrid.setCallback(this::onMouseClick);
        container.getChildren().add(gameGrid);

        // Set visible usernames.
        playerOneName.setText(game.getPlayer(0).getUsername());
        playerTwoName.setText(game.getPlayer(1).getUsername());
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
