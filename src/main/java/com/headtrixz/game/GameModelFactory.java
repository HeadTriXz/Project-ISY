package com.headtrixz.game;

/**
 * factory to create game models.
 */
public class GameModelFactory {
    /**
     * creates a GameModel for a certain game.
     *
     * @param gameType a String indicating which game the model is for.
     * @return a GameModel specifically for the type of game.
     */
    public static GameModel createGameModel(String gameType) {

        return switch (gameType) {
            case "TicTacToe" -> new TicTacToe();
            default ->
                    throw new IllegalArgumentException("Game type not implemented");
        };
    }
}
