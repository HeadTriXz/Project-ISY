package com.headtrixz.ui;

import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.game.players.TicTacToeAI;

public class GameControllerFactory {
    /**
     * Create a new game controller for the FXML.
     *
     * @param game the name of the game.
     * @return The game controller.
     */
    public GameController createGameController(String game) {
        String username = "Human";
        String aiName = "AI";

        switch (game) {
            case "tic-tac-toe" -> {
                TicTacToe gameModel = new TicTacToe();
                OfflineHelper helper = new OfflineHelper(gameModel);
                Player humanPlayer = new HumanPlayer(gameModel, username);
                Player aiPlayer = new TicTacToeAI(gameModel, aiName);
                GameController controller = new GameController(gameModel);
                gameModel.initialize(controller, helper, humanPlayer, aiPlayer);

                return controller;
            }
        }

        return null;
    }
}
