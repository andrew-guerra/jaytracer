package com.andrewguerra.jaytracer.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class ImageCoordinateTest {
    private static ImageCoordinate coordinate;

    @Before
    public void initializeCoordinate() {
        coordinate = new ImageCoordinate(1, 2);
    }

    @Test
    public void testConstructor() {    
        assertEquals(1, coordinate.row);
        assertEquals(2, coordinate.col);
    }

    @Test
    public void testDistance() {
        int distance = coordinate.distance(ImageCoordinate.ORIGIN);

        assertEquals(3, distance);
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals(coordinate, coordinate);
    }

    @Test
    public void testEqualsSameValues() {
        ImageCoordinate sameCoordinate = new ImageCoordinate(1, 2);
        
        assertEquals(coordinate, sameCoordinate);
    }

    @Test
    public void testNotEqualsDifferentClass() {
        Object object = new Object();

        assertNotEquals(coordinate, object);
    }

    @Test
    public void testNotEqualsDifferentRow() {
        ImageCoordinate diffCoordinate = new ImageCoordinate(2, 2);

        assertNotEquals(coordinate, diffCoordinate);
    }

    @Test
    public void testNotEqualsDifferentCol() {
        ImageCoordinate diffCoordinate = new ImageCoordinate(1, 1);

        assertNotEquals(coordinate, diffCoordinate);
    }

    @Test
    public void testNotEqualsDifferentRowAndCol() {
        ImageCoordinate diffCoordinate = ImageCoordinate.ORIGIN;

        assertNotEquals(coordinate, diffCoordinate);
    }
}
