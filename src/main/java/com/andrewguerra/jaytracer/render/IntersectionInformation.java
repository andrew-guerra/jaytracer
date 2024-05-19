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
     * The texture coordinates at the intersection point.
     */
    public final double u, v;

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
     * If the ray intersects the inside face of a scene entity.
     */
    public final boolean internalCollision;

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
        if(collision && entity != null) {
            this.entity = entity;
            this.intersectionPoint = incidentRay.cast(intersectionDistance);
            this.material = entity.material;
            this.collision = collision;

            Vector3 outwardNormal = entity.getSurfaceNormalRay(intersectionPoint).direction;
            this.u = entity.getUCoordinate(intersectionPoint, outwardNormal);
            this.v = entity.getVCoordinate(intersectionPoint, outwardNormal);
            this.internalCollision = incidentRay.direction.dot(outwardNormal) >= 0;

            this.normal = this.internalCollision ? outwardNormal.negate() : outwardNormal;
        } else {
            this.entity = entity;
            this.intersectionPoint = null;
            this.u = 0;
            this.v = 0;
            this.material = null;
            this.collision = false;
            this.internalCollision = false;
            this.normal = null;
        }
    }  
}
