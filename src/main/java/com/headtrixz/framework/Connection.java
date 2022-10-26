package com.headtrixz.framework;

import java.io.IOException;
import java.net.Socket;

public class Connection {
    private static Connection INSTANCE;

    private InputHandler inputHandler;
    private Thread inputHandlerThread;
    private OutputHandler outputHandler;
    private Socket socket;

    public void close() throws IOException {
        outputHandler.close();
        inputHandlerThread.interrupt();
        inputHandler.close();
        socket.close();

        System.out.println("Connection closed.");
    }

    public void connect(String host, int port) throws Exception {
        socket = new Socket(host, port);
        outputHandler = new OutputHandler(socket);
        inputHandler = new InputHandler(socket);

        inputHandlerThread = new Thread(inputHandler);
        inputHandlerThread.start();

        System.out.println("Connection established.");
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public static Connection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Connection();
        }

        return INSTANCE;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }
}
