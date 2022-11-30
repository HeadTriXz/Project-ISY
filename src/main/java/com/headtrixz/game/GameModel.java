package com.headtrixz.game;

import com.headtrixz.game.helpers.GameModelHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;

/**
 * The base for all games.
 */
public abstract class GameModel {
    /**
     * The state of the game.
     */
    public enum GameState {
        PLAYING,
        PLAYER_ONE_WON,
        PLAYER_TWO_WON,
        DRAW
    }

    protected GameBoard board;
    protected GameMethods controller;
    protected Player currentPlayer;
    protected GameModelHelper helper;
    protected String name;
    protected Player[] players;

    protected volatile int guiMove = -1;

    /**
     * The base for all games.
     *
     * @param name The name of the game.
     * @param boardSize The size of the board.
     */
    public GameModel(String name, int boardSize) {
        this.name = name;
        this.board = new GameBoard(boardSize);
    }

    /**
     * Returns a copy of the board.
     *
     * @return A copy of the board.
     */
    public GameBoard cloneBoard() {
        return board.clone();
    }

    /**
     * Returns the original board. WARNING: Do NOT modify the board.
     *
     * @return The original board.
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * Returns the game controller.
     *
     * @return The game controller.
     */
    public GameMethods getController() {
        return controller;
    }

    /**
     * Returns the player whose turn it currently is.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the move a human player has set. Will return -1 if none is set.
     *
     * @return The move a human player has set.
     */
    public int getGuiMove() {
        return guiMove;
    }

    /**
     * Returns the player at the given index.
     *
     * @param i The index of the player you want to get.
     * @return The player at the index i.
     */
    public Player getPlayer(int i) {
        return players[i];
    }

    /**
     * Returns the player with the given username.
     *
     * @param username The username of the player to get.
     * @return A player object.
     */
    public Player getPlayer(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }

        throw new RuntimeException("Unknown player: " + username);
    }

    /**
     * Return the next player.
     *
     * @return The opponent of the current player.
     */
    public Player getOpponent() {
        return players[currentPlayer.getId() % players.length];
    }

    /**
     * Returns the opponent of the passed in player.
     *
     * @param player the player to get the oppent of
     * @return The opponent of the passed player.
     */
    public Player getOpponent(Player player) {
        return getPlayer(player.getId() % players.length);
    }


    /**
     * check if the given player has won by checking if the GameState value with
     * index users id is equal to the current state.
     *
     * @param player the player to check
     * @return a boolean that is true if the player has won
     */
    public boolean hasPlayerWon(Player player) {
        return GameState.values()[player.getId()] == getState();
    }

    /**
     * Initializes the game.
     *
     * @param controller The game controller.
     * @param helper A helper class for either an offline or online game.
     */
    public void initialize(GameMethods controller, GameModelHelper helper, Player... players) {
        this.controller = controller;
        this.currentPlayer = players[0];
        this.helper = helper;
        this.players = players;
        for (int i = 0; i < players.length; i++) {
            players[i].setId(i + 1);
        }

        board.clear();
        helper.initialize();
    }

    /**
     * Sets the current player to the given player.
     *
     * @param player The player whose turn it is.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * If the move is valid, set the guiMove variable to the move.
     *
     * @param move The move that the player wants to make.
     */
    public void setGuiMove(int move) {
        if (!(currentPlayer instanceof HumanPlayer)) {
            return;
        }

        if (move == -1 || board.isValidMove(move)) {
            guiMove = move;
        }
    }

    /**
     * Returns the score of the current player at the current depth.
     *
     * @param currentPlayer The player whose turn it is to move.
     * @param depth         The depth of the current node in the tree.
     * @param maxDepth      The max amount of layers to search through
     * @return The score of the current player.
     */
    public abstract int getScore(Player currentPlayer, int depth, int maxDepth);

    /**
     * Returns the current state of the game.
     *
     * @return The current state of the game.
     */
    public abstract GameState getState();
}
