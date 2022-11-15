package com.headtrixz.ui;

import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.helpers.OfflineHelper;
import com.headtrixz.game.players.HumanPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.game.players.TicTacToeAI;
import com.headtrixz.ui.elements.GameGrid;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GameController implements GameMethods {
  private static final double PANE_SIZE = 300.0;

  private GameModel game;
  private OfflineHelper helper;
  private GameGrid gameGrid;

  @FXML private Text playerOneName;
  @FXML private Text playerTwoName;
  @FXML private StackPane container;

  public void displayHome() {
    helper.forfeit();
    UIManager.switchScreen("home");
  }

  @Override
  public void endGame() {
    UIManager.switchScreen("game-finish", new GameFinishController(game));
  }

  public void initialize() {
    // TODO: Turn this into a something else.
    game = new TicTacToe();
    helper = new OfflineHelper(game);

    Player playerOne = new HumanPlayer(game, "Humon");
    Player playerTwo = new TicTacToeAI((TicTacToe) game, "Compuper");

    game.initialize(this, helper, playerOne, playerTwo);

    gameGrid = new GameGrid(game.getBoard().getSize(), PANE_SIZE, true);
    gameGrid.setCallback(this::onMouseClick);
    container.getChildren().add(gameGrid);

    // Set visible usernames.
    playerOneName.setText(playerOne.getUsername());
    playerTwoName.setText(playerTwo.getUsername());
  }

  private void onMouseClick(int index) {
    game.setGuiMove(index);
  }

  @Override
  public void update(int move, Player player) {
    String[] players = {"", "X", "O"}; // TODO: Do this differently
    gameGrid.setTile(move, players[player.getId()]);
  }
}
