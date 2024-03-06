package com.andrewguerra.jaytracer.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.andrewguerra.jaytracer.render.Color;

public class ImageTest {
    public static Image squareImage, squareImageWhite, rectImage;

    @Before 
    public void intitializeImages() {
        squareImage = new Image(9, 9);
        squareImageWhite = new Image(9, 9, Color.WHITE);
        rectImage = new Image(9, 18);
    }

    @Test
    public void testConstructorPixels() {

    }

    @Test
    public void testConstructorDimensionsColor() {
        
    }

    @Test
    public void testConstructorDimensions() {
        
    }

    @Test
    public void testGetImageArea() {
        ImageArea expectedImageArea = new ImageArea(ImageCoordinate.ORIGIN, new ImageCoordinate(9, 9));
        ImageArea actualImageArea = squareImage.getImageArea();
        
        assertEquals(expectedImageArea, actualImageArea);
    }

    @Test
    public void testGetPixel() {
        Color color = squareImage.getPixel(ImageCoordinate.ORIGIN);

        assertEquals(Color.BLACK, color);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPixelIllegalArgument() {
        ImageCoordinate coordinate = new ImageCoordinate(9, 0);
        squareImage.getPixel(coordinate);
    }

    @Test
    public void testAdd() {
        Image image = squareImage.add(squareImage);


    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIllegalArgument() {
        Image image = squareImage.add(rectImage);
    }

    @Test
    public void testConstainsImageDoesContain() {
        boolean containsImage = rectImage.containsImage(squareImage, squareImage.getImageArea());

        assertTrue(containsImage);
    }

    @Test
    public void testContainsImageDoesNotContain() {
        boolean containsImage = rectImage.containsImage(squareImageWhite, squareImageWhite.getImageArea());

        assertFalse(containsImage);
    }
}
