package helpers.testcases;


/**
 * class to store data related to testcases for hasPLayerWon.
 */
public class HasPlayerWonTestCase {
    public int[] board;
    public int player;

    public HasPlayerWonTestCase(int[] board, int player) {
        this.board = board;
        this.player = player;
    }
}
