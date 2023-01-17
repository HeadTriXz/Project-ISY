package com.headtrixz.algorithms;

import java.util.HashMap;

/**
 * The TranspositionTable class implements a hashmap to store the state of the game as key
 * represented by an array and the value of the state as value.
 * The class provides methods to check if a state already exists in the table,
 * add a new value to the table for a specific state represented by an array and get the
 * corresponding value if the state already exists in the table.
 */
public class TranspositionTable {
    private static HashMap<String, Integer> table;
    private static TranspositionTable instance = null;


    /**
     * Constructor to initialize the hashmap.
     */
    public TranspositionTable() {
        table = new HashMap<>();
    }

    /**
     * getInstance method to return the only instance of the class.
     *
     * @return the only instance of the class.
     */
    public static TranspositionTable getInstance() {
        if (instance == null) {
            instance = new TranspositionTable();
        }

        return instance;
    }

    /**
     * Method to check if a state already exists in the table.
     *
     * @param state the state represented by an array to check for.
     * @return the corresponding value if the state already exists in
     *      the table, Integer.MIN_VALUE otherwise.
     */
    public int get(String state) {
        return table.getOrDefault(state, Integer.MIN_VALUE);
    }

    /**
     * Method to add a new value to the table for a specific state represented by an array.
     *
     * @param state the state represented by an array to add
     * @param value the value to add
     */
    public void put(String state, int value) {
        table.put(state, value);
    }

    public boolean containsKey(String gameAsString) {
        return table.containsKey(gameAsString);
    }
}

