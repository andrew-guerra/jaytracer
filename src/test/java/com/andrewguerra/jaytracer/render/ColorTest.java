package com.andrewguerra.jaytracer.render;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ColorTest {
    @Test
    public void testConstructor() {
        Color color = new Color(0, 0, 0);

        assertEquals(Color.BLACK, color);
    }

    @Test
    public void testAdd() {
        Color color = Color.RED.add(Color.GREEN).add(Color.BLUE);

        assertEquals(Color.WHITE, color);
    }

    @Test
    public void testMultiplyOne() {
        Color color = Color.WHITE.multiply(1);

        assertEquals(Color.WHITE, color);
    }
    
    @Test
    public void testMultiplyZero() {
        Color color = Color.WHITE.multiply(0);

        assertEquals(Color.BLACK, color);
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals(Color.RED, Color.RED);
    }

    @Test
    public void testEqualsSameValues() {
        Color color = new Color(1, 0, 0);

        assertEquals(Color.RED, color);
    }

    @Test
    public void testEqualsNotSameClass() {
        Object object = new Object();

        assertNotEquals(Color.RED, object);
    }

    @Test
    public void testEqualsNotEquals() {
        Color color = new Color(0.1, 0.1, 0.1);

        assertNotEquals(Color.RED, color);
    }

    @Test
    public void testEqualsNotEqualsRed() {
        Color color = Color.RED.multiply(0.5);

        assertNotEquals(Color.RED, color);
    }

    @Test
    public void testEqualsNotEqualsGreen() {
        Color color = new Color(1, 1, 0);

        assertNotEquals(Color.RED, color);
    }

    @Test
    public void testEqualsNotEqualsBlue() {
        Color color = new Color(1, 0, 1);

        assertNotEquals(Color.RED, color);
    }

    @Test
    public void testEqualsNotEqualsRedGreen() {
        Color color = new Color(0.1, 0.1, 0);

        assertNotEquals(Color.RED, color);
    }

    @Test
    public void testEqualsNotEqualsRedBlue() {
        Color color = new Color(0.1, 0, 0.1);

        assertNotEquals(Color.RED, color);
    }

    @Test
    public void testEqualsNotEqualsGreenBlue() {
        Color color = new Color(1, 0.1, 0.1);

        assertNotEquals(Color.RED, color);
    }
}
