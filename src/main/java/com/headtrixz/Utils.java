package com.headtrixz;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Utils {
    public static void goTo(Text text, String location) {
        try {
            Stage screen = (Stage) text.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(text.getClass().getResource(location + ".fxml"));
            screen.setScene(new Scene(fxmlLoader.load(), 600, 400));
        } catch (Exception e) {
            System.out.println("Whooppppss something went wrong");
            System.out.println(e);
        }
    }
}
