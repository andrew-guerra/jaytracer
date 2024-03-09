package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

public class PixelRender {
    public final Color[][] pixels;
    public final int row, col;
    public final Ray ray;

    /**
     * 
     * @param pixels
     * @param row
     * @param col
     * @param ray
     */
    public PixelRender(Color[][] pixels, int row, int col, Ray ray) {
        this.pixels = pixels;
        this.row = row;
        this.col = col;
        this.ray = ray;
    }
}
