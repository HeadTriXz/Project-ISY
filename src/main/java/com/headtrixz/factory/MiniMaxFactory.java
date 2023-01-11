package com.headtrixz.factory;

import com.headtrixz.game.GameModel;
import com.headtrixz.game.players.Player;
import com.headtrixz.minimax.BasicMiniMax;
import com.headtrixz.minimax.MiniMax;
import com.headtrixz.minimax.NegaMax;

/**
 * Creates a new MiniMax instance fresh from the factory.
 */
public class MiniMaxFactory {
    /**
     * The different types of MiniMax
     */
    public enum MiniMaxType {
        NegaMax, MiniMaxGPU, MiniMax
    }

    public static MiniMax createMiniMax(MiniMaxType type, GameModel game, Player player) {
        return switch (type) {
            case NegaMax -> new NegaMax(game);
            case MiniMax -> new BasicMiniMax(game, player);
            case MiniMaxGPU -> throw new RuntimeException("Method not implemented");
        };
    }
}
