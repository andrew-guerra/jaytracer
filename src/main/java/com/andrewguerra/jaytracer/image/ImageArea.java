package com.andrewguerra.jaytracer.image;

/**
 * Class to represent a set of coordinates within an image. This rectangular area can act as a mask to retrieve a
 * subset of an image.
 */
public class ImageArea {
    /**
     * The image coordinate of the top left point of the image area
     */
    public final ImageCoordinate topLeft;
    
    /**
     * The image coordinate of the top right point of the image area
     */
    public final ImageCoordinate topRight;

    /**
     * The image coordinate of the bottom left point of the image area
     */
    public final ImageCoordinate bottomLeft;
     
    /**
     * The image coordinate of the bottom right point of the image area
     */
    public final ImageCoordinate bottomRight;

    private final int height, width;

    /**
     * Constructor for an image area with two arbitrary cooridinates. These coordinates can be in any order.
     * The constructed image area will be the smallest rectangle bounded by coord1 and coord2.
     * 
     * @param coord1 A corner of the image area
     * @param coord2 A corner of the image area
     */
    public ImageArea(ImageCoordinate coord1, ImageCoordinate coord2) {
        int minRow = Math.min(coord1.row, coord2.row);
        int minCol = Math.min(coord1.col, coord2.col);
        int maxRow = Math.max(coord1.row, coord2.row);
        int maxCol = Math.max(coord1.col, coord2.col);

        this.topLeft = new ImageCoordinate(minRow, minCol);
        this.topRight = new ImageCoordinate(minRow, maxCol);
        this.bottomLeft = new ImageCoordinate(maxRow, minCol);
        this.bottomRight = new ImageCoordinate(maxRow, maxCol);

        this.height = this.topLeft.distance(this.bottomLeft);
        this.width = this.topLeft.distance(this.topRight);
    }

    /**
     * Returns the amount of pixels contained in the image area.
     * 
     * @return The amount pixels contained in the image area
     */
    public int size() {
        return this.height * this.width;
    }

    /**
     * Returns a coordinate array of the coordinates contained in the image area. The ordering will be rows of increasing
     * value, orderd from columns of increasing value.
     * 
     * @return The coordinates contained in the image area
     */
    public ImageCoordinate[] coordinates() {
        ImageCoordinate[] coordinates = new ImageCoordinate[this.size()];

        int index = 0;
        for(int row = topLeft.row; row < bottomLeft.row; row++) {
            for(int col = topLeft.col; col < topRight.col; col++) {
                coordinates[index++] = new ImageCoordinate(row, col);
            }
        }
        
        return coordinates;
    }

    /**
     * Returns wether this image area shares the same dimensions of otherArea. This will also
     * ensure that the areas have the same orientation unlike {@link ImageArea#size()}.
     * 
     * @param otherArea the other area to check for dimensional equality
     * @return wether the image areas share the same dimension
     */
    public boolean sharesDimensions(ImageArea otherArea) {
        return this.height == otherArea.height && this.width == otherArea.width;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj.getClass() != ImageArea.class) {
            return false;
        }

        ImageArea otherArea = (ImageArea) obj;

        return this.topLeft.equals(otherArea.topLeft) && this.bottomRight.equals(otherArea.bottomRight);
    }
}
