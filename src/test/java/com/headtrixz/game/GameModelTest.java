package com.headtrixz.game;

import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.ui.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    GameModel game;
    Player playerOne;
    Player playerTwo;


    @BeforeEach
    void setUp() {
        TicTacToe game = new TicTacToe();
        OfflineHelper helper = new OfflineHelper(game);

        this.playerOne = new HumanPlayer(game, "Humon");
        this.playerTwo = new HumanPlayer(game, "Compuper");


        GameController controller = new GameController();
        game.initialize(controller, helper, playerOne, playerTwo);


        this.game = game;
    }

    @Test
    void GetOpponentTest() {
        game.setCurrentPlayer(playerOne); // player one test
        assertEquals(game.getOpponent(), playerTwo);
        game.setCurrentPlayer(playerTwo); // player two test
        assertEquals(game.getOpponent(), playerOne);

    }

    @Test
    void GetOppenentOfPassedPlayerTest() {
        assertEquals(game.getOpponent(playerOne), playerTwo); //player one test
        assertEquals(game.getOpponent(playerTwo), playerOne); // player two test
    }

    @Test
    void CurrentPlayerWonTest() {
        int[] controllBoard = {1, 2, 1, 2, 1, 1, 2, 0, 1};
        game.getBoard().setCells(controllBoard);

        assertTrue(game.hasPlayerWon(game.getPlayer(0)));
        assertFalse(game.hasPlayerWon(game.getPlayer(1)));


    }
}