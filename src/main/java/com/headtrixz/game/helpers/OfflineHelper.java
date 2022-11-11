package com.headtrixz.game.helpers;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import javafx.application.Platform;

public class OfflineHelper implements GameModelHelper {
    private final GameModel game;
    private GameModel.GameState state;

    public OfflineHelper(GameModel game) {
        this.game = game;
    }

    public void forfeit() {
        this.state = GameModel.GameState.PLAYER_TWO_WON;
    }

    @Override
    public GameModel.GameState getState() {
        return state;
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
