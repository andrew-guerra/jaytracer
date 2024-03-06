package com.andrewguerra.jaytracer.image;

import com.andrewguerra.jaytracer.render.Color;

public class Image {
    public final Color[][] pixels;
    private final int height, width;
    private final ImageArea area;

    public Image(Color[][] pixels) {
        this.pixels = pixels;
        this.height = pixels.length;
        this.width = pixels[0].length;
        this.area = new ImageArea(ImageCoordinate.ORIGIN, new ImageCoordinate(this.height, this.width));;
    }

    public Image(int height, int width, Color color) {
        pixels = new Color[height][width];
        this.height = height;
        this.width = width;
        this.area = new ImageArea(ImageCoordinate.ORIGIN, new ImageCoordinate(this.height, this.width));

        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                pixels[row][col] = color;
            }
        }
    }

    public Image(int height, int width) {
        this(height, width, Color.BLACK);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public ImageArea getImageArea() {
        return this.area;
    }

    public Color getPixel(ImageCoordinate coordinate) {
        if(isIllegalCoordinate(coordinate)) {
            throw new IllegalArgumentException("Coordinate must be in bounds of image");
        }

        return pixels[coordinate.row][coordinate.col];
    }

    private boolean isIllegalCoordinate(ImageCoordinate coordinate) {
        return coordinate.row >= this.height || coordinate.col >= this.width;
    }

    public Image add(Image otherImage) {
        if(!this.sharesDimensions(otherImage)) {
            throw new IllegalArgumentException("Images must have same dimensions to add");
        }

        Color[][] newPixels = new Color[this.height][this.width];
        for(int row = 0; row < this.height; row++) {
            for(int col = 0; col < this.width; col++) {
                pixels[row][col] = this.pixels[row][col].add(otherImage.pixels[row][col]);
            }
        }

        return new Image(newPixels);
    }

    private boolean sharesDimensions(Image otherImage) {
        return this.pixels.length == otherImage.pixels.length && this.pixels[0].length == otherImage.pixels[0].length;
    }

    public boolean containsImage(Image targetImage, ImageArea area) {
        if(!targetImage.area.sharesDimensions(area)) {
            throw new IllegalArgumentException("Subimage area must share dimensions with search area");
        }

        ImageCoordinate[] searchAreaCoordinates = area.coordinates();
        ImageCoordinate[] subImageCoordinates = targetImage.area.coordinates();

        for(int i = 0; i < area.size(); i++) {
            if(!this.getPixel(searchAreaCoordinates[i]).equals(targetImage.getPixel(subImageCoordinates[i]))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj.getClass() != IllegalAccessError.class) {
            return false;
        }

        Image otherImage = (Image) obj;

        return this.containsImage(otherImage, this.area);
    }
}
