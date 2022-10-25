package com.headtrixz.models;

import com.headtrixz.controllers.GameController;
import com.headtrixz.game.GameBoard;
import com.headtrixz.game.Player;

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

    public GameModel(int boardSize) {
        board = new GameBoard(boardSize);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameBoard getBoard() {
        return board.clone();
    }

    public Player getPlayer(int i) {
        return players[i];
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
    }

    private void nextPlayer() {
        if (currentPlayer.getId() == players.length) {
            currentPlayer = players[0];
            return;
        }

        currentPlayer = players[currentPlayer.getId()];
    }

    public void setMove(int move, int player) {
        if (!board.isValidMove(move)) {
            return;
        }

        board.setMove(move, player);
        controller.onUpdate(move, player);

        // TODO: If local player, and is online, send command to server.

        if (getState() == GameState.PLAYING) {
            nextPlayer();
            currentPlayer.onTurn();
        } else {
            controller.endGame();
        }
    }

    public abstract GameState getState();
}
