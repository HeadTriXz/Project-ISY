package com.headtrixz.ui;

import com.headtrixz.networking.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TournamentSettingController {
  @FXML private TextField usernameField;
  @FXML private TextField ipField;
  @FXML private TextField portField;

  public void initialize() {
    usernameField.setText(UIManager.getSetting("username"));
    ipField.setText(UIManager.getSetting("ip"));
    portField.setText(UIManager.getSetting("port"));
  }

  private void save() {
    UIManager.setSetting("username", usernameField.getText());
    UIManager.setSetting("ip", ipField.getText());
    UIManager.setSetting("port", portField.getText());
  }

  public void connect() throws NumberFormatException {
    Connection conn = Connection.getInstance();

    try {
      conn.connect(UIManager.getSetting("ip"), Integer.parseInt(UIManager.getSetting("port")));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void back() {
    saveAndSwitch("home");
  }

  public void playTicTacToe() {
    connect();
    // TODO: response when connected or not!
    saveAndSwitch("tournament");
  }

  public void playOthello() {
    connect();
    // TODO: response when connected or not!
    // saveAndSwitch("othello");
  }

  private void saveAndSwitch(String name) {
    UIManager.setSetting("username", usernameField.getText());
    UIManager.setSetting("ip", ipField.getText());
    UIManager.setSetting("port", portField.getText());

    UIManager.switchScreen(name);
  }

  private boolean validate() {
    // TODO: validate fields of ui
    return true;
  }
}
