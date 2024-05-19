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
        Vector3 sphereDirection = this.position.subtract(ray.origin);

        double A = ray.direction.dot(ray.direction);
        double B = -2 * ray.direction.dot(sphereDirection);
        double C = sphereDirection.dot(sphereDirection) - (this.radius * this.radius);

        double discriminate = (B * B) - (4 * A * C); 

        if(discriminate < 0) {
            return Double.POSITIVE_INFINITY;
        }

        double discriminateSqrt = Math.sqrt(discriminate);
        double positiveRootDistance = (-B + discriminateSqrt) / (2 * A);
        double negativeRootDistance = (-B - discriminateSqrt) / (2 * A);

        if(Math.max(positiveRootDistance, negativeRootDistance) < 0) {
            return Double.POSITIVE_INFINITY;
        }

        return Math.min(positiveRootDistance, negativeRootDistance);
    }

    @Override
    public Ray getSurfaceNormalRay(Vector3 position) {
        Vector3 normalDirection = position.subtract(this.position).scale(1.0 / this.radius);
        
        return new Ray(position, normalDirection);
    }

    @Override
    public double getUCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        double phi = Math.atan2(-normal.z, normal.x) + Math.PI;

        return phi / (2 * Math.PI);
    }

    @Override
    public double getVCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        double theta = Math.acos(-normal.y);

        return theta / Math.PI;
    } 
}
