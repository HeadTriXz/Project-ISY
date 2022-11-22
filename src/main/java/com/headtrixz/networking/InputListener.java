package com.headtrixz.networking;

@FunctionalInterface
public interface InputListener {
    /**
     * This function is called when the server sends a message to the client.
     *
     * @param message The message received from the server.
     */
    void update(ServerMessage message);
}
