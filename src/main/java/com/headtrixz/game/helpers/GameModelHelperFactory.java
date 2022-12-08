package com.headtrixz.game.helpers;

import com.headtrixz.game.GameModel;

/**
 * factory to create a GameModelHelpers.
 */
public class GameModelHelperFactory {
    /**
     * creates a GameModelHelper.
     *
     * @param gameType a String indicating if the game is online or offline.
     * @param game   the game the helper is for.
     * @return a GameModelHelper specifically for the type of game.
     */
    public static GameModelHelper createGameModelHelper(String gameType, GameModel game) {
        return switch (gameType) {
            case "OnlineHelper" -> new OnlineHelper(game);
            case "OfflineHelper" -> new OfflineHelper(game);
            default ->
                    throw new IllegalArgumentException("GameModelHelper type not implemented");
        };
    }
}
