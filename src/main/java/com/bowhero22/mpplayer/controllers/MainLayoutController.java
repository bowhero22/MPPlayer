package com.bowhero22.mpplayer.controllers;

import com.bowhero22.mpplayer.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainLayoutController {

    @FXML
    protected void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainLayout.fxml"));
        MainApplication.scene = new Scene(fxmlLoader.load(), 320, 240);
    }
}