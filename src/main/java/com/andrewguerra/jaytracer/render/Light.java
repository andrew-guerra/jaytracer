package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class Light {
    public final SceneEntity entity;

    /**
     * 
     * @param entity
     */
    public Light(SceneEntity entity) {
        this.entity = entity;
    }

    /**
     * 
     * @param position
     * @return
     */
    public Ray reverseLightRay(Vector3 position) {
        return new Ray(position, this.lightDirection(position).normalize());
    }

    /**
     * 
     * @param position
     * @return
     */
    public double distance(Vector3 position) {
        return this.lightDirection(position).norm();
    }

    /**
     * 
     * @param position
     * @return
     */
    private Vector3 lightDirection(Vector3 position) {
        return this.entity.position.subtract(position);
    }
}
