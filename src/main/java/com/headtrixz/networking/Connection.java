package com.headtrixz.networking;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents a singleton class that handles the connection to the server.
 */
public class Connection {
    private static Connection INSTANCE;

    private InputHandler inputHandler;
    private Thread inputHandlerThread;
    private OutputHandler outputHandler;
    private Socket socket;

    /**
     * Closes the connection to the server.
     */
    public void close() throws IOException {
        outputHandler.close();
        inputHandlerThread.interrupt();
        inputHandler.close();
        socket.close();

        System.out.println("Connection closed.");
    }

    /**
     * Connects the socket to the specified hostname and port.
     *
     * @param host The hostname of the server you want to connect to.
     * @param port The port to connect to.
     */
    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            outputHandler = new OutputHandler(socket);
            inputHandler = new InputHandler(socket);

            inputHandlerThread = new Thread(inputHandler);
            inputHandlerThread.start();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns an instance of {@link InputHandler}.
     *
     * @return The inputHandler object.
     */
    public InputHandler getInputHandler() {
        return inputHandler;
    }

    /**
     * If the instance is null, create a new instance and return it. If the instance is not null,
     * return the existing instance.
     *
     * @return The instance of the Connection class.
     */
    public static Connection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Connection();
        }

        return INSTANCE;
    }

    /**
     * Returns an instance of {@link OutputHandler}.
     *
     * @return The outputHandler object.
     */
    public OutputHandler getOutputHandler() {
        return outputHandler;
    }
}
