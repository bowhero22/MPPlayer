package com.bowhero22.mpplayer.util.media;

import javafx.event.EventHandler;
import javafx.scene.media.MediaMarkerEvent;

import java.util.ArrayList;
import java.util.Collection;

public class MediaListeners {
    static Collection<Runnable> endOfMediaListeners = new ArrayList<>();
    static Collection<Runnable> playingListeners = new ArrayList<>();
    static Collection<Runnable> errorListeners = new ArrayList<>();
    static Collection<Runnable> haltedListeners = new ArrayList<>();
    static Collection<EventHandler<MediaMarkerEvent>> markerListeners = new ArrayList<>();
    static Collection<Runnable> pausedListeners = new ArrayList<>();
    static Collection<Runnable> readyListeners = new ArrayList<>();
    static Collection<Runnable> repeatListeners = new ArrayList<>();
    static Collection<Runnable> stalledListeners = new ArrayList<>();
    static Collection<Runnable> stoppedListeners = new ArrayList<>();

    public static void setOnEndOfMedia(Runnable runnable) {
        endOfMediaListeners.add(runnable);
    }

    public static void setOnPlaying(Runnable runnable) {
        playingListeners.add(runnable);
    }

    public static void setOnError(Runnable runnable) {
        errorListeners.add(runnable);
    }

    public static void setOnHalted(Runnable runnable) {
        haltedListeners.add(runnable);
    }

    public static void setOnMaker(EventHandler<MediaMarkerEvent> eventHandler) {
        markerListeners.add(eventHandler);
    }

    public static void setOnPaused(Runnable runnable) {
        pausedListeners.add(runnable);
    }

    public static void setOnReady(Runnable runnable) {
        readyListeners.add(runnable);
    }

    public static void setOnRepeat(Runnable runnable) {
        repeatListeners.add(runnable);
    }

    public static void setOnStalled(Runnable runnable) {
        stalledListeners.add(runnable);
    }

    public static void setOnStopped(Runnable runnable) {
        stoppedListeners.add(runnable);
    }
}
