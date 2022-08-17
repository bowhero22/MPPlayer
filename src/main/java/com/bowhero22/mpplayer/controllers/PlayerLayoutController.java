package com.bowhero22.mpplayer.controllers;

import com.bowhero22.mpplayer.util.FXUtil;
import com.bowhero22.mpplayer.util.media.MediaListeners;
import com.bowhero22.mpplayer.util.media.MediaManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerLayoutController implements Initializable {
    @FXML
    private TreeView<String> albumTreeView;

    @FXML
    private TreeView<String> artistTreeView;

    @FXML
    private TreeView<String> genreTreeView;

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

    private boolean isPlaying;

    private final String linuxMFile = "^(/[^/ ]*)+/?$";

    private final String winMP3File = "[a-zA-Z]:[\\\\\\/](?:[a-zA-Z0-9]+[\\\\\\/])*([a-zA-Z0-9]+\\.mp3)";
    private final String winWAVFile = "[a-zA-Z]:[\\\\\\/](?:[a-zA-Z0-9]+[\\\\\\/])*([a-zA-Z0-9]+\\.wav)";

    private MediaManager mediaManager = new MediaManager();

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

        musicLibListView.setEditable(true);

        setUpActionHandlers();
        setUpDragDrop();
        setUpTreeView();
    }


    private void setUpActionHandlers() {
        musicLibListView.setOnEditStart(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> event) {
                if (!isPlaying) {
                    isPlaying = true;
                    playMedia();
                }
            }
        });

        nextBtn.setOnAction((ActionEvent) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mgrIndex(true);
                    mediaManager.getMediaPlayer().dispose();

                    playMedia();
                    isPlaying = true;
                }
            });
        });

        playBtn.setOnAction((ActionEvent) -> {
            if (!isPlaying) {
                new Thread(() -> {
                    playMedia();
                    isPlaying = true;

                    if (mediaManager.getMediaPlayer().getStatus() == MediaPlayer.Status.STOPPED) {
                        mgrIndex(true);
                    }
                }).start();
            }
        });

        playRandomBtn.setOnAction((ActionEvent) -> {
            Random rand = new Random(System.currentTimeMillis() * 10000);
            long r = rand.nextLong(0, musicLibListView.getItems().size());

            mediaManager.getMediaPlayer().dispose();

            playMedia();
        });

        previousBtn.setOnAction((ActionEvent) -> {
            mgrIndex(false);
            mediaManager.getMediaPlayer().dispose();

            playMedia();
            isPlaying = true;
        });

        MediaListeners.setOnEndOfMedia(() -> {
            isPlaying = false;
        });

        MediaListeners.setOnPlaying(() -> {
            isPlaying = true;
        });
    }

    private void setUpDragDrop() {
        //Drag-and-Drop settings(initialization)
        musicLibVBox.setOnDragOver((DragEvent event)
                -> {
            AtomicBoolean isMusicFile = new AtomicBoolean(false);

            event.getDragboard().getFiles().forEach(f -> {
                if (f.getAbsolutePath().matches(linuxMFile) || f.getAbsolutePath().matches(winMP3File) || f.getAbsolutePath().matches(winWAVFile)) {
                    isMusicFile.set(true);
                } else {
                    isMusicFile.set(false);
                }
            });

            if (event.getGestureSource() != musicLibVBox
                    && event.getDragboard().hasFiles()
                    && isMusicFile.get()) {
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

            if (event.getDragboard().hasFiles()) {
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

    private void setUpTreeView() {
        //TO-DO: Create new icon and apply icon to tree view.
        artistImageView = FXUtil.getImageView(50, 50, 512, null);
        albumImageView = FXUtil.getImageView(50, 50, 512, null);
        genreImageView = FXUtil.getImageView(50, 50, 512, null);
        generalImageView = FXUtil.getImageView(50, 50, 512, null);

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

    private void playMedia() {
        mediaManager.play(musicLibListView.getItems().get(curIndex));

        System.gc();
    }

    private void mgrIndex(boolean increase) {
        if (increase) {
            if (musicLibListView.getItems().size() < curIndex++) {
                curIndex = 0;
            } else {
                curIndex++;
            }
        } else {
            if (curIndex-- < 0) {
                curIndex = musicLibListView.getItems().size();
            } else {
                curIndex--;
            }
        }
    }
}
