package com.headtrixz.minimax;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.players.Player;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * The minimax algorithm.
 */
public class MiniMax {
    private final GameModel game;
    private final Map<Integer, TranspositionEntry> transpositionTable;

    public MiniMax(GameModel game) {
        this.game = game;
        this.transpositionTable = new HashMap<>();
    }

    /**
     * get the best next move for any given player based on the current game state.
     * the game should be advanced to the next player for it to work correctly.
     *
     * @return the best move to make given the current game state
     */
    public int getMove() {
        return getMove(game.getBoard().getValidMoves().size());
    }

    /**
     * get the best next move for any given player based on the current game state.
     * the game should be advanced to the next player for it to work correctly.
     *
     * @param maxDepth the maximum layers to search
     * @return the best move to make given the current game state
     */
    public int getMove(int maxDepth) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;


        if (game instanceof TicTacToe) {
            // check if we can win in one  move
            for (int move : game.getBoard().getValidMoves()) {
                game.getBoard().setMove(move, game.getCurrentPlayer().getId());
                if (game.hasPlayerWon(game.getCurrentPlayer())) {
                    game.getBoard().setMove(move, GameBoard.EMPTY_CELL);
                    return move;
                }
                game.getBoard().setMove(move, GameBoard.EMPTY_CELL);
            }

            // check if we will lose
            for (int move : game.getBoard().getValidMoves()) {
                game.getBoard().setMove(move, game.getCurrentPlayer().getId() == 1 ? 2 : 1);
                if (game.hasPlayerWon(game.getOpponent())) {
                    game.getBoard().setMove(move, GameBoard.EMPTY_CELL);
                    return move;
                }
                game.getBoard().setMove(move, GameBoard.EMPTY_CELL);
            }
        }


        for (int move : game.getBoard().getValidMoves()) {
            game.getBoard().setMove(move, game.getCurrentPlayer().getId());
            int score = negamax(maxDepth, game.getCurrentPlayer(), 1, -80, 80, maxDepth);
            game.getBoard().setMove(move, GameBoard.EMPTY_CELL);

            if (score >= bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
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
     * @param alpha         the minimum score possible
     * @param beta          the maximum score possible
     * @param maxDepth      the max amount of layers to search
     * @return best move to make if depth == 0, else the score of the board
     */
    private int negamax(int depth, Player currentPlayer, int color, int alpha, int beta,
                        int maxDepth) {
        final int alphaOrig = alpha;

        TranspositionEntry ttEntry =
                transpositionTable.get(
                        TranspositionEntry.createHash(game.getBoard(), currentPlayer));
        if (ttEntry != null && ttEntry.depth() > depth) {
            switch (ttEntry.flag()) {
                case EXACT:
                    return ttEntry.value();
                case LOWER_BOUND:
                    alpha = Math.max(alpha, ttEntry.value());
                    break;
                case UPPER_BOUND:
                    beta = Math.min(beta, ttEntry.value());
                    break;
                default:
                    throw new IllegalStateException("Invalid flag");
            }

            if (alpha >= beta) {
                return ttEntry.value();
            }
        }


        if (depth == 0 || game.getState() != GameModel.GameState.PLAYING) {
            return game.getScore(currentPlayer, depth + 1, maxDepth);
        }


        Player opp = game.getOpponent(currentPlayer);
        int value = Integer.MIN_VALUE;

        for (int move : game.getBoard().getValidMoves()) {
            // set a move and get the score
            game.getBoard().setMove(move, opp.getId());
            int score = -negamax(depth - 1, opp, -color, -beta, -alpha, 8);

            value = Math.max(value, score);
            game.getBoard().setMove(move, GameBoard.EMPTY_CELL);

            alpha = Math.max(alpha, score);
            if (alpha >= beta) {
                break;
            }
        }

        addBoardToTranspositionTable(value, alphaOrig, beta, depth, currentPlayer);
        return value;
    }

    /**
     * add an entry to the transposition table.
     * if the entry already exists, it will be replaced.
     *
     * @param value the value of the board.
     * @param alpha the alpha value.
     * @param beta  the beta value.
     * @param depth the depth of the board.
     */
    private void addBoardToTranspositionTable(int value, int alpha, int beta, int depth,
                                              Player currentPlayer) {

        TranspositionEntry.Flags ttFlag = TranspositionEntry.Flags.EXACT;
        if (value < alpha) {
            ttFlag = TranspositionEntry.Flags.UPPER_BOUND;
        }
        if (value > beta) {
            ttFlag = TranspositionEntry.Flags.LOWER_BOUND;
        }

        transpositionTable.put(TranspositionEntry.createHash(game.getBoard(), currentPlayer),
                new TranspositionEntry(value, depth, ttFlag));

    }
}
