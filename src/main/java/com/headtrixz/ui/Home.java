package com.headtrixz.ui;

import com.headtrixz.networking.Connection;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Home {
    public Button playTicTacToeButton;
    public Button connect;
    public Button save;
    @FXML
    private TextField username;

    @FXML
    private TextField address;

    @FXML
    private TextField port;

    @FXML
    private Text connectionMessage;

    private boolean connected = false;
    private String usernameLocal;
    private String addressLocal;
    private String portLocal;

    @FXML
    public void initialize() {
        usernameLocal = UIManager.getSetting("username");
        addressLocal = UIManager.getSetting("address");
        portLocal = UIManager.getSetting("port");

        username.setText(usernameLocal);
        address.setText(addressLocal);
        port.setText(portLocal);

        disableCheck();
    }

    public void playTicTacToe() throws Exception {
        UIManager.switchScreen("game-mode");
    }

    public void connect() throws NumberFormatException {
        if (!connected) {
            String address = UIManager.getSetting("address");
            int port = Integer.parseInt(UIManager.getSetting("port"));

            connectToServer(address, port);
        } else {
            save.setDisable(false);
            connect.setText("Connect");

            connected = false;
        }
    }

    public void connectToServer(String address, int port) {
        Connection conn = Connection.getInstance();
        save.setDisable(true);

        try {
            conn.connect(UIManager.getSetting("address"), Integer.parseInt(UIManager.getSetting("port")));
            connect.setText("Disconnect");
            connected = true;
        } catch (Exception e) {
            save.setDisable(false);
            e.printStackTrace();
            connect.setText("Not connected");
        }
    }

    public void disconnectFromServer() {

    }

    public void save() {
        UIManager.setSetting("username", username.getText());
        UIManager.setSetting("address", address.getText());
        UIManager.setSetting("port", port.getText());

        usernameLocal = username.getText();
        addressLocal = address.getText();
        portLocal = port.getText();

        connect.setDisable(false);

        disableCheck();
    }

    public void disableCheck() {
        boolean boolForSinglePlayer = !usernameLocal.equals("");
        boolean boolForMultiplayer = !usernameLocal.equals("") && !addressLocal.equals("") && !portLocal.equals("");

        connect.setDisable(!boolForMultiplayer);
        playTicTacToeButton.setDisable(!boolForSinglePlayer);

    }
}
