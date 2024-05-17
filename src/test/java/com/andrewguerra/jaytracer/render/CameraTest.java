package com.andrewguerra.jaytracer.render;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class CameraTest {
    @Test
    public void testConstructor() {
        Camera camera = new Camera(new Ray(Vector3.ZERO, Vector3.Z.negate()) , Vector3.Y, 120, 90, 1);

        assertEquals(Camera.CANONICAL.ray, camera.ray);
    }
}
