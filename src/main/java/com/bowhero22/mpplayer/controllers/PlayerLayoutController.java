package com.bowhero22.mpplayer.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlayerLayoutController implements Initializable {


    @FXML
    private TreeView<String> albumTreeView;

    @FXML
    private TreeView<String> artistTreeView;

    @FXML
    private TreeView<String> genreTreeView;

    @FXML
    private Pagination musicLibPagination;

    @FXML
    private TreeView<String> musicInfoTreeView;

    public static ImageView[] albumImageView;

    public static ImageView[] artistImageView;

    public static  ImageView[] genreImageView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        artistImageView = createNewImageView(50, 50, 512);
        albumImageView = createNewImageView(50, 50, 512);
        genreImageView = createNewImageView(50, 50, 512);

        TreeItem<String> artistRoot = new TreeItem<>("Artist", albumImageView[0]);
        TreeItem<String> albumRoot = new TreeItem<>("Album", artistImageView[0]);
        TreeItem<String> genreRoot = new TreeItem<>("Genre", genreImageView[0]);

        artistTreeView.setRoot(artistRoot);
        albumTreeView.setRoot(albumRoot);
        genreTreeView.setRoot(genreRoot);

    }

    private ImageView[] createNewImageView(int height, int width, int size) {
        ImageView[] imageView = new ImageView[size];
        for(ImageView i : imageView) {
            i = new ImageView();
            i.setFitHeight(50);
            i.setFitWidth(50);
        }

        return imageView;
    }

    //Do some pi things pi is

}
