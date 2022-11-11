package com.headtrixz.ui;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameModel;
import com.headtrixz.ui.elements.GameGrid;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GameFinishController {
    @FXML
    private Text endText;

    @FXML
    private StackPane container;

    private final GameModel game;

    public GameFinishController(GameModel game) {
        this.game = game;
    }

    public void initialize() {
        String opponentName = game.getPlayer(1).getUsername();
        String text = switch (game.getState()) {
            case PLAYER_ONE_WON -> String.format("Gefeliciteerd, je hebt van %s gewonnen.", opponentName);
            case PLAYER_TWO_WON -> String.format("Helaas, je hebt van %s verloren.", opponentName);
            case DRAW -> "Er is gelijk gespeeld, er zijn geen winnaars";
            default -> "Deze uitkomst zou niet mogelijk moeten zijn";
        };

        endText.setText(text);

        GameBoard board = game.getBoard();
        GameGrid grid = new GameGrid(board.getSize(), 300.0, false);

        String[] players = { "", "X", "O" };
        for (int i = 0; i < board.getCellCount(); i++) {
            int move = board.getMove(i);
            grid.setTile(i, players[move]);
        }

        container.getChildren().add(grid);
    }

    public void goHome() {
        UIManager.switchScreen("home");
    }
}
