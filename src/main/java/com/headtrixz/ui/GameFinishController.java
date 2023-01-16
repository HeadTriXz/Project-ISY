package com.headtrixz.ui;

import static com.headtrixz.game.GameBoard.EMPTY_CELL;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameModel;
import com.headtrixz.ui.elements.GameGrid;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Controller for the GameFinish screen.
 */
public class GameFinishController {
    @FXML
    private Text endText;

    @FXML
    private StackPane container;

    private final GameModel game;

    /**
     * Inits the game finish controller with data.
     *
     * @param game the state of the game whenever it finished.
     */
    public GameFinishController(GameModel game) {
        this.game = game;
    }

    /**
     * FXML init method. Gets called when the screen has loaded.
     * Shows the board with who has won.
     */
    public void initialize() {
        String opponent = game.getPlayer(1).getUsername();
        if (game.getState() == GameModel.GameState.PLAYER_ONE_WON) {
            String fireworksPath =
                GameFinishController.class.getResource("/images/firework.gif").toString();

            Image image = new Image(fireworksPath, 300, 300, false, true);
            ImageView imageView = new ImageView(image);
            imageView.setX(10);

            container.getChildren().add(imageView);
        }


        String text = switch (game.getState()) {
            case PLAYER_ONE_WON ->
                String.format("Gefeliciteerd, je hebt van %s gewonnen.", opponent);
            case PLAYER_TWO_WON -> String.format("Helaas, je hebt van %s verloren.", opponent);
            case DRAW -> "Er is gelijk gespeeld, er zijn geen winnaars";
            default -> "Deze uitkomst zou niet mogelijk moeten zijn";
        };

        endText.setText(text);

        GameBoard board = game.getBoard();
        GameGrid grid = new GameGrid(board.getSize(), 300.0, game.getBackgroundColor());

        for (int i = 0; i < board.getCellCount(); i++) {
            int move = board.getMove(i);
            if (move != EMPTY_CELL) {
                grid.setTile(i, game.getImage(move));
            }
        }

        container.getChildren().add(grid);
    }

    /**
     * OnClick event to go back to the home screen.
     */
    public void goHome() {
        UIManager.switchScreen("home");
    }
}
