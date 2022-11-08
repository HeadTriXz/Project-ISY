package com.headtrixz.ui;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameModel.GameState;
import com.headtrixz.ui.elements.GameGrid;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GameFinish {
    @FXML
    private Text endText;

    @FXML
    private StackPane container;

    private String playerTwoName;
    private GameState winner;
    private GameBoard state;

    public void initialize() {
        switch (winner) {
            case PLAYER_ONE_WON:
                endText.setText(String.format("Gefeliciteerd, je hebt van %s gewonnen.", playerTwoName));
                break;

            case PLAYER_TWO_WON:
                endText.setText(String.format("Helaas, je hebt van %s verloren.", playerTwoName));
                break;

            case DRAW:
                endText.setText("Er is gelijk gespeeld, er zijn geen winnaars");
                break;

            case PLAYING:
                endText.setText("Deze uitkomst zou niet mogelijk moeten zijn");
                break;
        }

        GameGrid gg = new GameGrid(state.getSize(), 300.0);
        gg.createBoardGrid(false);

        String[] players = { "", "X", "O" };
        for (int i = 0; i < state.getCellCount(); i++) {
            int move = state.getMove(i);
            gg.setTile(i, players[move]);
        }

        container.getChildren().add(gg);
    }

    public void goHome() throws Exception {
        UIManager.switchScreen("home");
    }

    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
    }

    public void setWinner(GameState winner) {
        this.winner = winner;
    }

    public void setState(GameBoard state) {
        this.state = state;
    }
}
