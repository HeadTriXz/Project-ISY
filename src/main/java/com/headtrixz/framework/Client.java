package com.headtrixz.framework;

import java.io.*;
import java.net.Socket;

public class Client {
    private final InputHandler inputHandler;
    private final Thread inputHandlerThread;
    private final OutputHandler outputHandler;
    private final Socket socket;

    public Client(String host, int port) throws Exception {
        socket = new Socket(host, port);
        outputHandler = new OutputHandler(socket);
        inputHandler = new InputHandler(socket);

        inputHandlerThread = new Thread(inputHandler);
        inputHandlerThread.start();

        System.out.println("Connection established.");
    }

    public void close() throws IOException {
        outputHandler.close();
        inputHandlerThread.interrupt();
        inputHandler.close();
        socket.close();

        System.out.println("Connection closed.");
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }
}
