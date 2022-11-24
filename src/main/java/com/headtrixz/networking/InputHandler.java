package com.headtrixz.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Reads lines from the server and emits events based on the message type.
 */
public class InputHandler implements Runnable {
    private final Map<ServerMessageType, List<InputListener>> listeners = new HashMap<>();
    private final BufferedReader in;

    /**
     * Reads lines from the server and emits events based on the message type.
     *
     * @param socket An instance of a {@link Socket}.
     */
    public InputHandler(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        subscribe(ServerMessageType.ERROR, e -> {
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
    private void notify(ServerMessageType event, ServerMessage message) {
        if (!listeners.containsKey(event)) {
            return;
        }

        List<InputListener> list = listeners.get(event);
        for (InputListener listener : list) {
            listener.update(message);
        }
    }

    /**
     * Add the listener to the queue for removed listeners.
     *
     * @param event The event to listen for.
     * @param listener The listener to remove.
     */
    public void unsubscribe(ServerMessageType event, InputListener listener) {
        if (!listeners.containsKey(event)) {
            return;
        }

        listeners.get(event).remove(listener);
    }

    /**
     * Add the listener to the event.
     *
     * @param event The event to listen for.
     * @param listener The function that will be called when the event is triggered.
     */
    public void subscribe(ServerMessageType event, InputListener listener) {
        if (!listeners.containsKey(event)) {
            listeners.put(event, new CopyOnWriteArrayList<>());
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
                    notify(type, message);
                }
            }
        } catch (IOException ignored) {}
    }
}
