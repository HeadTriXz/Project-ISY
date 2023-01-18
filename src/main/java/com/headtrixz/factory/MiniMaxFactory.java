package com.headtrixz.factory;

import com.headtrixz.algorithms.BasicMiniMax;
import com.headtrixz.algorithms.MiniMax;
import com.headtrixz.algorithms.MiniMaxAlphaBeta;
import com.headtrixz.algorithms.MiniMaxOptimized;
import com.headtrixz.algorithms.MiniMaxTransposition;
import com.headtrixz.game.GameModel;

/**
 * Creates a new MiniMax instance fresh from the factory.
 */
public class MiniMaxFactory {
    /**
     * The different types of MiniMax.
     */
    public enum MiniMaxType {
        MiniMax,
        MiniMaxAlphaBeta,
        MiniMaxTransposition,
        MiniMaxOptimized
    }

    /**
     * Create a new MiniMax instance.
     *
     * @param type The specific algorithm to use.
     * @param game a link to the game.
     * @return a fresh minimax instance.
     */
    public static MiniMax createMiniMax(MiniMaxType type, GameModel game) {
        return switch (type) {
            case MiniMax -> new BasicMiniMax(game);
            case MiniMaxAlphaBeta -> new MiniMaxAlphaBeta(game);
            case MiniMaxTransposition -> new MiniMaxTransposition(game);
            case MiniMaxOptimized -> new MiniMaxOptimized(game);
            default -> throw new RuntimeException("Algorithm is not implemented");
        };
    }
}
