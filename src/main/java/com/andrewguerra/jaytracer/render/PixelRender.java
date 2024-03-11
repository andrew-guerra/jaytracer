package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

/**
 * A class to represent the render information for a single pixel
 */
public class PixelRender {
    /**
     * The pixel matrix that contains the pixel.
     */
    public final Color[][] pixels;

    /**
     * The row of the pixel.
     */
    public final int row;

    /**
     * The col of the pixel.
     */
    public final int col;

    /**
     * The ray that corresponds to the pixel
     */
    public final Ray ray;

    /**
     * Constructor with a pixel matrix, row, col, and ray.
     * 
     * @param pixels the pixel matrix that contains the pixel
     * @param row the row of the pixel
     * @param col the col of the pixel
     * @param ray the ray of the pixel
     */
    public PixelRender(Color[][] pixels, int row, int col, Ray ray) {
        this.pixels = pixels;
        this.row = row;
        this.col = col;
        this.ray = ray;
    }
}
