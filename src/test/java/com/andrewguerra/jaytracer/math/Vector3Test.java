package com.andrewguerra.jaytracer.math;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.andrewguerra.jaytracer.render.Color;

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
    public void testNormZero() {
        double expectedNorm = 0;
        double norm = Vector3.ZERO.norm();

        assertEquals(expectedNorm, norm, 0);
    }

    @Test
    public void testDistanceSameVector() {
        double distance = standardVector.distance(standardVector);

        assertEquals(0, distance, 0);
    }

    @Test
    public void testDistanceDiffVectors() {
        double expectedDistance = standardVector.norm();
        double actualDistance = Vector3.ZERO.distance(standardVector);

        assertEquals(expectedDistance, actualDistance, 0);
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
    public void testCrossSameVectors() {
        Vector3 expectedCross = Vector3.ZERO;
        Vector3 actualCross = standardVector.cross(standardVector);

        assertEquals(expectedCross, actualCross);
    }

    @Test
    public void testCrossDiffVectors() {
        Vector3 expectedCross = new Vector3(1, 7, -5);
        Vector3 actualCross = standardVector.cross(new Vector3(3, 1, 2));

        assertEquals(expectedCross, actualCross);
    }

    @Test
    public void testCrossZero() {
        Vector3 expectedCross = Vector3.ZERO;
        Vector3 actualCross = standardVector.cross(standardVector);

        assertEquals(expectedCross, actualCross);
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
    public void testSubtractSameVector() {
        Vector3 expectedSub = Vector3.ZERO;
        Vector3 actualSub = standardVector.subtract(standardVector);

        assertEquals(expectedSub, actualSub);
    }

    @Test
    public void testSubtractDiffVector() {
        Vector3 expectedSub = Vector3.ZERO;
        Vector3 actualSub = standardVector.subtract(standardVector);

        assertEquals(expectedSub, actualSub);
    }

    @Test
    public void testSubtractZeroVector() {
        Vector3 expectedSub = standardVector;
        Vector3 actualSub = standardVector.subtract(Vector3.ZERO);

        assertEquals(expectedSub, actualSub);
    }

    @Test
    public void testAverageSameVector() {
        Vector3 expectedAverage = standardVector;
        Vector3 actualAverage = standardVector.average(standardVector);

        assertEquals(expectedAverage, actualAverage);
    }

    @Test
    public void testAverageDiffVector() {
        Vector3 expectedAverage = standardVector;
        Vector3 actualAverage = standardVector.average(standardVector);

        assertEquals(expectedAverage, actualAverage);
    }

    @Test
    public void testAverageSameZero() {
        Vector3 expectedAverage = standardVector.scale(0.5);
        Vector3 actualAverage = standardVector.average(Vector3.ZERO);

        assertEquals(expectedAverage, actualAverage);
    }

    @Test
    public void testScalePositive() {
        Vector3 expectedScale = new Vector3(2, 4, 6);
        Vector3 actualScale = standardVector.scale(2);

        assertEquals(expectedScale, actualScale);
    }

    @Test
    public void testScaleNegative() {
        Vector3 expectedScale = new Vector3(-2, -4, -6);
        Vector3 actualScale = standardVector.scale(-2);

        assertEquals(expectedScale, actualScale);
    }

    @Test
    public void testScaleOne() {
        Vector3 expectedScale = standardVector;
        Vector3 actualScale = standardVector.scale(1);

        assertEquals(expectedScale, actualScale);
    }

    @Test
    public void testScaleNegativeOne() {
        Vector3 expectedScale = standardVector.negate();
        Vector3 actualScale = standardVector.scale(-1);

        assertEquals(expectedScale, actualScale);
    }

    @Test
    public void testScaleZero() {
        Vector3 expectedScale = Vector3.ZERO;
        Vector3 actualScale = standardVector.scale(0);

        assertEquals(expectedScale, actualScale);
    }

    @Test
    public void testSqrtPositive() {
        Vector3 expectedScale = new Vector3(Math.sqrt(1), Math.sqrt(2), Math.sqrt(3));
        Vector3 actualScale = standardVector.sqrt();

        assertEquals(expectedScale, actualScale);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSqrtNegative() {
        standardVector.negate().sqrt();
    }

    @Test
    public void testSqrtZero() {
        Vector3 expectedScale = Vector3.ZERO;
        Vector3 actualScale = Vector3.ZERO.sqrt();

        assertEquals(expectedScale, actualScale);
    }

    @Test
    public void testNormalize() {
        double norm = standardVector.norm();
        Vector3 expectedNormal = new Vector3(1 / norm, 2 / norm, 3 / norm);
        Vector3 normal = standardVector.normalize();

        assertEquals(expectedNormal, normal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNormalizeZero() {
        Vector3 expectedNormal = Vector3.ZERO;
        Vector3 normal = Vector3.ZERO.normalize();

        assertEquals(expectedNormal, normal);
    }

    @Test
    public void testNegate() {
        Vector3 expectedNegate = new Vector3(-1, -2, -3);
        Vector3 actualNegate = standardVector.negate();

        assertEquals(expectedNegate, actualNegate);
    }

    @Test
    public void testNegateZero() {
        Vector3 expectedNegate = Vector3.ZERO;
        Vector3 actualNegate = Vector3.ZERO.negate();

        assertEquals(expectedNegate, actualNegate);
    }

    @Test
    public void testNudge() {
        for(int i = 0; i < 100; i++) {
            Vector3 vector = Vector3.ZERO.nudge();

            assertTrue(vector.x >= -Vector3.NUDGE_FACTOR && vector.x <= Vector3.NUDGE_FACTOR);
            assertTrue(vector.y >= -Vector3.NUDGE_FACTOR && vector.y <= Vector3.NUDGE_FACTOR);
            assertTrue(vector.z >= -Vector3.NUDGE_FACTOR && vector.z <= Vector3.NUDGE_FACTOR);
        }
    }

    @Test
    public void testToColor() {
        Color expectedColor = new Color(1.0, 2.0, 3.0);
        Color actuaColor = standardVector.toColor();

        assertEquals(expectedColor, actuaColor);
    }

    @Test
    public void testToColorX() {
        Color expectedColor = Color.RED;
        Color actuaColor = Vector3.X.toColor();

        assertEquals(expectedColor, actuaColor);
    }

    @Test
    public void testToColorY() {
        Color expectedColor = Color.GREEN;
        Color actuaColor = Vector3.Y.toColor();

        assertEquals(expectedColor, actuaColor);
    }

    @Test
    public void testToColorZ() {
        Color expectedColor = Color.BLUE;
        Color actuaColor = Vector3.Z.toColor();

        assertEquals(expectedColor, actuaColor);
    }
    
    @Test
    public void testToColorZero() {
        Color expectedColor = Color.BLACK;
        Color actuaColor = Vector3.ZERO.toColor();

        assertEquals(expectedColor, actuaColor);
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

    @Test
    public void testToString() {
        String expected = "(1.00, 2.00, 3.00)";
        String actual = standardVector.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testToStringZero() {
        String expected = "(0.00, 0.00, 0.00)";
        String actual = Vector3.ZERO.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testRandomVector() {
        for(int i = 0; i < 100; i++) {
            Vector3 vector = Vector3.randomVector();

            assertTrue(vector.x >= 0 && vector.x <= 1);
            assertTrue(vector.y >= 0 && vector.y <= 1);
            assertTrue(vector.z >= 0 && vector.z <= 1);
        }
    }

    @Test
    public void testRandomVectorBounded() {
        for(int i = 0; i < 100; i++) {
            Vector3 vector = Vector3.randomVector(-1, 1);

            assertTrue(vector.x >= -1 && vector.x <= 1);
            assertTrue(vector.y >= -1 && vector.y <= 1);
            assertTrue(vector.z >= -1 && vector.z <= 1);
        }
    }

    @Test
    public void testRandomUnitVector() {
        for(int i = 0; i < 100; i++) {
            Vector3 vector = Vector3.randomUnitVector();

            assertEquals(1, vector.norm(), 0.01);
        }
    }
}
