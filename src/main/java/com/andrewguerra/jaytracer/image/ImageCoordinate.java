package com.andrewguerra.jaytracer.image;

public class ImageCoordinate {
    public final int row, col;

    public static final ImageCoordinate ORIGIN = new ImageCoordinate(0, 0);

    /**
     * 
     * @param row
     * @param col
     */
    public ImageCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //manhattan distance
    /**
     * 
     * @param otherCoordinate
     * @return
     */
    public int distance(ImageCoordinate otherCoordinate) {
        return Math.abs(this.row - otherCoordinate.row) + Math.abs(this.col - otherCoordinate.col);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj.getClass() != ImageCoordinate.class) {
            return false;
        }

        ImageCoordinate coordinate = (ImageCoordinate) obj;

        return this.row == coordinate.row && this.col == coordinate.col;
    }
}
