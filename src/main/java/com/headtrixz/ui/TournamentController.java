package com.headtrixz.ui;

import com.headtrixz.game.GameBoard;
import com.headtrixz.game.GameMethods;
import com.headtrixz.game.GameModel;
import com.headtrixz.game.Othello;
import com.headtrixz.game.TicTacToe;
import com.headtrixz.game.helpers.GameModelHelper;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    ImageView playerOneIcon;
    @FXML
    Text playerOneText;
    @FXML
    ImageView playerTwoIcon;
    @FXML
    Text playerTwoText;
    @FXML
    Text loggedInAs;
    @FXML
    StackPane gameContainer;

    String username;
    GameModel game;

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
        if (game != null) {
            game.getHelper().forfeit();
        }

        Connection connection = Connection.getInstance();
        connection.getInputHandler().unsubscribe(ServerMessageType.MATCH, onMatch);
        connection.getInputHandler().unsubscribe(ServerMessageType.PLAYERLIST, onPlayerList);

        connection.getOutputHandler().logout();
        UIManager.switchScreen("home");
    }

    private String onDraw() {
        drawCount++;
        draws.setText(String.format("Gelijkspel: %d", drawCount));
        return "Match gelijkgespeeld tegen";
    }

    private String onLoss() {
        loseCount++;
        loses.setText(String.format("Verloren: %d", loseCount));
        return "Match verloren van";
    }

    private String onWin() {
        winCount++;
        wins.setText(String.format("Gewonnen: %d", winCount));
        return "Match gewonnen van";
    }

    /**
     * Gets called when a game has ended. Shows a message in the log for what
     * the result was and increments the appropriate counter.
     */
    @Override
    public void endGame() {
        Player localPlayer = game.getHelper().getLocalPlayer();
        String text = switch (game.getState()) {
            case PLAYING -> throw new RuntimeException("Tried ending game while still playing.");
            case PLAYER_ONE_WON -> localPlayer.getId() == GameBoard.PLAYER_ONE ? onWin() : onLoss();
            case PLAYER_TWO_WON -> localPlayer.getId() == GameBoard.PLAYER_TWO ? onWin() : onLoss();
            case DRAW -> onDraw();
        };

        String opponent = game.getOpponent(localPlayer).getUsername();
        addToLogs(String.format("%s: %s\n", text, opponent));

        game = null;
    }

    /**
     * A listener that listens to the network connecting and starts a local game
     * to mirror the online game.
     */
    private final InputListener onMatch = message -> {
        Map<String, String> obj = message.getObject();
        String oppenent = obj.get("OPPONENT");
        addToLogs("Start een match met: " + oppenent);

        // TODO: Replace with factory.
        game = switch (obj.get("GAMETYPE")) {
            case "Tic-tac-toe" -> new TicTacToe();
            case "Reversi" -> new Othello();
            default -> throw new RuntimeException("Unknown type of game: " + obj.get("GAMETYPE"));
        };

        Player playerOne = obj.get("PLAYERTOMOVE").equals(oppenent)
            ? new RemotePlayer(oppenent)
            : new AIPlayer(game, username);
        Player playerTwo = obj.get("PLAYERTOMOVE").equals(oppenent)
            ? new AIPlayer(game, username)
            : new RemotePlayer(oppenent);

        GameModelHelper helper = new OnlineHelper(this, game);
        game.initialize(helper, playerOne, playerTwo);

        Platform.runLater(() -> {
            gameContainer.getChildren().remove(gameGrid);
            gameGrid = new GameGrid(
                game.getBoard().getSize(),
                gameContainer.getHeight(),
                game.getBackgroundColor()
            );

            gameContainer.getChildren().add(gameGrid);

            Image black = new Image(game.getImage(GameBoard.PLAYER_ONE), 20, 20, false, true);
            Image white = new Image(game.getImage(GameBoard.PLAYER_TWO), 20, 20, false, true);

            playerOneText.setText(playerOne.getUsername());
            playerOneIcon.setImage(black);

            playerTwoText.setText(playerTwo.getUsername());
            playerTwoIcon.setImage(white);

            update(-1, playerOne);
        });
    };

    /**
     * A listener that listens to the user playlist and sets that visible in the GUI.
     */
    private final InputListener onPlayerList = message -> {
        List<String> playersList = new ArrayList<>(Arrays.asList(message.getArray()));

        Platform.runLater(() -> {
            onlineText.setText(String.format("Online: %d", playersList.size()));
            playersList.remove(username);

            playersListView.setItems(FXCollections.observableArrayList(playersList));
            playersListView.refresh();
        });
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
        int[] board = game.getBoard().getCells(); // TODO: Duplicated code.
        gameGrid.clearBoard(board.length);

        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0) {
                gameGrid.setTile(i, game.getImage(board[i]));
            }
        }

        addToLogs(String.format("%s was gezet door speler %s", move, player.getUsername()));
    }
}
