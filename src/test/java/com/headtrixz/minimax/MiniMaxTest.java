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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TestMiniMaxTestTicTacToe {
    private final MiniMax miniMax;
    private final GameModel game;

    public TestMiniMaxTestTicTacToe() {

        TicTacToe game = new TicTacToe();
        OfflineHelper helper = new OfflineHelper(game);

        Player playerOne = new HumanPlayer(game, "Humon");
        Player playerTwo = new HumanPlayer(game, "Compuper");


        GameController controller = new GameController();
        game.initialize(controller, helper, playerOne, playerTwo);


        this.game = game;
        this.miniMax = new MiniMax(game);
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.DisplayName("getMove(), minimax tictactoe.")
    void getMove() {
        // load all the test cases and expected moves
        ArrayList<String> testCases = readFile("src/test/resources/tictactoe.txt");
        int testCaseCounter = 1;
        for (String testCase : testCases) {

            String[] testCaseSplit = testCase.split(":");
            // parse string from Arrays.toString() to int array
            JSONArray jsonArray = new JSONArray(testCaseSplit[0]);
            int[] board = jsonArray.toList().stream().mapToInt(i -> (int)i).toArray();

            game.getBoard().setCells(board);
            int expectedMove = Integer.parseInt(testCaseSplit[2]);
            int currentPlayerID = Integer.parseInt(testCaseSplit[1]);

            game.setCurrentPlayer(game.getPlayer(currentPlayerID-1));

            assertEquals(expectedMove, miniMax.getMove(), "Test case("+testCaseCounter+") failed");
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
        } catch(FileNotFoundException e) {
            fail("Test cases file not found: " + filePath);
            return null;
        }
    }
}