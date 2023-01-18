package com.headtrixz;

import com.headtrixz.benchmark.Benchmark;
import com.headtrixz.ui.UIManager;
import javafx.application.Application;

/**
 * The main class.
 */
public class Main {
    /**
     * Method to run when the application starts.
     *
     * @param args The command line parameters.
     */
    public static void main(String[] args) {
        // Application.launch(UIManager.class, args);
        if (args.length > 0 && args[0].equals("b")) {
            // benchmark shit
            Benchmark.benchmark();
            return;
        }
    }
}
