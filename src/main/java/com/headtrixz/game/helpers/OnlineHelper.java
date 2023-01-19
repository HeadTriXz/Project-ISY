package com.headtrixz.game.helpers;

import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import com.headtrixz.game.players.RemotePlayer;
import com.headtrixz.networking.Connection;
import com.headtrixz.networking.InputHandler;
import com.headtrixz.networking.InputListener;
import com.headtrixz.networking.ServerMessageType;
import java.util.Map;
import javafx.application.Platform;


/**
 * Represents a helper class that handles the game logic for an online game.
 */
public class OnlineHelper extends GameModelHelper {
    private Connection connection;
    private Player localPlayer;
    private GameModel.GameState state;

    /**
     * Represents a helper class that handles the game logic for an online game.
     *
     * @param controller The controller of the game.
     * @param game The game the helper is for.
     */
    public OnlineHelper(GameMethods controller, GameModel game) {
        super(controller, game);
    }

    /**
     * Ends the game and heads to the finish screen.
     */
    private void endGame() {
        unsubscribeAll();
        Platform.runLater(controller::endGame);
    }

    /**
     * Ends the game when the player forfeits.
     */
    @Override
    public void forfeit() {
        unsubscribeAll();
        connection.getOutputHandler().forfeit();
    }

    /**
     * Returns the current state of the game.
     *
     * @return The current state of the game.
     */
    @Override
    public GameModel.GameState getState() {
        return state;
    }

    /**
     * Returns the local player (you).
     *
     * @return The local player.
     */
    @Override
    public Player getLocalPlayer() {
        return localPlayer;
    }

    /**
     * Initializes the helper.
     */
    @Override
    public void initialize() {
        this.connection = Connection.getInstance();
        this.localPlayer = game.getPlayer(0) instanceof RemotePlayer
                ? game.getPlayer(1)
                : game.getPlayer(0);

        InputHandler input = connection.getInputHandler();
        input.subscribe(ServerMessageType.MOVE, onMove);
        input.subscribe(ServerMessageType.DRAW, onDraw);
        input.subscribe(ServerMessageType.LOSS, onLoss);
        input.subscribe(ServerMessageType.WIN, onWin);
        input.subscribe(ServerMessageType.YOURTURN, onYourTurn);
    }

    /**
     * Tells the player it's their turn and handles their move.
     *
     * @param player The player whose turn it is.
     */
    @Override
    public void nextTurn(Player player) {
        if (!game.hasValidMoves(player.getId())) {
            return;
        }

        game.setCurrentPlayer(player);

        if (player == localPlayer) {
            player.onTurn(m -> {
                System.out.println("Move: " + m);
                if (m == -1) {
                    return;
                }

                connection.getOutputHandler().move(m);
            });
        }
    }

    /**
     * A listener for the "SVR GAME MOVE" event.
     */
    private final InputListener onMove = message -> {
        Map<String, String> obj = message.getObject();
        Player player = game.getPlayer(obj.get("PLAYER"));
        int move = Integer.parseInt(obj.get("MOVE"));

        game.setMove(move, player.getId());
        Platform.runLater(() -> {
            controller.update(move, player);
        });

        if (player == localPlayer) {
            nextTurn(game.getOpponent(localPlayer));
        }
    };

    /**
     * A listener for the "SVR GAME DRAW" event.
     */
    private final InputListener onDraw = message -> {
        state = GameModel.GameState.DRAW;
        endGame();
    };

    /**
     * A listener for the "SVR GAME LOSS" event.
     */
    private final InputListener onLoss = message -> {
        state = localPlayer.getId() == 1
                ? GameModel.GameState.PLAYER_TWO_WON
                : GameModel.GameState.PLAYER_ONE_WON;

        endGame();
    };

    /**
     * A listener for the "SVR GAME WIN" event.
     */
    private final InputListener onWin = message -> {
        state = localPlayer.getId() == 1
                ? GameModel.GameState.PLAYER_ONE_WON
                : GameModel.GameState.PLAYER_TWO_WON;

        endGame();
    };

    /**
     * A listener for the "SVR GAME YOURTURN" event.
     */
    private final InputListener onYourTurn = message -> {
        nextTurn(localPlayer);
    };

    /**
     * Unsubscribes all listeners.
     */
    private void unsubscribeAll() {
        InputHandler input = connection.getInputHandler();
        input.unsubscribe(ServerMessageType.MOVE, onMove);
        input.unsubscribe(ServerMessageType.DRAW, onDraw);
        input.unsubscribe(ServerMessageType.LOSS, onLoss);
        input.unsubscribe(ServerMessageType.WIN, onWin);
        input.unsubscribe(ServerMessageType.YOURTURN, onYourTurn);
    }
}
