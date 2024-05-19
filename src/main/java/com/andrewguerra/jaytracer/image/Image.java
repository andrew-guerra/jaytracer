package com.andrewguerra.jaytracer.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.andrewguerra.jaytracer.render.Color;

/**
 * Class to represent an image as a matrix of pixels with an associated color.
 */
public class Image {
    public final Color[][] pixels;
    private final int height, width;
    private final ImageArea area;

    /**
     * Constructor for an image with a matrix of pixel colors.
     * 
     * @param pixels Matrix of pixel colors
     */
    public Image(Color[][] pixels) {
        this.pixels = pixels;
        this.height = pixels.length;
        this.width = pixels[0].length;
        this.area = new ImageArea(ImageCoordinate.ORIGIN, new ImageCoordinate(this.height, this.width));;
    }

    /**
     * Constructor for an image defined at the given filepath
     * 
     * @param filepath The filepath to an image file
     */
    public Image(String filepath) throws IOException {
        BufferedImage image = ImageIO.read(new File(filepath));
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.area = new ImageArea(ImageCoordinate.ORIGIN, new ImageCoordinate(this.height, this.width));
        pixels = new Color[this.height][this.width];

        int red, green, blue, rgb;
        for(int row = 0; row < this.height; row++) {
            for(int col = 0; col < this.width; col++) {
                rgb = image.getRGB(col, row);
                red = ((rgb >> 16) & 0xff);
                green = (rgb >> 8) & 0xff;
                blue = rgb & 0xff;

                this.pixels[row][col] = new Color(red / 255.0, green /255.0, blue / 255.0);
            }
        }
    }

    /**
     * Constructor for an image with a width and height with all the pixels having a shared color.
     * 
     * @param height The height of the image
     * @param width The widht of the image
     * @param color The color of the image pixels
     */
    public Image(int height, int width, Color color) {
        this.pixels = new Color[height][width];
        this.height = height;
        this.width = width;
        this.area = new ImageArea(ImageCoordinate.ORIGIN, new ImageCoordinate(this.height, this.width));

        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                pixels[row][col] = color;
            }
        }
    }

    /**
     * Constructor for an all blakc image of width and height.
     * @param height The height of the image
     * @param width The widht of the image
     */
    public Image(int height, int width) {
        this(height, width, Color.BLACK);
    }

    /**
     * Returns the height of the image.
     * 
     * @return The height of the image
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of the image.
     * 
     * @return The widht of the image.
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the image area that represents the entire image
     * 
     * @return The image area that represents the full area of the image
     */
    public ImageArea getImageArea() {
        return this.area;
    }

    /**
     * Returns the color of the pixel at the image coordiante.
     * 
     * @param coordinate The coordinate to get the pixel color at
     * @return The color of the pixel at the image coordiante
     */
    public Color getPixel(ImageCoordinate coordinate) {
        if(isIllegalCoordinate(coordinate)) {
            throw new IllegalArgumentException("Coordinate must be in bounds of image");
        }

        return pixels[coordinate.row][coordinate.col];
    }

    private boolean isIllegalCoordinate(ImageCoordinate coordinate) {
        return coordinate.row >= this.height || coordinate.col >= this.width;
    }

    /**
     * Returns the image result of adding the pixel colors of this image and otherImage.
     * 
     * @param otherImage The other image to add to this image
     * @return The image result of adding the pixel colors of this image and otherImage
     */
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

    /**
     * Returns wether this image contains targetImage at the image area. The area acts as a search region to match the target image.
     * If the area of targetImage and area are not equivalent, then an illegal argument excpetion will be thrown.
     * 
     * @param targetImage The image to search for
     * @param area The area of this image to search at
     * @return Returns wether this image contains targetImage at the image area
     */
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
