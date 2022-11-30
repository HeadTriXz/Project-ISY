package com.headtrixz.game;

import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.ui.GameController;
import helpers.TestCases.HasPlayerWonTestCase;
import helpers.Helpers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

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



    @TestFactory
    Stream<DynamicTest> hasPlayerWon() {
        // load test cases from currentPlayerWon.txt
        HasPlayerWonTestCase[] testCases =
                Helpers.generateHasPlayerWonTestCases();

        // create a dynamic test for each test case
        return Arrays.stream(testCases).map(testCase -> DynamicTest.dynamicTest(
                "hasPlayerWon() " + Arrays.toString(testCase.board) + ", " + testCase.player,
                () -> {
                    // set the board to the test case
                    game.getBoard().setCells(testCase.board);
                    // assert that the test case is correct
                    assertTrue(game.hasPlayerWon(game.getPlayer(testCase.player-1)));
                    assertFalse(game.hasPlayerWon(game.getOpponent(game.getPlayer(testCase.player-1))));
                }
        ));
    }
    @Test
    void CurrentPlayerWonTest() {
        int[] controllBoard = {1, 2, 1, 2, 1, 1, 2, 0, 1};
        game.getBoard().setCells(controllBoard);

        assertTrue(game.hasPlayerWon(game.getPlayer(0)));
        assertFalse(game.hasPlayerWon(game.getPlayer(1)));
    }


    @TestFactory
    Stream<DynamicTest> getScoreTest() {
        ArrayList<int[]> testCases = Helpers.generateTicTacToeBoards("src/test/resources/getScoreTestCases.txt");
        // add a testcase for every depth. store it in array instead of list for performace increse
        int cellCount = game.getBoard().getCellCount();
        assert testCases != null;
        int[][] muchoTestCases = new int[testCases.size() * cellCount][];


        for(int i=0; i < testCases.size(); i++) {
            int[] testCase = testCases.get(i);
            for (int j = 0; j < cellCount; j++) {
                muchoTestCases[i * cellCount + j] = testCase;
            }
        }


        final int[] testCaseCount = {0};
        return Arrays.stream(muchoTestCases).map(testCase -> {
            int depth = testCaseCount[0]++ % cellCount;

            return DynamicTest.dynamicTest("Test Case(" + testCaseCount[0] + "): " +
                    Arrays.toString(testCase) + ", " + depth +".", () -> {
                game.getBoard().setCells(testCase);


                final int score1 = game.getScore(game.getPlayer(0), depth, cellCount);
                final int score2 = game.getScore(game.getPlayer(1), depth, cellCount);

                final int finalScore = score1 + score2;

                if (game.getState() == GameModel.GameState.PLAYING || game.getState() == GameModel.GameState.DRAW) {
                    if (game.getBoard().getMove(4) != GameBoard.EMPTY_CELL) {
                        double depthPenalty = depth / 9f;
                        int midMoveScoreDiv = (int) (40 * depthPenalty);

                        assertEquals(midMoveScoreDiv, Math.abs(finalScore));
                    } else {
                        assertEquals(0, finalScore);
                    }
                } else {
                    assertEquals(0 , finalScore,
                            "Test case failed. " + Arrays.toString(testCase) + " at depth " + 1);
                }
            });
        });
    }

}