package com.bowhero22.mpplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainLayoutController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}