package com.andrewguerra.jaytracer.image;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class ImageAreaTest {
    public static ImageArea area, areaZero;

    @Before
    public void initializeAreas() {
        area = new ImageArea(ImageCoordinate.ORIGIN, new ImageCoordinate(3, 6));
        areaZero = new ImageArea(ImageCoordinate.ORIGIN, ImageCoordinate.ORIGIN);
    }

    @Test
    public void testConstructorTopLeftBottomRight() {
        ImageCoordinate topLeft = ImageCoordinate.ORIGIN;
        ImageCoordinate topRight = new ImageCoordinate(0, 6);
        ImageCoordinate bottomLeft = new ImageCoordinate(3, 0);
        ImageCoordinate bottomRight = new ImageCoordinate(3, 6); 

        assertEquals(topLeft, area.topLeft);
        assertEquals(topRight, area.topRight);
        assertEquals(bottomLeft, area.bottomLeft);
        assertEquals(bottomRight, area.bottomRight);
    }

    @Test
    public void testConstructorTopRightBottomLeft() {
        ImageArea actualArea = new ImageArea(new ImageCoordinate(0, 6), new ImageCoordinate(3, 0));
        ImageCoordinate topLeft = ImageCoordinate.ORIGIN;
        ImageCoordinate topRight = new ImageCoordinate(0, 6);
        ImageCoordinate bottomLeft = new ImageCoordinate(3, 0);
        ImageCoordinate bottomRight = new ImageCoordinate(3, 6); 

        assertEquals(topLeft, actualArea.topLeft);
        assertEquals(topRight, actualArea.topRight);
        assertEquals(bottomLeft, actualArea.bottomLeft);
        assertEquals(bottomRight, actualArea.bottomRight);
    }

    @Test
    public void testConstructorBottomLeftTopRight() {
        ImageArea actualArea = new ImageArea(new ImageCoordinate(3, 0), new ImageCoordinate(0, 6));
        ImageCoordinate topLeft = ImageCoordinate.ORIGIN;
        ImageCoordinate topRight = new ImageCoordinate(0, 6);
        ImageCoordinate bottomLeft = new ImageCoordinate(3, 0);
        ImageCoordinate bottomRight = new ImageCoordinate(3, 6); 

        assertEquals(topLeft, actualArea.topLeft);
        assertEquals(topRight, actualArea.topRight);
        assertEquals(bottomLeft, actualArea.bottomLeft);
        assertEquals(bottomRight, actualArea.bottomRight);
    }

    @Test
    public void testConstructorBottomRightTopLeft() {
        ImageArea actualArea = new ImageArea(new ImageCoordinate(3, 6), ImageCoordinate.ORIGIN);
        ImageCoordinate topLeft = ImageCoordinate.ORIGIN;
        ImageCoordinate topRight = new ImageCoordinate(0, 6);
        ImageCoordinate bottomLeft = new ImageCoordinate(3, 0);
        ImageCoordinate bottomRight = new ImageCoordinate(3, 6); 

        assertEquals(topLeft, actualArea.topLeft);
        assertEquals(topRight, actualArea.topRight);
        assertEquals(bottomLeft, actualArea.bottomLeft);
        assertEquals(bottomRight, actualArea.bottomRight);
    }

    @Test
    public void testSizeNonZero() {
        int size = area.size();

        assertEquals(18, size);
    }

    @Test
    public void testSizeZero() {
        int size = areaZero.size();

        assertEquals(0, size);
    }

    public static ImageArea getArea() {
        return area;
    }
    
    @Test
    public void testCoordinatesNonZero() {
        ImageCoordinate[] expectedCoordinates = new ImageCoordinate[area.size()];

        int index = 0;
        for(int row = area.topLeft.row; row < area.bottomLeft.row; row++) {
            for(int col = area.topLeft.col; col < area.topRight.col; col++) {
                expectedCoordinates[index++] = new ImageCoordinate(row, col);
            }
        }

        ImageCoordinate[] actualCoordinates = area.coordinates();

        assertArrayEquals(expectedCoordinates, actualCoordinates);
    }

    @Test
    public void testCoordinatesZero() {
        ImageCoordinate[] coordinates = areaZero.coordinates();

        assertEquals(0, coordinates.length);
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals(area, area);
    }

    @Test
    public void testEqualsSameValues() {
        ImageArea sameArea = new ImageArea(ImageCoordinate.ORIGIN, new ImageCoordinate(3, 6));

        assertEquals(area, sameArea);
    }

    @Test
    public void testEqualsNotSameClass() {
        Object object = new Object();

        assertNotEquals(area, object);
    }

    @Test
    public void testEqualsNotEquals() {
        ImageArea diffArea = new ImageArea(new ImageCoordinate(1, 1), new ImageCoordinate(2, 2));

        assertNotEquals(area, diffArea);
    }

    @Test
    public void testEqualsNotEqualsTopLeft() {
        ImageArea diffArea = new ImageArea(new ImageCoordinate(1, 1), new ImageCoordinate(3, 6));

        assertNotEquals(area, diffArea);
    }

    @Test
    public void testEqualsNotEqualsBottomRight() {
        ImageArea diffArea = new ImageArea(ImageCoordinate.ORIGIN, new ImageCoordinate(1, 1));

        assertNotEquals(area, diffArea);
    }
}
