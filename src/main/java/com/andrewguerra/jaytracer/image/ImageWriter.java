package com.andrewguerra.jaytracer.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class of utility function to write an Image object to a file
 */
public class ImageWriter {
    /**
     * Writes the image to a file with the given filename
     * 
     * @param image the image to write
     * @param filename the file name to write to
     */
    public static void writeImage(Image image, String filename) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for(int row = 0; row < image.getHeight(); row++) {
            for(int col = 0; col < image.getWidth(); col++) {
                bufferedImage.setRGB(col, row, image.pixels[row][col].toRGB());
            }
        }

        File imageFile = new File(filename);

        try {
            ImageIO.write(bufferedImage, "png", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
