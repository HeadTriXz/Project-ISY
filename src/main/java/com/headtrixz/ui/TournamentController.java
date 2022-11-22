package com.headtrixz.ui;

import java.util.HashMap;

import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.helpers.OnlineHelper;
import com.headtrixz.game.players.Player;
import com.headtrixz.game.players.RemotePlayer;
import com.headtrixz.networking.Connection;
import com.headtrixz.networking.InputListener;
import com.headtrixz.networking.ServerMessageType;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.players.TicTacToeAI;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class TournamentController implements GameMethods {
    @FXML
    TextArea logs;
    @FXML
    Text wins;
    @FXML
    Text loses;
    @FXML
    Text draws;
    @FXML
    Text loggedInAs;

    String username;
    GameModel currentGame;
    OnlineHelper onlineHelper;

    int drawCount;
    int winCount;
    int loseCount;

    /**
     * Appends text to the log text field and scrolls down the latest message.
     *
     * @param message the message to append.
     */
    public void addToLogs(String message) {
        logs.appendText(String.format("%s\n", message));
        logs.setScrollTop(Double.MAX_VALUE);
    }

    /**
     * On click event for the disconnect button.
     * Forfeits the current match and logs out of the server.
     */
    public void disconnect() {
        if (currentGame != null) {
            onlineHelper.forfeit();
        }

        Connection connection = Connection.getInstance();
        connection.getInputHandler().unsubscribe(ServerMessageType.MATCH, onMatch);
        connection.getOutputHandler().logout();
        UIManager.switchScreen("home");
    }

    /**
     * Gets called when a game has ended. Shows a message in the log for what
     * the result was and increments the appropriate counter.
     */
    @Override
    public void endGame() {
        String logText = "Ja dit is een apparte situatie, maar je hebt iets gedaan tegen";
        switch (currentGame.getState()) {
            case PLAYER_ONE_WON -> {
                winCount++;
                wins.setText(String.format("Gewonnen: %d", winCount));
                logText = "Match gewonnen van";
            }

            case PLAYER_TWO_WON -> {
                loseCount++;
                loses.setText(String.format("Verloren: %d", loseCount));
                logText = "Match verloren van";
            }

            case DRAW -> {
                drawCount++;
                draws.setText(String.format("Gelijkspel: %d", drawCount));
                logText = "Match gelijkgespeeld tegen";
            }
        }

        String opponent = currentGame.getPlayer(1).getUsername();
        addToLogs(String.format("%s: %s\n", logText, opponent));

        currentGame = null;
    }

    /**
     * FXML init method. Logs into the server when the screen has loaded.
     */
    public void initialize() {
        username = UIManager.getSetting("username");
        loggedInAs.setText(String.format("Ingelogd als: %s", username));

        Connection connection = Connection.getInstance();
        connection.getOutputHandler().login(username);
        connection.getInputHandler().subscribe(ServerMessageType.MATCH, onMatch);
    }

    /**
     * A listener that listens to the network connecting and starts a local game
     * to mirror the online game.
     */
    private final InputListener onMatch = message -> {
        HashMap<String, String> obj = message.getObject();
        String oppenent = obj.get("OPPONENT");
        addToLogs("Start een match met: " + oppenent);

        // TODO: Set this to a helper/util class
        currentGame = new TicTacToe();
        RemotePlayer remotePlayer = new RemotePlayer(oppenent);
        TicTacToeAI aiPlayer = new TicTacToeAI((TicTacToe) currentGame, username);
        onlineHelper = new OnlineHelper(currentGame);
        currentGame.initialize(this, onlineHelper, aiPlayer, remotePlayer);
    };

    /**
     * Gets called when a set is done on the board by either players.
     * Puts a log message of the move.
     *
     * @param move the index of the move the player has done.
     * @param player the player who has set the move.
     */
    @Override
    public void update(int move, Player player) {
        addToLogs(String.format("%s was gezet door speler %s", move, player.getUsername()));
    }
}
