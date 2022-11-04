package com.headtrixz.game;

import com.headtrixz.ui.GameController;
import javafx.application.Platform;


public abstract class GameModel {
    public enum GameState {
        PLAYING,
        PLAYER_ONE_WON,
        PLAYER_TWO_WON,
        DRAW
    }

    protected GameBoard board;
    protected GameController controller;
    protected Player currentPlayer;
    protected Player[] players;

    protected volatile int guiMove = -1;

    public GameModel(int boardSize) {
        board = new GameBoard(boardSize);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameBoard getActualGameBoard() {return board;}
    public GameBoard getBoard() {
        return board.clone();
    }

    public int getGuiMove() {
        return guiMove;
    }

    public Player getPlayer(int i) {
        return players[i% players.length];

    }

    public Player getPlayer(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }

        throw new RuntimeException("Unknown player: " + username);
    }

    public void initialize(GameController controller, Player ...players) {
        this.controller = controller;
        this.currentPlayer = players[0];
        this.players = players;
        for (int i = 0; i < players.length; i++) {
            players[i].setId(i + 1);
        }

        board.clear();
        nextTurn(currentPlayer);
    }

    private void nextPlayer() {
        if (currentPlayer.getId() == players.length) {
            currentPlayer = players[0];
            return;
        }

        currentPlayer = players[currentPlayer.getId()];
    }

    public void nextTurn(Player player) {
        player.onTurn(m -> {
            if (!board.isValidMove(m)) {
                return;
            }

            board.setMove(m, player.getId());
            Platform.runLater(() -> {
                controller.onUpdate(m, player.getId());
            });

            if (getState() == GameState.PLAYING) {
                nextPlayer();
                nextTurn(currentPlayer);
            } else {
                Platform.runLater(() -> {
                    controller.endGame();
                });
            }
        });
    }

    public void setGuiMove(int move) {
        if (move == -1 || board.isValidMove(move)) {
            guiMove = move;
        }
    }

    public abstract GameState getState();
    public abstract int getScore();

    /**
     * chek if the given player has won by checking if the GameState value with index users id + 1 is equal to the current state
     *
     * @param player the player to check
     * @return a boolean that is true if the player has won
     */
    public boolean hasPlayerWon(Player player) {
        return GameState.values()[player.getId()+1] == getState();
    }
}
