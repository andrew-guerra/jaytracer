package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.image.Image;
import com.andrewguerra.jaytracer.image.ImageCoordinate;
import com.andrewguerra.jaytracer.math.Vector3;

public class ImageTexture extends Texture {
    private Image image;
    
    public ImageTexture(Image image) {
        this.image = image;
    }

    @Override
    public Color value(double u, double v, Vector3 point) {
        double uImage = u;
        double vImage = 1 - v;

        int row = (int) Math.round(vImage * (image.getHeight() - 1));
        int col = (int) Math.round(uImage * (image.getWidth() - 1));
        ImageCoordinate coordinate = new ImageCoordinate(row, col);

        return image.getPixel(coordinate);
    }
}
