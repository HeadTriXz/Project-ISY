package com.headtrixz.minimax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.ui.GameController;
import helpers.Helpers;
import helpers.testcases.MiniMaxTestCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class TestMiniMaxTestTicTacToe {
    private GameModel game;

    @BeforeEach
    void setUp() {
        TicTacToe game = new TicTacToe();
        OfflineHelper helper = new OfflineHelper(game);

        Player playerOne = new HumanPlayer(game, "Humon");
        Player playerTwo = new HumanPlayer(game, "Compuper");

        GameController controller = new GameController();
        game.initialize(controller, helper, playerOne, playerTwo);

        this.game = game;
    }

    @TestFactory
    Stream<DynamicTest> getMoveTest() {
        // get all tests
        ArrayList<MiniMaxTestCase> testCases =
                Helpers.loadMiniMaxTestCases("src/test/resources/tictactoe.txt");

        // get the minimax instance
        MiniMax miniMax = new MiniMax(this.game);

        // keep count of amount of tests done
        final int[] testCaseCount = {1};

        // generate a dynamic test for each test case
        assert testCases != null;
        return testCases.stream().map(testCase -> DynamicTest.dynamicTest(
                "Test Case(" + testCaseCount[0] + "): " + Arrays.toString(testCase.board)
                + ", player: " + testCase.currentPlayerId + ".", () -> {
                    game.getBoard().setCells(testCase.board);
                    game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerId - 1));

                    assertEquals(testCase.expectedMove, miniMax.getMove(),
                            "Test case(" + testCaseCount[0]++ + ") failed.");
                }
            )
        );
    }


    @TestFactory
    Stream<DynamicTest> getMoveCleanTableTest() {
        ArrayList<MiniMaxTestCase> testCases =
                Helpers.loadMiniMaxTestCases("src/test/resources/tictactoe.txt");


        // keep count of amount of tests done
        final int[] testCaseCount = {1};

        // generate a dynamic test for each test case
        assert testCases != null;
        return testCases.stream().map(testCase -> {
            // create a new minimax instance (simulate first move of game)
            MiniMax miniMax = new MiniMax(this.game);
            return DynamicTest.dynamicTest(
                    "Test Case(" + testCaseCount[0] + "): " + Arrays.toString(testCase.board)
                            + ", player: " + testCase.currentPlayerId + ".", () -> {
                        game.getBoard().setCells(testCase.board);
                        game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerId - 1));
                        assertEquals(testCase.expectedMove, miniMax.getMove(),
                                "Test case(" + testCaseCount[0]++ + ") failed.");
                    }
                );
            }
        );
    }


    @TestFactory
    Stream<DynamicTest> getMoveIterativeTest() {
        ArrayList<MiniMaxTestCase> testCases =
                Helpers.loadMiniMaxTestCases("src/test/resources/tictactoe.txt");

        // get the minimax instance
        MiniMax miniMax = new MiniMax(this.game);

        // keep count of amount of tests done
        final int[] testCaseCount = {1};

        // generate a dynamic test for each test case
        assert testCases != null;
        return testCases.stream().map(testCase -> DynamicTest.dynamicTest(
                "Test Case(" + testCaseCount[0]++ + "): " + Arrays.toString(testCase.board)
                        + ", player: " + testCase.currentPlayerId + ".", () -> {
                    game.getBoard().setCells(testCase.board);
                    game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerId - 1));

                    assertEquals(testCase.expectedMove, miniMax.getMoveIterative(1000),
                            "Test case(" + testCaseCount[0] + ") failed.");
                }
            )
        );
    }


    @TestFactory
    Stream<DynamicTest> getMoveIterativeCleanTableTest() {
        ArrayList<MiniMaxTestCase> testCases =
                Helpers.loadMiniMaxTestCases("src/test/resources/tictactoe.txt");

        // keep count of amount of tests done
        final int[] testCaseCount = {1};

        // generate a dynamic test for each test case
        assert testCases != null;
        return testCases.stream().map(testCase -> {
            // create a new minimax instance (simulate first move of game)
            MiniMax miniMax = new MiniMax(this.game);
            return DynamicTest.dynamicTest(
                    "Test Case(" + testCaseCount[0]++ + "): " + Arrays.toString(testCase.board)
                            + ", player: " + testCase.currentPlayerId + ".", () -> {
                        game.getBoard().setCells(testCase.board);
                        game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerId - 1));

                        assertEquals(testCase.expectedMove, miniMax.getMoveIterative(1000),
                                "Test case(" + testCaseCount[0] + ") failed.");
                    }
                );
            }
        );
    }


    @Test
    @Description("Test custom hash for uniqueness across all valid games and player combinations")
    void hashBoardAndPlayerTest() {
        ArrayList<int[]> boards =
                Helpers.generateTicTacToeBoards("src/test/resources/getScoreTestCases.txt");


        ArrayList<Long> hashes = new ArrayList<>();
        assert boards != null;
        for (int[] board : boards) {
            game.getBoard().setCells(board);
            for (int i = 0; i < 2; i++) {
                game.setCurrentPlayer(game.getPlayer(i));
                long hash = TranspositionEntry.createHash(game.getBoard(), game.getPlayer(i));
                assertFalse(hashes.contains(hash),
                        "Hash function returned a duplicate hash.");
                hashes.add(hash);
            }
        }
    }


}