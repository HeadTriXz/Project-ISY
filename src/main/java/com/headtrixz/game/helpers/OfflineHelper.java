package com.headtrixz.game.helpers;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import javafx.application.Platform;

public class OfflineHelper implements GameModelHelper {
    private final GameModel game;

    public OfflineHelper(GameModel game) {
        this.game = game;
    }

    public void forfeit() {
        // Does nothing.
    }

    @Override
    public GameModel.GameState getState() {
        return null;
    }

    @Override
    public void initialize() {
        nextTurn(game.getCurrentPlayer());
    }

    @Override
    public void nextTurn(Player player) {
        player.onTurn(m -> {
            if (!game.getBoard().isValidMove(m)) {
                return;
            }

            game.getBoard().setMove(m, player.getId());
            Platform.runLater(() -> {
                game.getController().update(m, player.getId());
            });

            if (game.getState() == GameModel.GameState.PLAYING) {
                nextTurn(game.nextPlayer());
            } else {
                Platform.runLater(() -> {
                    game.getController().endGame();
                });
            }
        });
    }
}
