package com.bowhero22.mpplayer.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXUtil {
    public static ImageView[] getImageView(int height, int width, int arrsize, Image icon) {
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
