package com.bowhero22.mpplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage stage;
    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerLayout.fxml"));
        scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("MPPlayer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        MainApplication.stage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}