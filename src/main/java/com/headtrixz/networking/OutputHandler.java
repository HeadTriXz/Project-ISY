package com.headtrixz.networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class OutputHandler {
    private final PrintWriter out;

    public OutputHandler(Socket socket) throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void challenge(String username, String mode) {
        send("challenge \"" + username + "\" \"" + mode + '"');
    }

    public void challengeAccept(int challengeID) {
        send("challenge accept " + challengeID);
    }

    public void close() {
        out.close();
    }

    public void forfeit() {
        send("forfeit");
    }

    public void getGameList() {
        send("get gamelist");
    }

    public void getPlayerList() {
        send("get playerlist");
    }

    public void login(String username) {
        send("login " + username);
    }

    public void logout() {
        send("logout");
    }

    public void move(int move) {
        send("move " + move);
    }

    public void send(String input) {
        out.println(input);
    }

    public void subscribe(String mode) {
        send("subscribe " + mode);
    }
}
