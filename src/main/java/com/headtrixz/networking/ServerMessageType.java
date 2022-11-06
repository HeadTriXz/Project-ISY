package com.headtrixz.networking;

public enum ServerMessageType {
    OK("OK"),
    ERROR("ERR"),
    GAMELIST("SVR GAMELIST"),
    PLAYERLIST("SVR PLAYERLIST"),
    CHALLENGE_CANCELLED("SVR GAME CHALLENGE CANCELLED"),
    CHALLENGE("SVR GAME CHALLENGE"),
    DRAW("SVR GAME DRAW"),
    LOSS("SVR GAME LOSS"),
    MATCH("SVR GAME MATCH"),
    MOVE("SVR GAME MOVE"),
    WIN("SVR GAME WIN"),
    YOURTURN("SVR GAME YOURTURN");

    private final String message;

    ServerMessageType(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
