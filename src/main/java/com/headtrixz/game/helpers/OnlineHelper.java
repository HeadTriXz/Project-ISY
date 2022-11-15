package com.headtrixz.game.helpers;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import com.headtrixz.game.players.RemotePlayer;
import com.headtrixz.networking.Connection;
import com.headtrixz.networking.InputHandler;
import com.headtrixz.networking.ServerMessage;
import com.headtrixz.networking.ServerMessageType;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.function.Consumer;

public class OnlineHelper implements GameModelHelper {
    private Connection connection;
    private GameModel game;
    private Player localPlayer;
    private GameModel.GameState state;

    public OnlineHelper(GameModel game) {
        this.game = game;
    }

    public void forfeit() {
        unsubscribeAll();
        connection.getOutputHandler().forfeit();
    }

    private void endGame() {
        unsubscribeAll();
        Platform.runLater(() -> {
            game.getController().endGame();
        });
    }

    @Override
    public GameModel.GameState getState() {
        return state;
    }

    @Override
    public void initialize() {
        this.connection = Connection.getInstance();
        this.localPlayer = game.getPlayer(0) instanceof RemotePlayer
                ? game.getPlayer(1)
                : game.getPlayer(0);

        InputHandler input = connection.getInputHandler();
        input.on(ServerMessageType.MOVE, onMove);
        input.on(ServerMessageType.DRAW, onDraw);
        input.on(ServerMessageType.LOSS, onLoss);
        input.on(ServerMessageType.WIN, onWin);
        input.on(ServerMessageType.YOURTURN, onYourTurn);
    }

    @Override
    public void nextTurn(Player player) {
        game.setCurrentPlayer(player);

        if (player == localPlayer) {
            player.onTurn(m -> {
                if (m == -1) {
                    return;
                }

                connection.getOutputHandler().move(m);
            });
        }
    }

    private final Consumer<ServerMessage> onMove = message -> {
        HashMap<String, String> obj = message.getObject();
        Player player = game.getPlayer(obj.get("PLAYER"));
        int move = Integer.parseInt(obj.get("MOVE"));

        game.getBoard().setMove(move, player.getId());
        Platform.runLater(() -> {
            game.getController().update(move, player);
        });

        if (player == localPlayer) {
            nextTurn(game.getOpponent());
        }
    };

    private final Consumer<ServerMessage> onDraw = message -> {
        state = GameModel.GameState.DRAW;
        endGame();
    };

    private final Consumer<ServerMessage> onLoss = message -> {
        state = localPlayer.getId() == 1
                ? GameModel.GameState.PLAYER_TWO_WON
                : GameModel.GameState.PLAYER_ONE_WON;

        endGame();
    };

    private final Consumer<ServerMessage> onWin = message -> {
        state = localPlayer.getId() == 1
                ? GameModel.GameState.PLAYER_ONE_WON
                : GameModel.GameState.PLAYER_TWO_WON;

        endGame();
    };

    private final Consumer<ServerMessage> onYourTurn = message -> {
        nextTurn(localPlayer);
    };

    private void unsubscribeAll() {
        InputHandler input = connection.getInputHandler();
        input.off(ServerMessageType.MOVE, onMove);
        input.off(ServerMessageType.DRAW, onDraw);
        input.off(ServerMessageType.LOSS, onLoss);
        input.off(ServerMessageType.WIN, onWin);
        input.off(ServerMessageType.YOURTURN, onYourTurn);
    }
}
