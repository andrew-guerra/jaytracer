package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

/**
 * Class to represent casting a ray into a scene. This object holds wether the ray intersects a scene entity.
 * If there is an intersection, the scene entity and its material is stored, along with the intersection point, and 
 * the surface normal at that point.
 */
public class IntersectionInformation {
    /**
     * The intersection point of the ray with a scene entity.
     */
    public final Vector3 intersectionPoint;

    /**
     * The surface normal at the intersection point.
     */
    public final Vector3 normal;

    /**
     * The scene entity that was intersected.
     */
    public final SceneEntity entity;

    /**
     * The material of the intersected scene entity.
     */
    public final Material material;

    /**
     * If the ray intersected a scene entity.
     */
    public final boolean collision;

    /**
     * Constructor with a entity, incident ray, intersection distance and
     * if there was a collision.
     * 
     * @param entity The entity that was intersected
     * @param incidentRay The ray cast into the scene
     * @param intersectionDistance The distance along the ray of the intersection
     * @param collision If an intersection occured
     */
    public IntersectionInformation(SceneEntity entity, Ray incidentRay, double intersectionDistance, boolean collision) {
        this.entity = entity;
        this.intersectionPoint = incidentRay.cast(intersectionDistance);
        this.normal = entity != null ? entity.getSurfaceNormalRay(intersectionPoint).origin : null;
        this.material = entity != null ? entity.material : null;
        this.collision = collision;
    }
}
