package helpers;

public class MiniMaxTestCase {
    public int[] board;
    public int currentPlayerID;
    public int expectedMove;
    public String comment;

    public MiniMaxTestCase(int[] board, int currentPlayerID, int expectedMove, String comment) {
        this.board = board;
        this.currentPlayerID = currentPlayerID;
        this.expectedMove = expectedMove;
        this.comment = comment;
    }
}
