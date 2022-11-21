package com.headtrixz.networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/** Sends messages to the server. */
public class OutputHandler {
  private final PrintWriter out;

  /**
   * Sends messages to the server.
   *
   * @param socket An instance of a {@link Socket}.
   */
  public OutputHandler(Socket socket) throws IOException {
    out = new PrintWriter(socket.getOutputStream(), true);
  }

  /**
   * Sends a challenge to the specified user with the specified mode.
   *
   * @param username The username of the person you want to challenge.
   * @param mode The mode of the game.
   */
  public void challenge(String username, String mode) {
    send("challenge \"" + username + "\" \"" + mode + '"');
  }

  /**
   * Sends a message to the server to accept a challenge.
   *
   * @param challengeID The ID of the challenge you want to accept.
   */
  public void challengeAccept(int challengeID) {
    send("challenge accept " + challengeID);
  }

  /** Closes the output stream. */
  public void close() {
    out.close();
  }

  /** Sends the server a message saying that the player has forfeited. */
  public void forfeit() {
    send("forfeit");
  }

  /** Sends the 'get gamelist' command to the server. */
  public void getGameList() {
    send("get gamelist");
  }

  /** Sends the 'get playerlist' command to the server. */
  public void getPlayerList() {
    send("get playerlist");
  }

  /**
   * Sends a login command to the server.
   *
   * @param username The username of the user you want to login as.
   */
  public void login(String username) {
    send("login " + username);
  }

  /** Sends the 'logout' command to the server. */
  public void logout() {
    send("logout");
  }

  /**
   * Tells the server to set a move for the logged-in player.
   *
   * @param move The move you want to make.
   */
  public void move(int move) {
    send("move " + move);
  }

  /**
   * Sends a command to the server.
   *
   * @param input The command to be sent to the server
   */
  public void send(String input) {
    out.println(input);
  }

  /**
   * Subscribes the logged-in player to the provided gamemode.
   *
   * @param mode The mode to subscribe to.
   */
  public void subscribe(String mode) {
    send("subscribe " + mode);
  }
}
