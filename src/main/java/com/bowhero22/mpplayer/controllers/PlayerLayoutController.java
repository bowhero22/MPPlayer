package com.bowhero22.mpplayer.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

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

    public static ImageView[] genreImageView;

    public static ImageView[] generalImageView;

    public static TreeItem<String> artistRoot;
    public static TreeItem<String> albumRoot;
    public static TreeItem<String> genreRoot;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TO-DO: Create new icon and apply icon to tree view.
        artistImageView = createNewImageView(50, 50, 512, null);
        albumImageView = createNewImageView(50, 50, 512, null);
        genreImageView = createNewImageView(50, 50, 512, null);
        generalImageView = createNewImageView(50,50,512,null);

        artistRoot = new TreeItem<>("Artist", albumImageView[0]);
        albumRoot = new TreeItem<>("Album", artistImageView[0]);
        genreRoot = new TreeItem<>("Genre", genreImageView[0]);

        artistTreeView.setRoot(artistRoot);
        albumTreeView.setRoot(albumRoot);
        genreTreeView.setRoot(genreRoot);

        TreeItem<String> noItem = new TreeItem<>("No Item", generalImageView[0]);

        //TreeView initialization with "No Item" item.

        artistRoot.getChildren().add(noItem);
        albumRoot.getChildren().add(noItem);
        genreRoot.getChildren().add(noItem);
    }

    private ImageView[] createNewImageView(int height, int width, int arrsize, Image icon) {
        Boolean noIcon = icon == null;

        ImageView[] imageView = new ImageView[arrsize];
        for(ImageView i : imageView) {
            if(noIcon) {
                i = new ImageView();
            } else {
                i = new ImageView(icon);
            }
            i.setFitHeight(height);
            i.setFitWidth(width);
        }

        return imageView;
    }

    //Do some pi things pi is

}
