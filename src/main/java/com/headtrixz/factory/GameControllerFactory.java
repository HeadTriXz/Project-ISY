package com.headtrixz.factory;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.Othello;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.AIPlayer;
import com.headtrixz.game.players.HackyAIPlayer;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.ui.GameController;
import com.headtrixz.ui.UIManager;
import com.headtrixz.ui.util.GameType;

/**
 * Create a new game controller fresh from the factory.
 */
public class GameControllerFactory {

    /**
     * Create a new game controller for the FXML.
     *
     * @param game the name of the game.
     * @return The game controller.
     */
    public static GameController createGameController(GameType game) {
        String username = UIManager.getSetting("username");
        GameModel gameModel;
        switch (game) {
            case TicTacToe -> {
                gameModel = new TicTacToe();
            }

            case Othello -> {
                gameModel = new Othello();
            }

            default -> {
                return null;
            }
        }

        OfflineHelper helper = new OfflineHelper(gameModel);
        Player humanPlayer = new HumanPlayer(gameModel, username);
        Player aiPlayer = new AIPlayer(gameModel, "AI");
        GameController controller = new GameController(gameModel);

        gameModel.initialize(controller, helper, humanPlayer, aiPlayer);
        return controller;
    }
}
