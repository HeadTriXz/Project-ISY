package com.headtrixz.minimax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

/**
 * A class that tests the MiniMax algorithm.
 */
class MiniMaxTest {
    /**
     * Tests specifically for tic-tac-toe.
     */
    static class TestNegaMaxTestTicTacToe {
        private GameModel game;

        @BeforeEach
        void setUp() {
            TicTacToe game = new TicTacToe();
            GameController controller = new GameController(game);
            OfflineHelper helper = new OfflineHelper(controller, game);

            Player playerOne = new HumanPlayer(game, "Humon");
            Player playerTwo = new HumanPlayer(game, "Compuper");

            game.initialize(helper, playerOne, playerTwo);

            this.game = game;
        }

        @TestFactory
        Stream<DynamicTest> getMoveTest() {
            // get all tests
            ArrayList<MiniMaxTestCase> testCases =
                Helpers.loadMiniMaxTestCases("src/test/resources/tictactoe.txt");

            // get the minimax instance
            NegaMax miniMax = new NegaMax(this.game);

            // keep count of amount of tests done
            final int[] testCaseCount = {1};

            // generate a dynamic test for each test case
            assert testCases != null;
            return testCases.stream().map(testCase -> DynamicTest.dynamicTest(
                    "Test Case(" + testCaseCount[0] + "): " + Arrays.toString(testCase.board)
                        + ", player: " + testCase.currentPlayerId + ".", () -> {
                        game.getBoard().setCells(testCase.board);
                        game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerId - 1));

                        if (testCaseCount[0] == 42) {
                            System.out.println("test");
                        }

                        assertTrue(testCase.expectedMoves.contains(miniMax.getMove()),
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
                    NegaMax miniMax = new NegaMax(this.game);
                    return DynamicTest.dynamicTest(
                        "Test Case(" + testCaseCount[0] + "): " + Arrays.toString(testCase.board)
                            + ", player: " + testCase.currentPlayerId + ".", () -> {
                            game.getBoard().setCells(testCase.board);
                            game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerId - 1));
                            assertTrue(testCase.expectedMoves.contains(miniMax.getMove()),
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
            NegaMax miniMax = new NegaMax(this.game);

            // keep count of amount of tests done
            final int[] testCaseCount = {1};

            // generate a dynamic test for each test case
            assert testCases != null;
            return testCases.stream().map(testCase -> DynamicTest.dynamicTest(
                    "Test Case(" + testCaseCount[0]++ + "): " + Arrays.toString(testCase.board)
                        + ", player: " + testCase.currentPlayerId + ".", () -> {
                        game.getBoard().setCells(testCase.board);
                        game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerId - 1));

                    assertTrue(testCase.expectedMoves.contains(miniMax.getMoveIterative(1000)),
                        "Test case(" + testCaseCount[0]++ + ") failed.");
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
                NegaMax miniMax = new NegaMax(this.game);
                return DynamicTest.dynamicTest("Test Case(" + testCaseCount[0]++ + "): "
                        + Arrays.toString(testCase.board) + ", player: " + testCase.currentPlayerId
                        + ".", () -> {
                        game.getBoard().setCells(testCase.board);
                        game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerId - 1));

                        assertTrue(testCase.expectedMoves.contains(miniMax.getMoveIterative(1000)),
                            "Test case(" + testCaseCount[0]++ + ") failed.");
                        }
                );
            });
        }

        @Test
        void hashBoardAndPlayerUniquenessTest() {
            ArrayList<int[]> boards =
                Helpers.generateTicTacToeBoards(
                    "src/test/resources/getScoreTestCases.txt");

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

        @TestFactory
        Stream<DynamicTest> hashBoardAndPlayerSameResultTest() {
            ArrayList<int[]> boards =
                Helpers.generateTicTacToeBoards(
                    "src/test/resources/getScoreTestCases.txt");

            // keep count of amount of tests done
            final int[] testCaseCount = {1};

            return boards.stream().map(testCase -> {
                return DynamicTest.dynamicTest(
                    "Test Case(" + testCaseCount[0]++ + "): " + Arrays.toString(testCase) + ".",
                    () -> {
                        game.getBoard().setCells(testCase);
                        GameModel cloneGame = game.clone();
                        for (int i = 0; i < 2; i++) {
                            long hash = TranspositionEntry.createHash(game.getBoard(),
                                game.getPlayer(i));
                            long hash2 = TranspositionEntry.createHash(cloneGame.getBoard(),
                                cloneGame.getPlayer(i));
                            assertEquals(hash, hash2,
                                "Hash function returned a different hash"
                                    + " for the same board and player.");
                        }
                    }
                );
            });
        }
    }
}
