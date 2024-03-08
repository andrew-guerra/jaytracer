package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class Light {
    public final SceneEntity entity;

    public Light(SceneEntity entity) {
        this.entity = entity;
    }

    public Ray reverseLightRay(Vector3 position) {
        return new Ray(position, this.lightDirection(position).normalize());
    }

    public double distance(Vector3 position) {
        return this.lightDirection(position).norm();
    }

    private Vector3 lightDirection(Vector3 position) {
        return this.entity.position.subtract(position);
    }
}
