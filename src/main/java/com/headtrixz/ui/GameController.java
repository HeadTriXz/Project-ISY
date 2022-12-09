package com.headtrixz.ui;

import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.ui.elements.GameGrid;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * A controller for the game screen.
 */
public class GameController extends GameMethods {
    private static final double PANE_SIZE = 300.0;

    @FXML
    private Text playerOneName;
    @FXML
    private Text playerTwoName;
    @FXML
    private StackPane container;

    /**
     * Create a new controller.
     *
     * @param game the game this controller will host
     */
    public GameController(GameModel game) {
        this.game = game;
    }

    /**
     * Stop the game and go back to the home screen.
     */
    public void displayHome() {
        game.getHelper().forfeit();
        UIManager.switchScreen("home");
    }

    /**
     * Gets called when the game ends comes to a natural ending. Switches to the Game Finish screen
     * with the current game data.
     */
    @Override
    public void endGame() {
        UIManager.switchScreen("game-finish", new GameFinishController(game));
    }

    /**
     * FXML init method. Gets called when the screen has loaded. Sets up the players and game.
     */
    public void initialize() {
        gameGrid = new GameGrid(game.getBoard().getSize(), PANE_SIZE, game.getBackgroundColor());
        gameGrid.setCallback(this::onMouseClick);
        container.getChildren().add(gameGrid);

        // Set visible usernames.
        playerOneName.setText(game.getPlayer(0).getUsername());
        playerTwoName.setText(game.getPlayer(1).getUsername());

        update(-1, null);
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
     * Gets called when a set is done on the board by either players. Updates the tile on the board
     * to show which player has set which move.
     *
     * @param move the index of the move the player has done.
     * @param player the player who has set the move.
     */
    @Override
    public void update(int move, Player player) {
        updateGrid();

        if (game.getCurrentPlayer() instanceof HumanPlayer) {
            updateSuggestions();
        }
    }

    /**
     * Updates the suggestions on the game grid.
     */
    public void updateSuggestions() {
        List<Integer> moves = game.getValidMoves();
        gameGrid.setSuggestions(moves, game.getImage(0));
    }
}
