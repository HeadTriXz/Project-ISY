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

    /**
     * Stop the game and go back to the home screen.
     */
    public void displayHome() {
        helper.forfeit();
        UIManager.switchScreen("home");
    }

    /**
     * Gets called when the game ends comes to a natural ending.
     * Switches to the Game Finish screen with the current game data.
     */
    @Override
    public void endGame() {
        UIManager.switchScreen("game-finish", new GameFinishController(game));
    }

    /**
     * FXML init method. Gets called when the screen has loaded.
     * Sets up the players and game.
     */
    public void initialize() {
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

    /**
     * Gets called whenever a square on the board gets clicked.
     *
     * @param index The number which relates to the position on the board.
     */
    private void onMouseClick(int index) {
        game.setGuiMove(index);
    }

    /**
     * Gets called when a set is done on the board by either players.
     * Updates the tile on the board to show which player has set which move.
     *
     * @param move the index of the move the player has done.
     * @param player the player who has set the move.
     */
    @Override
    public void update(int move, Player player) {
        String[] players = { "", "X", "O" }; // TODO: Do this differently
        gameGrid.setTile(move, players[player.getId()]);
    }
}
