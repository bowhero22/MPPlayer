package com.bowhero22.mpplayer.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerLayoutController implements Initializable {
    @FXML
    private TreeView<String> libraryTreeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
