package com.headtrixz.benchmark;

import com.headtrixz.factory.MiniMaxFactory.MiniMaxType;
import com.headtrixz.game.Othello;
import com.headtrixz.game.helpers.BenchmarkHelper;
import com.headtrixz.game.players.AIPlayer;
import com.headtrixz.game.players.HackyAIPlayer;
import com.headtrixz.game.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The **great** benchmark class.
 */
public class Benchmark {
    /**
     * Run a benchmark.
     *
     * @param algorithm the algorithm to use.
     */
    public static void benchmark(MiniMaxType algorithm, int iterations) {
        System.out.printf("Starting benchmark with the %s algorithm\n", algorithm);
        int[] benchmarkDepths = { 4, 6, 10 };
        Map<Integer, List<Long>> data = new HashMap<>();

        for (int depth : benchmarkDepths) {
            ArrayList<Long> runTimes = new ArrayList<>();
            for (int i = 0; i < iterations; i++) {
                Player player = setupGame(algorithm);

                long startTime = System.nanoTime();
                player.getMove(depth);
                long endTime = System.nanoTime();

                runTimes.add(endTime - startTime);
            }
            data.put(depth, runTimes);
        }

        process(data);
    }

    private static Player setupGame(MiniMaxType algorithm) {
        Othello othello = new Othello();
        Player player = new AIPlayer(othello, "jannie", algorithm);
        Player player2 = new HackyAIPlayer(othello, "Lars, ga betere code schrijven.");
        BenchmarkHelper helper = new BenchmarkHelper(null, othello);
        othello.initialize(helper, player, player2);

        return player;
    }

    private static void process(Map<Integer, List<Long>> data) {
        for (int depth : data.keySet()) {
            float avg = 0;
            double power = 1 * Math.pow(10, 6);

            for (long value : data.get(depth)) {
                avg += (float) ((value * 1.0) / power * 1.0);
            }
            avg /= data.get(depth).size();

            System.out.printf("avg for %d took %f milliseconds\n", depth, avg);
        }
    }
}
