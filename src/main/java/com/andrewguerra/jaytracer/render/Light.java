package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

/**
 * A class to represent a light entity in a scene.
 */
public class Light {
    public final SceneEntity entity;

    /**
     * Constructor with a scene entity to encapsulate
     * @param entity the entity to encapulate
     */
    public Light(SceneEntity entity) {
        this.entity = entity;
    }

    /**
     * Returns the reversed ray of the light direction at a given position
     * 
     * @param position the position on the entity to calculate the reversed light ray
     * @return the reversed ray of light at the given position
     */
    public Ray reverseLightRay(Vector3 position) {
        return new Ray(position, this.lightDirection(position).normalize());
    }

    /**
     * Returns the distance between the position and the light direction.
     * 
     * @param position the position to calculate the distance to
     * @return the distance between the position and the light
     */
    public double distance(Vector3 position) {
        return this.lightDirection(position).norm();
    }

    /**
     * Returns the light light direction in the given position.
     * 
     * @param position the position to calculate the light direction to
     * @return the light direction at the given point
     */
    private Vector3 lightDirection(Vector3 position) {
        return this.entity.position.subtract(position);
    }
}
