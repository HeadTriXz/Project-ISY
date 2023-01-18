package com.headtrixz.benchmark;

import com.headtrixz.factory.MiniMaxFactory.MiniMaxType;
import com.headtrixz.game.Othello;
import com.headtrixz.game.helpers.BenchmarkHelper;
import com.headtrixz.game.players.AIPlayer;
import com.headtrixz.game.players.HackyAIPlayer;
import com.headtrixz.game.players.Player;
import java.util.ArrayList;

/**
 * The **great** benchmark class.
 */
public class Benchmark {
    /**
     * Run a benchmark.
     *
     * @param algorithm the algorithm to use.
     */
    public static void benchmark(MiniMaxType algorithm) {
        System.out.println("Starting benchmark");
        Othello othello = new Othello();
        Player player = new AIPlayer(othello, "jannie", algorithm);
        Player player2 = new HackyAIPlayer(othello, "Lars, ga betere code schrijven.");
        BenchmarkHelper helper = new BenchmarkHelper(othello);
        othello.initialize(helper, player, player2);

        int[] benchmarkDepths = { 4, 8, 16 };
        ArrayList<Float> avgTimes = new ArrayList<Float>();

        for (int depth : benchmarkDepths) {
            ArrayList<Float> runTimes = new ArrayList<Float>();
            for (int i = 0; i < 50; i++) {
                long startTime = System.nanoTime();
                player.getMove(depth);
                long endTime = System.nanoTime();
                double power = 1 * Math.pow(10, 6);
                float time = (float) (((endTime - startTime) * 1.0) / power * 1.0);
                runTimes.add(time);
            }
            float totalTime = 0;
            for (float time : runTimes) {
                totalTime += time;
            }
            avgTimes.add(totalTime / runTimes.size());
        }
        for (int i = 0; i < avgTimes.size(); i++) {
            System.out.printf("Running with depth %d took %f milliseconds on average\n",
                    benchmarkDepths[i], avgTimes.get(i));
        }

        helper.forfeit();
    }
}
