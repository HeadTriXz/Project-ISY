package com.headtrixz.ui;

import com.headtrixz.game.Othello;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.game.players.AiPlayer;
import com.headtrixz.ui.util.GameType;

public class GameControllerFactory {

    /**
     * Create a new game controller for the FXML.
     *
     * @param game the name of the game.
     * @return The game controller.
     */
    public static GameController createGameController(GameType game) {
        String username = UIManager.getSetting("username");
        String aiName = "AI";

        switch (game) {
            case TicTacToe -> {
                TicTacToe gameModel = new TicTacToe();
                OfflineHelper helper = new OfflineHelper(gameModel);
                Player humanPlayer = new HumanPlayer(gameModel, username);
                Player aiPlayer = new AiPlayer(gameModel, aiName);
                GameController controller = new GameController(gameModel);

                gameModel.initialize(controller, helper, humanPlayer, aiPlayer);
                return controller;
            }

            case Othello -> {
                Othello gameModel = new Othello();
                OfflineHelper helper = new OfflineHelper(gameModel);
                Player humanPlayer = new HumanPlayer(gameModel, username);
                Player aiPlayer = new AiPlayer(gameModel, aiName);
                GameController controller = new GameController(gameModel);

                gameModel.initialize(controller, helper, humanPlayer, aiPlayer);
                return controller;
            }
        }

        return null;
    }
}
