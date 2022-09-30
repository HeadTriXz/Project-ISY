package com.headtrixz.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class InputHandler implements Runnable {
    private final HashMap<ServerMessageType, ArrayList<Consumer<ServerMessage>>> listeners = new HashMap<>();
    private final BufferedReader in;

    public InputHandler(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        on(ServerMessageType.ERROR, e -> {
            System.err.println("Error: " + e.getMessage());
        });
    }

    public void close() throws IOException {
        in.close();
    }

    private void emit(ServerMessageType event, ServerMessage message) {
        if (!listeners.containsKey(event)) {
            return;
        }

        for (Consumer<ServerMessage> listener : listeners.get(event)) {
            listener.accept(message);
        }
    }

    public void off(ServerMessageType event, Consumer<ServerMessage> listener) {
        if (!listeners.containsKey(event)) {
            return;
        }

        listeners.get(event).remove(listener);
    }

    public void on(ServerMessageType event, Consumer<ServerMessage> listener) {
        if (!listeners.containsKey(event)) {
            listeners.put(event, new ArrayList<>());
        }

        listeners.get(event).add(listener);
    }

    public void once(ServerMessageType event, Consumer<ServerMessage> listener) {
        if (!listeners.containsKey(event)) {
            listeners.put(event, new ArrayList<>());
        }

        listeners.get(event).add(e -> {
            off(event, listener);
            listener.accept(e);
        });
    }

    @Override
    public void run() {
        try {
            String input;
            while ((input = in.readLine()) != null) {
                ServerMessage message = new ServerMessage(input);
                ServerMessageType type = message.getType();
                if (type != null) {
                    emit(type, message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
