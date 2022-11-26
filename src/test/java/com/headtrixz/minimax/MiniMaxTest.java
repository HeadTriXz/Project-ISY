package com.headtrixz.minimax;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.ui.GameController;
import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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

    @Test
    @DisplayName("getMove(), minimax tictactoe.")
    void getMove() {
        // load all the test cases and expected moves
        ArrayList<TestCase> testCases = generateTestCases("src/test/resources/tictactoe.txt");
        int testCaseCounter = 1;
        MiniMax miniMax = new MiniMax(this.game);
        for (TestCase testCase : testCases) {

            game.getBoard().setCells(testCase.board);
            game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerID - 1));
            int move = miniMax.getMove();

            if (!testCase.expectedMoves.contains(move)) {
                miniMax.getMove();
            }

            assertTrue(testCase.expectedMoves.contains(miniMax.getMoveIterative(1000)),
                    "Test case(" + testCaseCounter + ") failed");
            testCaseCounter++;
        }
    }

    @Test
    @DisplayName("getMove(), minimax tictactoe, clean table")
    void getMoveCleanTable() {
        ArrayList<TestCase> testCases = generateTestCases("src/test/resources/tictactoe.txt");
        int testCaseCounter = 1;
        for (TestCase testCase : testCases) {
            MiniMax miniMax = new MiniMax(this.game);

            game.getBoard().setCells(testCase.board);
            game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerID - 1));
            int move = miniMax.getMove();

            if (!testCase.expectedMoves.contains(move)) {
                miniMax.getMove();
            }

            assertTrue(testCase.expectedMoves.contains(miniMax.getMoveIterative(1000)),
                    "Test case(" + testCaseCounter + ") failed");
            testCaseCounter++;
        }
    }

    @Test
    @DisplayName("getMoveItterative(), minimax tictactoe, itterative.")
    void getMoveItterativeTest() {
        ArrayList<TestCase> testCases = generateTestCases("src/test/resources/tictactoe.txt");
        int testCaseCounter = 1;

        MiniMax miniMax = new MiniMax(this.game);
        for (TestCase testCase : testCases) {

            game.getBoard().setCells(testCase.board);
            game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerID - 1));

            assertTrue(testCase.expectedMoves.contains(miniMax.getMoveIterative(1000)),
                    "Test case(" + testCaseCounter + ") failed");
            testCaseCounter++;
        }
    }

    @Test
    @DisplayName("getMoveItterative(), minimax tictactoe, itterative, cleaned table")
    void getMoveItterativeTestCleanTable() {
        ArrayList<TestCase> testCases = generateTestCases("src/test/resources/tictactoe.txt");
        int testCaseCounter = 1;

        for (TestCase testCase : testCases) {
            MiniMax miniMax = new MiniMax(this.game);

            game.getBoard().setCells(testCase.board);
            game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerID - 1));

            int move = miniMax.getMoveIterative(1000);

            if (!testCase.expectedMoves.contains(move)) {
                miniMax.getMoveIterative(1000);
            }

            assertTrue(testCase.expectedMoves.contains(miniMax.getMoveIterative(1000)),
                    "Test case(" + testCaseCounter + ") failed");
            testCaseCounter++;
        }
    }

    static ArrayList<String> readFile(String filePath) {
        try {
            ArrayList<String> lines = new ArrayList<>();
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
            return lines;
        } catch (FileNotFoundException e) {
            fail("Test cases file not found: " + filePath);
            return null;
        }
    }


    /**
     * Generate test cases from a file.
     *
     * @param textFile The file to read from.
     * @return An arrayList of test cases.
     */
    static ArrayList<TestCase> generateTestCases(String textFile) {
        ArrayList<String> fileLines = readFile(textFile);

        ArrayList<TestCase> testCases = new ArrayList<>(fileLines.size());
        for (String line : fileLines) {
            if (!line.startsWith("[")) {
                continue;
            }
            String[] lineParts = line.split("//");
            String comment = lineParts.length > 1 ? lineParts[1].trim() : "";

            String[] testCaseSplit = lineParts[0].trim().split(":");
            // parse string from Arrays.toString() to int array
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(testCaseSplit[0]);
            } catch (Exception e) {
                fail("Test case file is not in the correct format");
                return null;
            }
            // parse the actual test case.
            int[] board = jsonArray.toList().stream().mapToInt(i -> (int) i).toArray();
            ArrayList<Integer> expectedMoves = parseExpectedResults(testCaseSplit[2]);
            int currentPlayerID = Integer.parseInt(testCaseSplit[1]);

            testCases.add(new TestCase(board, currentPlayerID, expectedMoves, comment));
        }

        return testCases;
    }

    static private ArrayList<Integer> parseExpectedResults(String expectedResults) {
        ArrayList<Integer> expectedMoves = new ArrayList<>();
        String[] expectedResultsSplit = expectedResults.split(";");
        for (String expectedResult : expectedResultsSplit) {
            expectedMoves.add(Integer.parseInt(expectedResult));
        }
        return expectedMoves;
    }

    private static class TestCase {
        int[] board;
        int currentPlayerID;
        ArrayList<Integer> expectedMoves;
        String comment;

        public TestCase(int[] board, int currentPlayerID, ArrayList<Integer> expectedMoves,
                        String comment) {
            this.board = board;
            this.currentPlayerID = currentPlayerID;
            this.expectedMoves = expectedMoves;
            this.comment = comment;
        }
    }
}