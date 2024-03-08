package com.andrewguerra.jaytracer.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class RayTest {
    private static Ray standardRay;
    private static Vector3 origin;
    private static Vector3 direction;

    @Before
    public void initializeStandardRay() {
        origin = new Vector3(1, 2, 3);
        direction = new Vector3(4, 5, 6);
        standardRay = new Ray(origin, direction);
    }

    @Test
    public void testConstructor() {
        assertEquals(origin, standardRay.origin);
        assertEquals(direction, standardRay.direction);
    }

    @Test
    public void testCastZeroFactor() {
        Vector3 vector = standardRay.cast(0);

        assertEquals(standardRay.origin, vector);
    }

    @Test
    public void testCastOneFactor() {
        Vector3 expectedVector = standardRay.origin.add(standardRay.direction);
        Vector3 vector = standardRay.cast(1);

        assertEquals(expectedVector, vector);
    }

    @Test
    public void testReflectOrthoginalNormal() {
        Ray incidentRay = new Ray(Vector3.Y, Vector3.Y.negate());
        Ray normalRay = new Ray(Vector3.ZERO, Vector3.Y);

        Ray reflectedRay = incidentRay.reflect(normalRay);

        assertEquals(Vector3.ZERO, reflectedRay.origin);
        assertEquals(Vector3.Y, reflectedRay.direction);
    }

    @Test
    public void testReflectNonOrthoginalNormal() {
        Ray incidentRay = new Ray(Vector3.Y, Vector3.Y.negate());
        Ray normalRay = new Ray(Vector3.ZERO, new Vector3(1, 1, 0).normalize());

        Ray reflectedRay = incidentRay.reflect(normalRay);

        assertEquals(Vector3.ZERO, reflectedRay.origin);
        assertEquals(Vector3.X, reflectedRay.direction);
    }

    @Test 
    public void testNudge() {
        for(int i = 0; i < 100; i++) {
            Ray ray = standardRay.nudge();

            assertEquals(standardRay.origin, ray.origin);
            assertTrue(standardRay.direction.distance(ray.direction) <= Vector3.MAX_NUDGE_DISTANCE);
        }
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals(Ray.X, Ray.X);
    }

    @Test
    public void testEqualsSameValues() {
        Ray ray = new Ray(new Vector3(0, 0, 0), new Vector3(1, 0, 0));

        assertEquals(Ray.X, ray);
    }

    @Test
    public void testEqualsNotSameClass() {
        Object object = new Object();

        assertNotEquals(Ray.X, object);
    }

    @Test
    public void testEqualsNotEquals() {
        assertNotEquals(Ray.X, Ray.Y);
    }

    @Test
    public void testEqualsNotEqualsSameOrigin() {
        assertNotEquals(Ray.X, Ray.Z);
    }

    @Test
    public void testEqualsNotEqualsSameDirection() {
        Ray ray = new Ray(new Vector3(1, 1, 1), Ray.X.direction);

        assertNotEquals(Ray.X, ray);
    }

    @Test
    public void testToString() {
        String expected = "(1.00, 2.00, 3.00):(4.00, 5.00, 6.00)";
        String actual = standardRay.toString();

        assertEquals(expected, actual);
    }
}
