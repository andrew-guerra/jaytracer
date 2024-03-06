package com.andrewguerra.jaytracer.math;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class Vector3Test {
    private static Vector3 standardVector;

    @Before
    public void initializeStandardVector() {
        standardVector = new Vector3(1, 2, 3);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, standardVector.x, 0);
        assertEquals(2, standardVector.y, 0);
        assertEquals(3, standardVector.z, 0);
    }

    @Test
    public void testAsArray() {
        double[] expectedArray = new double[]{1, 2, 3};
        double[] array = standardVector.asArray();

        assertArrayEquals(expectedArray, array, 0);
    }

    @Test
    public void testNorm() {
        double expectedNorm = Math.sqrt(1 + 4 + 9);
        double norm = standardVector.norm();

        assertEquals(expectedNorm, norm, 0);
    }

    @Test
    public void testDistanceSameVector() {
        double distance = standardVector.distance(standardVector);

        assertEquals(0, distance, 0);
    }

    @Test
    public void testDistanceDiffVectors() {
        double expectedDistance = Vector3.ZERO.subtract(standardVector).norm();
        double distance = Vector3.ZERO.distance(standardVector);

        //assertEquals(expectedDistance, distance, 0);
    }

    @Test
    public void testDotSameVectors() {
        double expectedProduct = 1*1 + 2*2 + 3*3;
        double product = standardVector.dot(standardVector);

        assertEquals(expectedProduct, product, 0);
    }

    @Test
    public void testDotDiffVectors() {
        double expectedProduct = 1*1 + 2*2 + 3*3;
        double product = standardVector.dot(standardVector);

        assertEquals(expectedProduct, product, 0);
    }

    @Test
    public void testDotZeroVector() {
        double expectedProduct = 0;
        double product = standardVector.dot(Vector3.ZERO);

        assertEquals(expectedProduct, product, 0);
    }

    @Test
    public void testCross() {
        
    }

    @Test
    public void testAddSameVector() {
        Vector3 expectedVector = new Vector3(2, 4, 6);
        Vector3 vector = standardVector.add(standardVector);

        assertEquals(expectedVector, vector);
    }

    @Test
    public void testAddZeroVector() {
        Vector3 vector = standardVector.add(Vector3.ZERO);

        assertEquals(standardVector, vector);
    }

    @Test
    public void testNormal() {
        double norm = standardVector.norm();
        Vector3 expectedNormal = new Vector3(1 / norm, 2 / norm, 3 / norm);
        Vector3 normal = standardVector.normal();

        assertEquals(expectedNormal, normal);
    }

    @Test
    public void testNegate() {
        Vector3 expectedVector = new Vector3(-1, -2, -3);
        Vector3 vector = standardVector.negate();

        assertEquals(expectedVector, vector);
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals(standardVector, standardVector);
    }

    @Test
    public void testEqualsSameValues() {
        Vector3 vector = new Vector3(1, 2, 3);

        assertEquals(standardVector, vector);
    }

    @Test
    public void testEqualsNotSameClass() {
        Object object = new Object();

        assertNotEquals(standardVector, object);
    }

    @Test
    public void testEqualsNotEquals() {
        assertNotEquals(standardVector, Vector3.ZERO);
    }

    @Test
    public void testEqualsNotEqualsSameX() {
        assertNotEquals(standardVector, Vector3.X);
    }

    @Test
    public void testEqualsNotEqualsSameY() {
        assertNotEquals(standardVector, Vector3.Y.scale(2));
    }

    @Test
    public void testEqualsNotEqualsSameZ() {
        assertNotEquals(standardVector, Vector3.Z.scale(3));
    }

    @Test
    public void testEqualsNotEqualsSameXY() {
        assertNotEquals(standardVector, Vector3.X.add(Vector3.Y.scale(2)));
    }

    @Test
    public void testEqualsNotEqualsSameXZ() {
        assertNotEquals(standardVector, Vector3.X.add(Vector3.Z.scale(3)));
    }

    @Test
    public void testEqualsNotEqualsSameYZ() {
        assertNotEquals(standardVector, Vector3.Y.scale(2).add(Vector3.Z.scale(3)));
    }
}
