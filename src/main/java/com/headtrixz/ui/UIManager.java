package com.headtrixz.ui;

import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class for the GUI.
 */
public class UIManager extends Application {
    private static Stage mainStage;
    private static Preferences preferences;

    /**
     * Start the GUI.
     *
     * @param stage the main stage.
     */
    @Override
    public void start(Stage stage) {
        mainStage = stage;

        stage.setTitle("Roos&LeeSpelen");
        stage.setMaxHeight(400);
        stage.setMaxWidth(600);

        UIManager.switchScreen("home");
        stage.show();
    }

    /**
     * A wrapper to save settings under this class.
     *
     * @param key The key of the setting
     * @param value The value of the setting
     */
    public static void setSetting(String key, String value) {
        preferences = Preferences.userNodeForPackage(UIManager.class);
        preferences.put(key, value);
    }

    /**
     * A wrapper to get a saved setting from this class.
     *
     * @param key The key under which the setting was saved.
     * @param def A default value for when the key is not found.
     * @return The value of the setting, or a empty string when not found.
     */
    public static String getSetting(String key, String def) {
        preferences = Preferences.userNodeForPackage(UIManager.class);
        return preferences.get(key, def);
    }

    /**
     * A wrapper to get a saved setting from this class.
     *
     * @param key The key under which the setting was saved.
     * @return The value of the setting, or a empty string when not found.
     */
    public static String getSetting(String key) {
        return getSetting(key, "");
    }

    /**
     * A helper method to switch screens based on the file name.
     *
     * @param name the name of the file to switch to. Must be without the `.fxml` extension.
     */
    public static void switchScreen(String name) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(UIManager.class.getResource(name + ".fxml"));
            mainStage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            mainStage.setResizable(false);
        } catch (IOException e) {
            System.out.println("Something went wrong whilst switching screens");
            e.printStackTrace();
        }
    }

    /**
     * A helper method to switch screens based on the file name.
     *
     * @param name the name of the file to switch to. Must be without the `.fxml` extension.
     * @param controller the controller to give to the FXML.
     */
    public static void switchScreen(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(UIManager.class.getResource(name + ".fxml"));
            fxmlLoader.setController(controller);

            mainStage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            mainStage.setResizable(false);
        } catch (IOException e) {
            System.out.println("Something went wrong whilst switching screens");
            e.printStackTrace();
        }
    }
}
