package com.headtrixz;

import com.headtrixz.framework.*;
import com.headtrixz.ui.*;

import javafx.application.Application;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Application.launch(UIManager.class, args);

        // try {
        // Client client = new Client("localhost", 7789);
        // InputHandler in = client.getInputHandler();
        // OutputHandler out = client.getOutputHandler();
        //
        // in.on(ServerMessageType.CHALLENGE, e -> {
        // int challengeID = Integer.parseInt(e.getObject().get("CHALLENGENUMBER"));
        // out.challengeAccept(challengeID);
        // });
        //
        // AtomicInteger move = new AtomicInteger();
        // in.on(ServerMessageType.MOVE, e -> {
        // move.set(Integer.parseInt(e.getObject().get("MOVE")));
        // });
        //
        // in.on(ServerMessageType.YOURTURN, e -> {
        // out.move(move.get() + 1);
        // });
        //
        // Consumer<ServerMessage> onFinished = e -> {
        // move.set(0);
        // };
        //
        // in.on(ServerMessageType.WIN, onFinished);
        // in.on(ServerMessageType.LOSS, onFinished);
        // in.on(ServerMessageType.DRAW, onFinished);
        //
        // out.login("noobmaster69");
        // } catch (Exception e) {
        // System.err.println(e.getMessage());
        // }
    }
}
