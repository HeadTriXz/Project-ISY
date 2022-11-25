package com.headtrixz.minimax;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * The minimax algorithm.
 */
public class MiniMax {
    public static final int MAX_DEPTH = 8;

    private final GameModel game;
    private final Map<GameBoard, TranspositionEntry> transpositionTable;

    public MiniMax(GameModel game) {
        this.game = game;
        this.transpositionTable = new HashMap<>();
    }

    /**
     * get the best next move for any given player based on the current game state.
     * the game should be advanced to the next player for it to work correctly.
     * this function works using the current board and does not work on a clone of the board
     * because it uses the state of the game determining the score of the board
     *
     * @return the best move to make given the current game state
     */
    public int getMove() {
        return getMove(MAX_DEPTH);
    }

    /**
     * get the best next move for any given player based on the current game state.
     * the game should be advanced to the next player for it to work correctly.
     * this function works using the current board and does not work on a clone of the
     * board because it uses the state
     * of the game determining the score of the board
     *
     * @param maxDepth the maximum depth to look to
     * @return the best move to make given the current game state
     */
    public int getMove(int maxDepth) {
        return minimax(0, game.getCurrentPlayer(), maxDepth, game.getMinScore(), game.getMaxScore(),
                1);
    }

    /**
     * get the best move for the current player given the current game state
     * within the given time limit.
     *
     * @param maxMillis the time limit in milliseconds
     * @return the best move to make given the current game state
     */
    public int getMoveIterative(int maxMillis) {
        final int maxIterations = game.getBoard().getValidMoves().size();

        final LinkedList<Integer> moves = new LinkedList<>();
        moves.addLast(getMove(0));

        Thread work = new Thread(() -> {
            for (int i = 1; i <= maxIterations; i++) {
                moves.addLast(getMove(i));
            }
        });

        work.start();
        try {
            work.join(maxMillis);
        } catch (InterruptedException ignored) {
            // ignored
        }

        return moves.getLast();
    }

    /**
     * the algorithm that actually gets the correct next move.
     *
     * @param depth         the current depth
     * @param currentPlayer the player that is currently at play
     * @return best move to make if depth == 0, else the score of the board
     */
    private int minimax(int depth, Player currentPlayer, int maxDepth, int alpha, int beta,
                        int color) throws IllegalStateException {


        final int alphaOriginal = alpha;

        TranspositionEntry entry = transpositionTable.get(game.getBoard());
        if (entry != null && entry.depth() > depth && depth > 0) {
            switch (entry.flag()) {
                case EXACT -> {
                    return entry.value();
                }
                case LOWER_BOUND -> alpha = Math.max(entry.value(), alpha);
                case UPPER_BOUND -> beta = Math.min(entry.value(), beta);
                default -> throw new IllegalStateException("Unexpected value: " + entry.flag());
            }

            if (alpha >= beta) {
                return entry.value();
            }
        }

        // check if the game is finished or the max depth has been reached
        if (game.getState() != GameModel.GameState.PLAYING || depth >= maxDepth) {
            return game.getScore(currentPlayer, depth) * color;
        }

        Map<Integer, Integer> potentialOutcomes = new HashMap<>();

        //get the opponent. the id of current player is +1 of the index in players array
        // so by passing the currentPlayers id we get the next player
        Player opp = game.getOpponent(currentPlayer);

        int max = Integer.MIN_VALUE;
        // iterate over all valid moves and calculate there score
        for (int move : game.cloneBoard().getValidMoves()) {
            game.getBoard().setMove(move, currentPlayer.getId());

            int score = -minimax(depth + 1, opp, maxDepth, -beta, -alpha, -color);
            potentialOutcomes.put(move, score);

            game.getBoard().setMove(move, 0);

            // check if we already have a board with the max score.
            alpha = Math.max(alpha, score);
            max = Math.max(max, score);
            if (alpha >= beta) {
                break;
            }
        }

        // return the move if we are at the base call or the score if we are in a recursive call
        if (depth == 0) {
            return getBestOutcome(potentialOutcomes).getKey();
        }

        if (max <= alphaOriginal) {
            entry = new TranspositionEntry(max, depth, TranspositionEntry.Flags.UPPER_BOUND);
        } else if (max >= beta) {
            entry = new TranspositionEntry(max, depth, TranspositionEntry.Flags.LOWER_BOUND);
        } else {
            entry = new TranspositionEntry(max, depth, TranspositionEntry.Flags.EXACT);
        }

        transpositionTable.put(game.getBoard(), entry);
        return max;
    }

    /**
     * get the entry in the map with the best score.
     *
     * @param boardScores the map with boardScores and moves
     * @return the map entry with the best score
     */
    private static Map.Entry<Integer, Integer> getBestOutcome(Map<Integer, Integer> boardScores) {
        return boardScores.entrySet().stream().max(Map.Entry.comparingByValue()).get();
    }
}
