package com.andrewguerra.jaytracer.render;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class SphereTest {
    private static Sphere unitSphere;

    @Before
    public void initializeUnitSphere() {
        unitSphere = new Sphere(1);
    }

    @Test
    public void testGetSurfaceNormalRayUnitX() {
        Ray expectedRay = new Ray(Vector3.X, Vector3.X);
        Ray surfaceNormalRay = unitSphere.getSurfaceNormalRay(Vector3.X);

        assertEquals(expectedRay, surfaceNormalRay);
    }

    @Test
    public void testGetSurfaceNormalRayUnitY() {
        Ray expectedRay = new Ray(Vector3.Y, Vector3.Y);
        Ray surfaceNormalRay = unitSphere.getSurfaceNormalRay(Vector3.Y);

        assertEquals(expectedRay, surfaceNormalRay);
    }

    @Test
    public void testGetSurfaceNormalRayUnitZ() {
        Ray expectedRay = new Ray(Vector3.Z, Vector3.Z);
        Ray surfaceNormalRay = unitSphere.getSurfaceNormalRay(Vector3.Z);

        assertEquals(expectedRay, surfaceNormalRay);
    }
}
