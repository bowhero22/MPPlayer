package com.bowhero22.mpplayer.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.concurrent.*;

public class MediaManager  {
    private Media media;
    private MediaPlayer mediaPlayer;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private int index = 0;

    public MediaManager() {}

    public void play(String file) {
        media = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.play();
    }

    private void createNewMedia(String file) {
        media = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public Duration getFileDuration() {
        return mediaPlayer.getTotalDuration();
    }

    public Duration getCurTime() {
        return mediaPlayer.getCurrentTime();
    }

    public Media getMedia() {
        return media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
