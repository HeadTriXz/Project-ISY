package com.headtrixz.factory;

import com.headtrixz.game.GameModel;
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
        NegaMax, MiniMaxGPU
    }

    public static MiniMax createMiniMax(MiniMaxType type, GameModel game) {
        return switch (type) {
            case NegaMax -> new NegaMax(game);
            case MiniMaxGPU -> throw new RuntimeException("Method not implemented");
        };
    }
}
