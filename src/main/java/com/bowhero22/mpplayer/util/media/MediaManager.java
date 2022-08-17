package com.bowhero22.mpplayer.util.media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.misc.Unsafe;

import java.io.File;
import java.lang.reflect.Field;

public class MediaManager {
    private Media media;
    private MediaPlayer mediaPlayer;

    private Unsafe unsafe;


    public MediaManager() {
        //Creates Media, and MediaPlayer class' instance with no-parameter constructor using unsafe.
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);

            media = (Media) unsafe.allocateInstance(Media.class);
            mediaPlayer = (MediaPlayer) unsafe.allocateInstance(MediaPlayer.class);

            addListeners();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public MediaManager(String file) {
        //Normally creates Media, and MediaPlayer class' instance
        media = getMedia(file);
        mediaPlayer = getMediaPlayer(media);

        addListeners();
    }

    public MediaManager(Media media, MediaPlayer mediaPlayer) {
        if (media == null || mediaPlayer == null) {
            throw new NullPointerException();
        } else {
            this.media = media;
            this.mediaPlayer = mediaPlayer;

            addListeners();
        }
    }

    public void play() {
        mediaPlayer.play();
    }

    public void play(String file) {
        media = getMedia(file);
        mediaPlayer = getMediaPlayer(media);
        addListeners();

        mediaPlayer.play();
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

    private Media getMedia(String file) {
        return new Media(new File(file).toURI().toString());
    }

    private MediaPlayer getMediaPlayer(Media media) {
        return new MediaPlayer(media);
    }

    private void addListeners() {
        MediaListeners.endOfMediaListeners.forEach(e -> mediaPlayer.setOnEndOfMedia(e));
        MediaListeners.playingListeners.forEach(e -> mediaPlayer.setOnPlaying(e));
        MediaListeners.errorListeners.forEach(e -> mediaPlayer.setOnError(e));
        MediaListeners.haltedListeners.forEach(e -> mediaPlayer.setOnHalted(e));
        MediaListeners.markerListeners.forEach(e -> mediaPlayer.setOnMarker(e));
        MediaListeners.pausedListeners.forEach(e -> mediaPlayer.setOnPaused(e));
        MediaListeners.readyListeners.forEach(e -> mediaPlayer.setOnReady(e));
        MediaListeners.repeatListeners.forEach(e -> mediaPlayer.setOnRepeat(e));
        MediaListeners.stalledListeners.forEach(e -> mediaPlayer.setOnStalled(e));
        MediaListeners.stoppedListeners.forEach(e -> mediaPlayer.setOnStopped(e));
    }
}
