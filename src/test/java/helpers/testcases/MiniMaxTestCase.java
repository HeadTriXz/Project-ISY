package helpers.testcases;

/**
 * A class that represents a test case for the minimax.getMove method.
 */
public class MiniMaxTestCase {
    public int[] board;
    public int currentPlayerId;
    public int expectedMove;
    public String comment;

    /**
     * Constructor for the MiniMaxTestCase class.
     *
     * @param board             The board to test.
     * @param currentPlayerId   The current player.
     * @param expectedMove      The expected move.
     * @param comment           The comment.
     */
    public MiniMaxTestCase(int[] board, int currentPlayerId, int expectedMove, String comment) {
        this.board = board;
        this.currentPlayerId = currentPlayerId;
        this.expectedMove = expectedMove;
        this.comment = comment;
    }
}
