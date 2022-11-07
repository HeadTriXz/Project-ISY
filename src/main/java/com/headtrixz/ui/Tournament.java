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

    String username;
    GameModel currentGame;

    int drawCount;
    int winCount;
    int loseCount;

    public void initialize() {
        drawCount = 0;
        winCount = 0;
        loseCount = 0;
        username = UIManager.getSetting("username");

        Connection connection = Connection.getInstance();
        connection.getOutputHandler().login(username);
        connection.getInputHandler().on(ServerMessageType.MATCH, match);
    }

    private final Consumer<ServerMessage> match = message -> {
        HashMap<String, String> obj = message.getObject();
        String oppenent = obj.get("OPPONENT");
        addToLogs("Starting match with: " + oppenent);

        // TODO: Set this to helper
        currentGame = new TicTacToe();
        RemotePlayer remotePlayer = new RemotePlayer(oppenent);
        TicTacToeAI aiPlayer = new TicTacToeAI((TicTacToe) currentGame, username);
        OnlineHelper onlineHelper = new OnlineHelper(currentGame);
        currentGame.initialize(this, onlineHelper, remotePlayer, aiPlayer);
    };

    public void addToLogs(String message) {
        // TODO: Max lines in log.
        String curr = logs.getText();
        logs.setText(curr + "\n" + message);
    }

    public void disconnect() {
        Connection connection = Connection.getInstance();
        connection.getInputHandler().off(ServerMessageType.MATCH, match);
        connection.getOutputHandler().logout();
        UIManager.switchScreen("home");
    }

    @Override
    public void endGame() {
        switch (currentGame.getState()) {
            case DRAW:
                drawCount++;
                draws.setText("Gelijk: " + drawCount);
                break;
            case PLAYER_ONE_WON:
                winCount++;
                wins.setText("Gewonnen: " + winCount);
                break;
            case PLAYER_TWO_WON:
                loseCount++;
                loses.setText("Verloren: " + loseCount);
                break;
            default:
                // My editor did not like that there was not a default case.
                break;
        }
    }

    @Override
    public void update(int move, int playerId) {
        addToLogs(move + " has been set by: " + playerId);
    }
}
