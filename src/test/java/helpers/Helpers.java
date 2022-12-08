package helpers;

import static org.junit.jupiter.api.Assertions.fail;

import helpers.testcases.HasPlayerWonTestCase;
import helpers.testcases.MiniMaxTestCase;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;

/**
 * A class that contains helper methods for testing.
 */
public class Helpers {
    /**
     * Read all lines of text in a file and put it into an arraylist.
     *
     * @param filePath the path to the file.
     * @return an arraylist of strings.
     */
    public static ArrayList<String> readFile(String filePath) {
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
    public static ArrayList<MiniMaxTestCase> loadMiniMaxTestCases(String textFile) {
        ArrayList<String> fileLines = readFile(textFile);

        assert fileLines != null;
        ArrayList<MiniMaxTestCase> testCases = new ArrayList<>(fileLines.size());
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
            String[] movesString = testCaseSplit[2].split(";");
            int[] moves = new int[movesString.length];
            for (int i = 0; i < movesString.length; i++) {
                moves[i] = Integer.parseInt(movesString[i]);
            }

            int currentPlayerID = Integer.parseInt(testCaseSplit[1]);

            testCases.add(new MiniMaxTestCase(board, currentPlayerID, moves, comment));
        }

        return testCases;
    }

    /**
     * Generate test cases from a file.
     *
     * @param textFile The file to read from.
     * @return An arrayList of test cases.
     */
    public static ArrayList<int[]> generateTicTacToeBoards(String textFile) {
        ArrayList<String> fileLines = readFile(textFile);

        assert fileLines != null;
        ArrayList<int[]> testCases = new ArrayList<>(fileLines.size());
        for (String line : fileLines) {
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(line);
            } catch (Exception e) {
                fail("Test case file is not in the correct format");
                return null;
            }
            // parse the actual test case.
            int[] board = jsonArray.toList().stream().mapToInt(i -> (int) i).toArray();

            testCases.add(board);
        }

        return testCases;
    }

    /**
     * generate an array of valid boards for tictactoe.
     * valid means only 1 winner and not a difference between made moves of 0 or 1
     *
     * @return all valid boards.
     */
    private static int[][] generateAllPossibleBoards() {
        return generatePermutations(9).toArray(new int[][] {});
    }

    /**
     * generate all permutations of a given length.
     *
     * @param n the length of the permutations.
     * @return all permutations.
     */
    public static List<int[]> generatePermutations(int n) {
        List<int[]> permutations = new ArrayList<>();

        if (n == 0) {
            permutations.add(new int[0]);
            return permutations;
        }

        for (int i = 0; i < 3; i++) {
            List<int[]> subPermutations = generatePermutations(n - 1);
            for (int[] subPerm : subPermutations) {
                int[] newPerm = new int[n];
                newPerm[0] = i;
                System.arraycopy(subPerm, 0, newPerm, 1, n - 1);
                permutations.add(newPerm);
            }
        }

        return permutations;
    }

    /**
     * Generate test cases for hasPlayerWon.
     *
     * @return array of testCases
     */
    public static HasPlayerWonTestCase[] generateHasPlayerWonTestCases() {
        int[][] permutations = generateAllPossibleBoards();

        for (int i = 0; i < permutations.length; i++) {
            int[] permutation = permutations[i];

            int player1 = 0;
            int player2 = 0;
            for (int k : permutation) {
                if (k == 1) {
                    player1++;
                } else if (k == 2) {
                    player2++;
                }
            }

            if (Math.abs(player1 - player2) > 1
                || !onlyOneTrue(hasWonArr(1, permutation), hasWonArr(2, permutation))) {
                permutations[i] = null;
            }

        }

        // move all value sof null to the end of the array
        int i = 0;
        for (int k = 0; k < permutations.length; k++) {
            if (permutations[k] == null) {
                continue;
            }
            permutations[i++] = permutations[k];
        }

        return Arrays.stream(Arrays.copyOf(permutations, i)).map(ints -> {
            int player = hasWonArr(1, ints) ? 1 : 2;
            return new HasPlayerWonTestCase(ints, player);
        }).toArray(HasPlayerWonTestCase[]::new);
    }

    /**
     * check if only one of the values is true.
     *
     * @param a values to check
     * @param b values to check
     * @return true if only one of the values is true.
     */
    public static boolean onlyOneTrue(boolean a, boolean b) {
        return (a && !b) || (!a && b);
    }

    /**
     * check if a player has won.
     *
     * @param player player to check
     * @param board  board to check
     * @return true if the player has won.
     */
    private static boolean hasWonArr(int player, int[] board) {
        // check if the player has won the game of tictactoe
        // check rows
        for (int i = 0; i < 3; i++) {
            if (board[i * 3] == player && board[i * 3 + 1] == player
                && board[i * 3 + 2] == player) {
                return true;
            }
        }

        // check columns
        for (int i = 0; i < 3; i++) {
            if (board[i] == player && board[i + 3] == player && board[i + 6] == player) {
                return true;
            }
        }

        // check diagonals
        if (board[0] == player && board[4] == player && board[8] == player) {
            return true;
        }

        return board[2] == player && board[4] == player && board[6] == player;
    }
}
