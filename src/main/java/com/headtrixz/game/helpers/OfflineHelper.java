package com.headtrixz.game.helpers;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import javafx.application.Platform;

/**
 * Represents a helper class that handles the game logic for an offline game.
 */
public class OfflineHelper implements GameModelHelper {
    private final GameModel game;
    private GameModel.GameState state;

    /**
     * Represents a helper class that handles the game logic for an offline game.
     *
     * @param game The game the helper is for.
     */
    public OfflineHelper(GameModel game) {
        this.game = game;
    }

    /**
     * Ends the game when the player forfeits.
     */
    @Override
    public void forfeit() {
        this.state = GameModel.GameState.PLAYER_TWO_WON;
    }

    /**
     * @return The current state of the game.
     */
    @Override
    public GameModel.GameState getState() {
        return state;
    }

    /**
     * Initializes the helper.
     */
    @Override
    public void initialize() {
        nextTurn(game.getCurrentPlayer());
    }


    /**
     * Tells the player it's their turn and handles their move.
     *
     * @param player The player whose turn it is.
     */
    @Override
    public void nextTurn(Player player) {
        player.onTurn(m -> {
            if (!game.getBoard().isValidMove(m)) {
                return;
            }

            game.getBoard().setMove(m, player.getId());
            Platform.runLater(() -> {
                game.getController().update(m, player);
            });

            if (game.getState() == GameModel.GameState.PLAYING) {
                game.setCurrentPlayer(game.getOpponent());
                nextTurn(game.getCurrentPlayer());
            } else {
                Platform.runLater(() -> {
                    game.getController().endGame();
                });
            }
        });
    }
}
