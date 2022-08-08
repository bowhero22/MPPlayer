package com.bowhero22.mpplayer.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerLayoutController implements Initializable {


    @FXML
    private TreeView<String> albumTreeView;

    @FXML
    private TreeView<String> artistTreeView;

    @FXML
    private TreeView<String> genreTreeView;

    @FXML
    private TreeView<String> musicInfoTreeView;

    @FXML
    private ListView<Object> musicLibListView;

    @FXML
    private VBox musicLibVBox;

    @FXML
    private Button nextBtn;

    @FXML
    private Button playBtn;

    @FXML
    private Button playRandomBtn;

    @FXML
    private Button previousBtn;

    public static ImageView[] albumImageView;

    public static ImageView[] artistImageView;

    public static ImageView[] genreImageView;

    public static ImageView[] generalImageView;

    public static TreeItem<String> artistRoot;
    public static TreeItem<String> albumRoot;
    public static TreeItem<String> genreRoot;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nextBtn.setDisable(true);
        playBtn.setDisable(true);
        playRandomBtn.setDisable(true);
        previousBtn.setDisable(true);

        //TO-DO: Create new icon and apply icon to tree view.
        artistImageView = createNewImageView(50, 50, 512, null);
        albumImageView = createNewImageView(50, 50, 512, null);
        genreImageView = createNewImageView(50, 50, 512, null);
        generalImageView = createNewImageView(50, 50, 512, null);

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


        //Drag-and-Drop settings(initialization)
        musicLibVBox.setOnDragOver((DragEvent event)
                -> {
            if (event.getGestureSource() != musicLibVBox
                    && (event.getDragboard().hasUrl()) || event.getDragboard().hasString() || event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        musicLibVBox.setOnDragDropped((DragEvent event)
                -> {
            event.acceptTransferModes(TransferMode.ANY);
            Dragboard db = event.getDragboard();

            ObservableList<Object> curListView = musicLibListView.getItems();

            boolean success = false;

            if (event.getDragboard().hasFiles()) {
                db.getFiles().forEach(f -> curListView.add((String) f.getName()));
                musicLibListView.setItems(curListView);

                success = true;
            } else if (event.getDragboard().hasUrl() && event.getDragboard().getUrl().startsWith("https://www.youtube.com")) {
                curListView.add((String) db.getUrl());
                musicLibListView.setItems(curListView);

                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private ImageView[] createNewImageView(int height, int width, int arrsize, Image icon) {
        Boolean noIcon = icon == null;

        ImageView[] imageView = new ImageView[arrsize];
        for (ImageView i : imageView) {
            if (noIcon) {
                i = new ImageView();
            } else {
                i = new ImageView(icon);
            }
            i.setFitHeight(height);
            i.setFitWidth(width);
        }

        return imageView;
    }
}
