package com.headtrixz.game;

import com.headtrixz.game.helpers.GameModelHelper;
import com.headtrixz.game.players.Player;


public abstract class GameModel {
    public enum GameState {
        PLAYING,
        PLAYER_ONE_WON,
        PLAYER_TWO_WON,
        DRAW
    }

    protected GameBoard board;
    protected GameCommands controller;
    protected Player currentPlayer;
    protected GameModelHelper helper;
    protected String name;
    protected Player[] players;

    protected volatile int guiMove = -1;

    public GameModel(String name, int boardSize) {
        this.name = name;
        this.board = new GameBoard(boardSize); // TODO: Move to initialize
    }

    public GameBoard cloneBoard() {
        return board.clone();
    }

    public GameBoard getBoard() {
        return board;
    }

    public GameCommands getController() {
        return controller;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getGuiMove() {
        return guiMove;
    }

    public Player getPlayer(int i) {
        return players[i % players.length];
    }

    public Player getPlayer(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }

        throw new RuntimeException("Unknown player: " + username);
    }

    public void initialize(GameCommands controller, GameModelHelper helper, Player... players) {
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

    public Player nextPlayer() {
        return currentPlayer = currentPlayer.getId() == players.length
                ? players[0]
                : players[currentPlayer.getId()];
    }

    public void setGuiMove(int move) {
        if (move == -1 || board.isValidMove(move)) {
            guiMove = move;
        }
    }

    public abstract GameState getState();
    public abstract int getScore(Player currentPlayer, int depth);
    public abstract int getMinScore();
    public abstract int getMaxScore();

    /**
     * chek if the given player has won by checking if the GameState value with
     * index users id + 1 is equal to the current state
     *
     * @param player the player to check
     * @return a boolean that is true if the player has won
     */
    public boolean hasPlayerWon(Player player) {
        return GameState.values()[player.getId() + 1] == getState();
    }
}
