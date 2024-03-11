package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

/**
 * A class to represent an entity within a scene. This entity is an object to be rendered with a position, and defined 
 * intersection and surface normal functions.
 */
public abstract class SceneEntity {
    /**
     * The position of the entity. The position is not necessarily defined as the center of the scene
     * entity. The semantics of this field is dependent on the implementation of concrete subclasses.
     */
    protected Vector3 position;

    /**
     * The material of the entity.
     */
    protected Material material;

    /**
     * Constructor with a position and a material.
     * 
     * @param position The position of the entity
     * @param material The material of the entity
     */
    public SceneEntity(Vector3 position, Material material) {
        this.position = position;
        this.material = material;
    }

    /**
     * Constructor with a position at the origin with the default material.
     */
    public SceneEntity() {
        this.position = Vector3.ZERO;
        this.material = Material.DEFAULT;
    }

    /**
     * Returns the position of the entity.
     * 
     * @return the position of the entity
     */
    public Vector3 getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the entity
     * 
     * @param position The position of the entity
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Returns the positive distance cast along the ray where an intersection occurs with the entity. If there is
     * no intersection with the ray, positive infinity is returned.
     * 
     * @param ray The ray to calculate the distance for intersection
     * @return the distance along the ray where an intersection occurs with the entity
     */
    public abstract double intersectionDistance(Ray ray);

    /**
     * Returns the surface normal of this entity at the given position. It is assumed
     * that the position is located on the surface of the entity.
     * 
     * @param position The position where to calculate the surface normal
     * @return the unit surface normal at the given position
     */
    public abstract Ray getSurfaceNormalRay(Vector3 position);
}
