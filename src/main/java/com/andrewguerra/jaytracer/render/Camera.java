package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class Camera {
    public final Ray ray;
    public final Vector3 up;
    public final double hfov, vfov;

    public static final Camera CANONICAL = new Camera(new Ray(Vector3.ZERO, Vector3.Z.negate()) , Vector3.Y, 120, 90);

    public Camera(Ray ray, Vector3 up, double hfov, double vfov) {
        this.ray = ray;
        this.up = up;
        this.hfov = hfov;
        this.vfov = vfov;
    }
}
