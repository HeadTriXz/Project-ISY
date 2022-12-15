package com.headtrixz.ui;

import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.helpers.OnlineHelper;
import com.headtrixz.game.players.AIPlayer;
import com.headtrixz.game.players.Player;
import com.headtrixz.game.players.RemotePlayer;
import com.headtrixz.networking.Connection;
import com.headtrixz.networking.InputListener;
import com.headtrixz.networking.ServerMessageType;
import com.headtrixz.ui.elements.GameGrid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class TournamentController implements GameMethods {

    @FXML
    ListView<String> playersListView;
    @FXML
    Text title;
    @FXML
    Text wins;
    @FXML
    Text loses;
    @FXML
    Text draws;
    @FXML
    Text onlineText;
    @FXML
    Text playerOneText;
    @FXML
    Text playerTwoText;
    @FXML
    Text loggedInAs;
    @FXML
    StackPane gameContainer;

    String username;
    GameModel currentGame;
    OnlineHelper onlineHelper;

    GameGrid gameGrid;


    int drawCount;
    int winCount;
    int loseCount;

    /**
     * FXML init method. Logs into the server when the screen has loaded.
     */
    public void initialize() {
        username = UIManager.getSetting("username");
        loggedInAs.setText(String.format("Ingelogd als: %s", username));

        Connection connection = Connection.getInstance();
        connection.getOutputHandler().login(username);
        connection.getInputHandler().subscribe(ServerMessageType.MATCH, onMatch);
        connection.getInputHandler().subscribe(ServerMessageType.PLAYERLIST, onPlayerList);

        Thread work = new Thread(() -> {
            while (true) {
                try {
                    this.getPlayerList();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        work.start();

        title.setText("Tournooi modes voor Tic Tac Toe");
        playerOneText.setText(username + " - X");
    }


    /**
     * Appends text to the log text field and scrolls down the latest message.
     *
     * @param message the message to append.
     */
    public void addToLogs(String message) {
        System.out.println(message);
    }

    public void getPlayerList() {
        Connection connection = Connection.getInstance();
        connection.getOutputHandler().getPlayerList();
    }

    /**
     * On click event for the disconnect button.
     * Forfeits the current match and logs out of the server.
     */
    public void disconnect() {
        if (currentGame != null) {
            onlineHelper.forfeit();
        }

        Connection connection = Connection.getInstance();
        connection.getInputHandler().unsubscribe(ServerMessageType.MATCH, onMatch);
        connection.getInputHandler().unsubscribe(ServerMessageType.PLAYERLIST, onPlayerList);

        connection.getOutputHandler().logout();
        UIManager.switchScreen("home");
    }

    /**
     * Gets called when a game has ended. Shows a message in the log for what
     * the result was and increments the appropriate counter.
     */
    @Override
    public void endGame() {
        String logText = "Ja dit is een apparte situatie, maar je hebt iets gedaan tegen";
        switch (currentGame.getState()) {
            case PLAYER_ONE_WON -> {
                winCount++;
                wins.setText(String.format("Gewonnen: %d", winCount));
                logText = "Match gewonnen van";
            }

            case PLAYER_TWO_WON -> {
                loseCount++;
                loses.setText(String.format("Verloren: %d", loseCount));
                logText = "Match verloren van";
            }

            case DRAW -> {
                drawCount++;
                draws.setText(String.format("Gelijkspel: %d", drawCount));
                logText = "Match gelijkgespeeld tegen";
            }
        }

        String opponent = currentGame.getPlayer(1).getUsername();
        addToLogs(String.format("%s: %s\n", logText, opponent));

        currentGame = null;
    }

    /**
     * A listener that listens to the network connecting and starts a local game
     * to mirror the online game.
     */
    private final InputListener onMatch = message -> {
        Map<String, String> obj = message.getObject();
        String oppenent = obj.get("OPPONENT");
        addToLogs("Start een match met: " + oppenent);

        // TODO: Set this to a helper/util class
        currentGame = new TicTacToe();
        RemotePlayer remotePlayer = new RemotePlayer(oppenent);
        AIPlayer aiPlayer = new AIPlayer(currentGame, username);
        onlineHelper = new OnlineHelper(currentGame);
        currentGame.initialize(this, onlineHelper, aiPlayer, remotePlayer);

        Platform.runLater(() -> {
            gameContainer.getChildren().remove(gameGrid);
            gameGrid = new GameGrid(
                currentGame.getBoard().getSize(),
                gameContainer.getHeight(),
                currentGame.getBackgroundColor()
            );

            gameContainer.getChildren().add(gameGrid);
            playerTwoText.setText("O - " + oppenent);
        });
    };

    /**
     * A listener that listens to the user playlist and sets that visible in the GUI.
     */
    private final InputListener onPlayerList = message -> {
        List<String> playersList = new ArrayList<String>(Arrays.asList(message.getArray()));

        onlineText.setText(String.format("Online: %d", playersList.size()));

        playersList.remove(username);

        playersListView.setItems(FXCollections.observableArrayList(playersList));
        playersListView.refresh();
    };

    /**
     * Gets called when a set is done on the board by either players.
     * Puts a log message of the move.
     *
     * @param move the index of the move the player has done.
     * @param player the player who has set the move.
     */
    @Override
    public void update(int move, Player player) {
        addToLogs(String.format("%s was gezet door speler %s", move, player.getUsername()));
        gameGrid.setTile(move, currentGame.getImage(player.getId()));
    }
}
