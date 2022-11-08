package com.headtrixz.ui;

import java.util.HashMap;
import java.util.function.Consumer;

import com.headtrixz.game.GameCommands;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.helpers.OnlineHelper;
import com.headtrixz.game.players.RemotePlayer;
import com.headtrixz.networking.Connection;
import com.headtrixz.networking.ServerMessage;
import com.headtrixz.networking.ServerMessageType;
import com.headtrixz.tictactoe.TicTacToe;
import com.headtrixz.tictactoe.TicTacToeAI;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class Tournament implements GameCommands {
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

    public void initialize() {
        drawCount = 0;
        winCount = 0;
        loseCount = 0;
        username = UIManager.getSetting("username");
        loggedInAs.setText(String.format("Ingelogd als: %s", username));

        Connection connection = Connection.getInstance();
        connection.getOutputHandler().login(username);
        connection.getInputHandler().on(ServerMessageType.MATCH, match);
    }

    private final Consumer<ServerMessage> match = message -> {
        HashMap<String, String> obj = message.getObject();
        String oppenent = obj.get("OPPONENT");
        addToLogs("Starting match with: " + oppenent);

        // TODO: Set this to a helper/util class
        currentGame = new TicTacToe();
        RemotePlayer remotePlayer = new RemotePlayer(oppenent);
        TicTacToeAI aiPlayer = new TicTacToeAI((TicTacToe) currentGame, username);
        onlineHelper = new OnlineHelper(currentGame);
        currentGame.initialize(this, onlineHelper, aiPlayer, remotePlayer);
    };

    public void addToLogs(String message) {
        logs.appendText(String.format("%s\n", message));
        logs.setScrollTop(Double.MAX_VALUE);
    }

    public void disconnect() {
        if (currentGame != null) {
            onlineHelper.forfeit();
        }
        Connection connection = Connection.getInstance();
        connection.getInputHandler().off(ServerMessageType.MATCH, match);
        connection.getOutputHandler().logout();
        UIManager.switchScreen("home");
    }

    @Override
    public void endGame() {
        String logText;
        switch (currentGame.getState()) {
            case PLAYER_ONE_WON:
                winCount++;
                wins.setText(String.format("Gewonnen: %d", winCount));
                logText = "Match gewonnen van";
                break;
            case DRAW:
                drawCount++;
                draws.setText(String.format("Gelijkspel: %d", drawCount));
                logText = "Match gelijkgespeeld tegen";
                break;
            case PLAYER_TWO_WON:
                loseCount++;
                loses.setText(String.format("Verloren: %d", loseCount));
                logText = "Match verloren van";
                break;
            case PLAYING:
            default:
                // This default case should not be able to be reached. But everything complains
                // if i don't have this... Thanks java!
                logText = "Ja dit is een apparte situatie, maar je hebt iets gedaan tegen";
                break;
        }

        String opponent = currentGame.getPlayer(1).getUsername();
        addToLogs(String.format("%s: %s\n", logText, opponent));

        currentGame = null;
    }

    @Override
    public void update(int move, int playerId) {
        String player = currentGame.getPlayer(playerId).getUsername();
        addToLogs(String.format("%s was gezet door speler %s", move, player));
    }
}
