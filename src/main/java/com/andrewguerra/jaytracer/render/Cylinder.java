package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class Cylinder extends SceneEntity {
    private Ray ray;
    private Vector3 axis;
    private double radius;
    private double height;
    
    public Cylinder(Vector3 position, Material material, Vector3 axis, double radius, double height) {
        super(position, material);

        this.ray = new Ray(position, axis);
        this.axis = axis;
        this.radius = radius;
        this.height = height;
    }

    public Cylinder(Vector3 axis, double radius, double height) {
        super();

        this.ray = new Ray(this.position, axis);
        this.axis = axis;
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double intersectionDistance(Ray ray) {
        Vector3 X = ray.origin.subtract(this.position);
        double A = ray.direction.dot(ray.direction) - Math.pow(ray.direction.dot(this.axis), 2);
        double C = X.dot(X) - Math.pow(X.dot(this.axis), 2) - this.radius * this.radius;
        double B = 2 * (ray.direction.dot(X) - ray.direction.dot(this.axis) * (X.dot(this.axis)));
        
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

        double distance = Math.min(positiveRootDistance, negativeRootDistance);
        Vector3 intersectionPoint = ray.cast(distance);

        double cylinderHeightPosition = intersectionPoint.subtract(this.position).dot(this.axis);
        
        if(cylinderHeightPosition > this.height) {
            return Double.POSITIVE_INFINITY;
        }

        return distance;
    }

    @Override
    public Ray getSurfaceNormalRay(Vector3 position) {
        double cylinderHeightPosition = position.subtract(this.position).dot(this.axis);
        Vector3 levelPosition = this.ray.cast(cylinderHeightPosition);

        return new Ray(position, position.subtract(levelPosition).normalize());
    }
    
}
