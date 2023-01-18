package com.headtrixz.game.helpers;

import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.GameModel.GameState;
import com.headtrixz.game.players.Player;

public class BenchmarkHelper extends GameModelHelper{
    private GameModel.GameState state;
    public BenchmarkHelper(GameMethods controller, GameModel game) {
        super(controller, game);
    }

    @Override
    public void forfeit() {
        this.state = GameModel.GameState.PLAYER_TWO_WON;
    }

    @Override
    public Player getLocalPlayer() {
        return game.getPlayer(0);
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void initialize() { }

    @Override
    public void nextTurn(Player player) { }
}
