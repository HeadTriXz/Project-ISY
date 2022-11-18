package com.headtrixz.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Reads lines from the server and emits events based on the message type.
 */
public class InputHandler implements Runnable {
    private final HashMap<ServerMessageType, ArrayList<Consumer<ServerMessage>>> listeners = new HashMap<>();
    private final HashMap<ServerMessageType, ArrayList<Consumer<ServerMessage>>> removed = new HashMap<>();
    private final BufferedReader in;

    /**
     * Reads lines from the server and emits events based on the message type.
     *
     * @param socket An instance of a {@link Socket}.
     */
    public InputHandler(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        on(ServerMessageType.ERROR, e -> {
            System.err.println("Error: " + e.getMessage());
        });
    }

    /**
     * Closes the input stream.
     */
    public void close() throws IOException {
        in.close();
    }

    /**
     * If there are any listeners for the given event, then for each listener, call the listener
     * with the given message.
     *
     * @param event The event to emit.
     * @param message The message that was received from the server.
     */
    private void emit(ServerMessageType event, ServerMessage message) {
        if (!listeners.containsKey(event)) {
            return;
        }

        var list = listeners.get(event);
        if (removed.containsKey(event)) {
            for (Consumer<ServerMessage> listener : removed.get(event)) {
                list.remove(listener);
            }

            removed.remove(event);
        }

        for (Consumer<ServerMessage> listener : list) {
            listener.accept(message);
        }
    }

    /**
     * Add the listener to the queue for removed listeners.
     *
     * @param event The event to listen for.
     * @param listener The listener to remove.
     */
    public void off(ServerMessageType event, Consumer<ServerMessage> listener) {
        if (!listeners.containsKey(event)) {
            return;
        }

        if (!removed.containsKey(event)) {
            removed.put(event, new ArrayList<>());
        }

        removed.get(event).add(listener);
    }

    /**
     * Add the listener to the event.
     *
     * @param event The event to listen for.
     * @param listener The function that will be called when the event is triggered.
     */
    public void on(ServerMessageType event, Consumer<ServerMessage> listener) {
        if (!listeners.containsKey(event)) {
            listeners.put(event, new ArrayList<>());
        }

        listeners.get(event).add(listener);
    }

    /**
     * Reads lines from the server and emits events based on the message type.
     */
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
        } catch (IOException ignored) {}
    }
}
