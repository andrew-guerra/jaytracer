package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public abstract class SceneEntity {
    protected Vector3 position;
    protected Material material;

    /**
     * 
     * @param position
     * @param material
     */
    public SceneEntity(Vector3 position, Material material) {
        this.position = position;
        this.material = material;
    }

    /**
     * 
     */
    public SceneEntity() {
        this.position = Vector3.ZERO;
        this.material = Material.DEFAULT;
    }

    /**
     * 
     * @return
     */
    public Vector3 getPosition() {
        return this.position;
    }

    /**
     * 
     * @param position
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * 
     * @param ray
     * @return
     */
    public abstract double intersectionDistance(Ray ray);

    /**
     * 
     * @param position
     * @return
     */
    public abstract Ray getSurfaceNormalRay(Vector3 position);
}
