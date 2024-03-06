package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class IntersectionInformation {
    public final SceneEntity entity;
    public final Vector3 intersectionPoint;
    public final Ray incidentRay;
    public final double intersectionDistance;
    public final Camera camera;

    public IntersectionInformation(SceneEntity entity, Ray incidentRay, double intersectionDistance, Camera camera) {
        this.entity = entity;
        this.intersectionPoint = incidentRay.cast(intersectionDistance);
        this.incidentRay = incidentRay;
        this.intersectionDistance = intersectionDistance;
        this.camera = camera;
    }
}
