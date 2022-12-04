package helpers.testCases;

/**
 * A class that represents a test case for the minimax.getMove method.
 */
public class MiniMaxTestCase {
    public int[] board;
    public int currentPlayerId;
    public int expectedMove;
    public String comment;

    public MiniMaxTestCase(int[] board, int currentPlayerId, int expectedMove, String comment) {
        this.board = board;
        this.currentPlayerId = currentPlayerId;
        this.expectedMove = expectedMove;
        this.comment = comment;
    }
}
