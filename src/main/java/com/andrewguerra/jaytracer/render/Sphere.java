package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

/**
 * A class for a sphere as a scene entity, defined by its radius.
 */
public class Sphere extends SceneEntity {
    private double radius;

    /**
     * Constructor with a position, material, and the sphere's radius.
     * 
     * @param position the position of the sphere
     * @param material the material of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Vector3 position, Material material, double radius) {
        super(position,material);

        this.radius = radius;
    }

    /**
     * Constructor for a standard sphere at the origin and standard material with a given radius
     * 
     * @param radius the radius of the sphere
     */
    public Sphere(double radius) {
        super();

        this.radius = radius;
    }

    @Override
    public double intersectionDistance(Ray ray) {
        Vector3 sphereDirection = ray.origin.subtract(this.position);

        double A = ray.direction.dot(ray.direction);
        double B = 2 * ray.direction.dot(sphereDirection);
        double C = sphereDirection.dot(sphereDirection) - (this.radius * this.radius);

        double discriminate = (B * B) - (4 * A * C); 

        if(discriminate <= 0) {
            return Double.POSITIVE_INFINITY;
        }

        double discriminateSqrt = Math.sqrt(discriminate);

        if(Math.min((-B + discriminateSqrt) / (2 * A), (-B - discriminateSqrt) / (2 * A)) < 0) {
            return -1;
        }

        return Math.min((-B + discriminateSqrt) / (2 * A), (-B - discriminateSqrt) / (2 * A));
    }

    @Override
    public Ray getSurfaceNormalRay(Vector3 position) {
        Vector3 normalDirection = position.subtract(this.position).scale(1.0 / this.radius);
        
        return new Ray(position, normalDirection);
    } 
}
