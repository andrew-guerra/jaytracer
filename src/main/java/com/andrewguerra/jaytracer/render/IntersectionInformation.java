package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class IntersectionInformation {
    public final Vector3 intersectionPoint;
    public final Vector3 normal;
    public final SceneEntity entity;
    public final Material material;
    public final boolean collision;

    /**
     * 
     * @param entity
     * @param incidentRay
     * @param intersectionDistance
     * @param collision
     */
    public IntersectionInformation(SceneEntity entity, Ray incidentRay, double intersectionDistance, boolean collision) {
        this.entity = entity;
        this.intersectionPoint = incidentRay.cast(intersectionDistance);
        this.normal = entity != null ? entity.getSurfaceNormalRay(intersectionPoint).origin : null;
        this.material = entity != null ? entity.material : null;
        this.collision = collision;
    }
}
