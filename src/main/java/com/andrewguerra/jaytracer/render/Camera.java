package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

/**
 * Class to represent a camera positioned in a scene. This camera has a view ray as well as an up direction, along
 * with a field of view.
 */
public class Camera {
    /**
     * The view ray.
     */
    public final Ray ray;

    /**
     * The up direction.
     */
    public final Vector3 up;

    /**
     * The horizontal field of view in radians.
     */
    public final double hfov;
    
    /**
     * The vertical field of view in radians.
     */
    public final double vfov;

    /**
     * The canonical camera, where the position is at the origin with a view direction down the negative Z-axis.
     */
    public static final Camera CANONICAL = new Camera(new Ray(Vector3.ZERO, Vector3.Z.negate()) , Vector3.Y, 120, 90);

    /**
     * Constructor for a camera witha view ray, up direction and horizontal and vertical fields of view.
     * 
     * @param ray The view ray direction
     * @param up The up direction
     * @param hfov The horizontal field of view in radians
     * @param vfov The vertical field of view in radians
     */
    public Camera(Ray ray, Vector3 up, double hfov, double vfov) {
        this.ray = ray;
        this.up = up;
        this.hfov = hfov;
        this.vfov = vfov;
    }
}
