package com.headtrixz.ui;

import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UIManager extends Application {
    private static Stage mainStage;
    private static Preferences preferences;

    @Override
    public void start(Stage stage) {
        mainStage = stage;

        stage.setTitle("ISY Project");
        UIManager.switchScreen("home");
        stage.show();
    }

    public static void setSetting(String key, String value) {
        preferences = Preferences.userNodeForPackage(UIManager.class);
        preferences.put(key, value);
    }

    public static String getSetting(String key, String def) {
        preferences = Preferences.userNodeForPackage(UIManager.class);
        return preferences.get(key, def);
    }

    public static String getSetting(String key) {
        return getSetting(key, "");
    }

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
