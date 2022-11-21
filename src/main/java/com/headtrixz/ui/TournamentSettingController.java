package com.headtrixz.ui;

import com.headtrixz.networking.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TournamentSettingController {
  @FXML private TextField usernameField;
  @FXML private TextField ipField;
  @FXML private TextField portField;

  /** FXML init method. Makes sure that the settings are persistence between launches. */
  public void initialize() {
    usernameField.setText(UIManager.getSetting("username"));
    ipField.setText(UIManager.getSetting("ip"));
    portField.setText(UIManager.getSetting("port"));
  }

  /** Makes the connection the the server. */
  private void connect() {
    Connection conn = Connection.getInstance();

    try {
      conn.connect(UIManager.getSetting("ip"), Integer.parseInt(UIManager.getSetting("port")));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** On click event for when the back home button is pressed. */
  public void back() {
    saveAndSwitch("home");
  }

  /** On click event for when the TTT button is pressed. */
  public void playTicTacToe() {
    connect();
    // TODO: response when connected or not!
    saveAndSwitch("tournament");
  }

  /** On click event for when the Othello button is pressed. */
  public void playOthello() {
    connect();
    // TODO: response when connected or not!
    // saveAndSwitch("othello");
  }

  /**
   * Saves all the settings and switches from screen.
   *
   * @param name The screen to switch to.
   */
  private void saveAndSwitch(String name) {
    UIManager.setSetting("username", usernameField.getText());
    UIManager.setSetting("ip", ipField.getText());
    UIManager.setSetting("port", portField.getText());

    UIManager.switchScreen(name);
  }

  /**
   * Validates all the input fields.
   *
   * @return true when the values seem all right, false when they don't.
   */
  private boolean validate() {
    // TODO: validate fields of ui
    return true;
  }
}
