package com.andrewguerra.jaytracer.image;

public class ImageArea {
    public final ImageCoordinate topLeft, topRight, bottomLeft, bottomRight;
    private final int height, width;

    /**
     * 
     * @param coord1
     * @param coord2
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
     * 
     * @return
     */
    public int size() {
        return this.height * this.width;
    }

    /**
     * 
     * @return
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
     * 
     * @param otherArea
     * @return
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
