package com.headtrixz.factory;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.Othello;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.AIPlayer;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.ui.GameController;
import com.headtrixz.ui.UIManager;

/**
 * Create a new game controller fresh from the factory.
 */
public class GameControllerFactory {
    /**
     * The different games.
     */
    public enum GameType {
        TicTacToe, Othello,
    }

    /**
     * Create a new game controller for the FXML.
     *
     * @param type The type of game.
     * @return The game controller.
     */
    public static GameController createGameController(GameType type) {
        String username = UIManager.getSetting("username");
        GameModel game = switch (type) {
            case TicTacToe -> new TicTacToe();
            case Othello -> new Othello();
        };

        GameController controller = new GameController(game);
        OfflineHelper helper = new OfflineHelper(controller, game);
        Player playerOne = new AIPlayer(game, username, MiniMaxFactory.MiniMaxType.MiniMaxAlphaBeta);
        Player playerTwo = new AIPlayer(game, "AI", MiniMaxFactory.MiniMaxType.MiniMaxOptimized);

        game.initialize(helper, playerOne, playerTwo);
        return controller;
    }
}
