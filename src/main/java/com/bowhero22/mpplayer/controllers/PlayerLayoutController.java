package com.bowhero22.mpplayer.controllers;

import com.bowhero22.mpplayer.util.MediaManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
    private ListView<String> musicLibListView;

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

    private final String linuxMFile = "^(/[^/ ]*)+/?$";

    private final String winMP3File = "^[0-9]{3}x[0-9]{3}.mp3$";
    private final String winWAVFile = "^[0-9]{3}x[0-9]{3}.wav$";

    private MediaManager mediaManager  = new MediaManager();

    private int curIndex;

    public static ImageView[] albumImageView;

    public static ImageView[] artistImageView;

    public static ImageView[] genreImageView;

    public static ImageView[] generalImageView;

    public static TreeItem<String> artistRoot;
    public static TreeItem<String> albumRoot;
    public static TreeItem<String> genreRoot;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        curIndex = 0;

        /**
         *         nextBtn.setDisable(true);
         *         playBtn.setDisable(true);
         *         playRandomBtn.setDisable(true);
         *         previousBtn.setDisable(true);
         */

        musicLibListView.setEditable(true);
        musicLibListView.setCellFactory(TextFieldListCell.forListView());

        nextBtn.setOnAction((ActionEvent) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    curIndex++;

                    playMedia();
                }
            });
        });

        playBtn.setOnAction((ActionEvent) -> {
            new Thread(() -> {
                playMedia();

                mediaManager.getMediaPlayer().setOnEndOfMedia(() -> {
                    if(musicLibListView.getItems().size() <= curIndex) {
                        curIndex = 0;
                    } else {
                        curIndex++;
                    }
                    playMedia();
                });
            }).start();
        });

        playRandomBtn.setOnAction((ActionEvent) -> {
            Random rand = new Random(System.currentTimeMillis() * 10000);
            long r = rand.nextLong(0, musicLibListView.getItems().size());

            playMedia();
        });

        previousBtn.setOnAction((ActionEvent) -> {
            curIndex--;

            playMedia();
        });


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
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        musicLibVBox.setOnDragDropped((DragEvent event)
                -> {
            event.acceptTransferModes(TransferMode.ANY);
            Dragboard db = event.getDragboard();

            ObservableList<String> curListView = musicLibListView.getItems();

            boolean success = false;

            AtomicBoolean isMusicFile = new AtomicBoolean(false);

            event.getDragboard().getFiles().forEach(f -> {
                if(f.getAbsolutePath().matches(linuxMFile) || f.getAbsolutePath().matches(winMP3File) || f.getAbsolutePath().matches(winWAVFile)) {
                    isMusicFile.set(true);
                } else {
                    isMusicFile.set(false);
                }
            });

            if (event.getDragboard().hasFiles() && isMusicFile.get()) {
                db.getFiles().forEach(f -> curListView.add((String) f.getAbsolutePath()));
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

    //Plays media with current index.
    private void playMedia() {
        mediaManager.play(
                (String) musicLibListView.getItems()
                .stream()
                .map(object -> Objects.toString(object, null))
                .collect(Collectors.toList())
                .get(curIndex));
    }
}
