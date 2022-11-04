package com.headtrixz.MiniMax;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.Player;

import java.util.HashMap;
import java.util.Map;

public class MiniMax {

    public static int maxDepth = 4;


    /**
     * get the best next move for any given player based on the current game state.
     * the game should be advanced to the next player for it to work correctly.
     * this function works using the current board and does not work on a clone of the board because it uses the state
     * of the game determining the score of the board
     *
     * @param game the current game state
     * @return the best move to make given the current game state
     */
    public static int getMove(GameModel game) {
        return minimax(game, 0, game.getCurrentPlayer());
    }

    /**
     * the algorith that actualy gets the correct next move
     *
     * @param game          the current game.
     * @param depth         the current depth
     * @param currentPlayer the player that is currently at play
     * @return best move to make if depth == 0, else the score of the board
     */
    private static int minimax(GameModel game, int depth, Player currentPlayer) {

        //TODO:: refractor to keep the next moves state so we can progresifly build up the next moves.
        // not needed for tictactoe but usefull for reversi/othello
        Map<Integer, Integer> potentialOutcomes = new HashMap<>();


        // check if the game is finished or the max depth has been reached
        //TODO:: improve the scoring to work better with other games
        if (game.getState() == GameModel.GameState.DRAW || depth == maxDepth) {
            return 0;
        } else if (game.getState() != GameModel.GameState.PLAYING) {
            return -1;
        }

        //get the opponent. the id of current player is +1 of the index in players array
        // so by passing the currentPlayers id we get the next player
        Player opp = game.getPlayer(currentPlayer.getId());

        // iterate over all valid moves and calculate there score
        for (int move : game.getBoard().getValidMoves()) {
            game.getActualGameBoard().setMove(move, currentPlayer.getId());

            potentialOutcomes.put(move, (-1 * minimax(game, depth + 1, opp)));
            game.getActualGameBoard().setMove(move, 0);

        }

        // return the move if we are at the base call or the score if we are in a recursive call
        if (depth == 0) {
            return getBestOutcome(potentialOutcomes).getKey();
        } else {
            return getBestOutcome(potentialOutcomes).getValue();
        }

    }

    /**
     * get the entry in the map with the best score
     *
     * @param boardScores the map with boardScores and moves
     * @return the map entry with the best score
     */
    private static Map.Entry<Integer, Integer> getBestOutcome(Map<Integer, Integer> boardScores) {
        return boardScores.entrySet().stream().max(Map.Entry.comparingByValue()).get();
    }


}