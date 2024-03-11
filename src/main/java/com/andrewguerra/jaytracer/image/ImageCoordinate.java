package com.andrewguerra.jaytracer.image;

/**
 * Class to represent a coordinate within an image, with the origin (0,0) located at the top left corner.
 */
public class ImageCoordinate {
    /**
     * The row of the coordinate.
     */
    public final int row;
    
    /**
     * The column of the coordinate.
     */
    public final int col;

    /**
     * The origin coordinate of an image
     */
    public static final ImageCoordinate ORIGIN = new ImageCoordinate(0, 0);

    /**
     * Constuctor for a coordinate with a row and a column
     * 
     * @param row The row of the coordinate
     * @param col The column of the coordinate
     */
    public ImageCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //manhattan distance
    /**
     * Returns the <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Manhattan distance</a> between this coordinate and otherCoordinate.
     * 
     * @param otherCoordinate The other coordinate to calculate the distance to
     * @return The <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Manhattan distance</a> between this coordinate and otherCoordinate
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
