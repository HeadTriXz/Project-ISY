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
import static org.junit.jupiter.api.Assertions.fail;

class TestMiniMaxTestTicTacToe {
    private MiniMax miniMax;
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
        this.miniMax = new MiniMax(game);
    }

    @Test
    @DisplayName("getMove(), minimax tictactoe.")
    void getMove() {
        // load all the test cases and expected moves
        ArrayList<TestCase> testCases = generateTestCases("src/test/resources/tictactoe.txt");
        int testCaseCounter = 1;
        for (TestCase testCase : testCases) {
            game.getBoard().setCells(testCase.board);
            game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerID - 1));
            int move = miniMax.getMove();

            if (move != testCase.expectedMove) {
                miniMax.getMove();
            }

            assertEquals(testCase.expectedMove, miniMax.getMove(),
                    "Test case(" + testCaseCounter + ") failed");
            testCaseCounter++;
        }
    }

    @Test
    @DisplayName("getMoveItterative(), minimax tictactoe, itterative.")
    void getMoveItterativeTest() {
        ArrayList<TestCase> testCases = generateTestCases("src/test/resources/tictactoe.txt");
        int testCaseCounter = 1;

        for (TestCase testCase : testCases) {
            game.getBoard().setCells(testCase.board);
            game.setCurrentPlayer(game.getPlayer(testCase.currentPlayerID - 1));


            assertEquals(testCase.expectedMove, miniMax.getMoveIterative(1000),
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
            int expectedMove = Integer.parseInt(testCaseSplit[2]);
            int currentPlayerID = Integer.parseInt(testCaseSplit[1]);

            testCases.add(new TestCase(board, currentPlayerID, expectedMove, comment));
        }

        return testCases;
    }

    private static class TestCase {
        int[] board;
        int currentPlayerID;
        int expectedMove;
        String comment;

        public TestCase(int[] board, int currentPlayerID, int expectedMove, String comment) {
            this.board = board;
            this.currentPlayerID = currentPlayerID;
            this.expectedMove = expectedMove;
            this.comment = comment;
        }
    }
}