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

        Vector3 intersectionPointPositiveRoot = ray.cast(positiveRootDistance);
        Vector3 intersectionPointNegativeRoot = ray.cast(negativeRootDistance);

        double cylinderHeightPositionPositiveRoot = getLevelHeight(intersectionPointPositiveRoot);
        double cylinderHeightPositionNegativeRoot = getLevelHeight(intersectionPointNegativeRoot);
        
        boolean intersectionPointPositiveRootInBounds = cylinderHeightPositionPositiveRoot > this.height || cylinderHeightPositionPositiveRoot < 0;
        boolean intersectionPointNegativeRootInBounds = cylinderHeightPositionNegativeRoot > this.height || cylinderHeightPositionNegativeRoot < 0;

        if(intersectionPointPositiveRootInBounds && intersectionPointNegativeRootInBounds) {
            return Double.POSITIVE_INFINITY;
        }

        if(!intersectionPointNegativeRootInBounds) {
            return positiveRootDistance;
        }

        return negativeRootDistance;
    }

    @Override
    public Ray getSurfaceNormalRay(Vector3 position) {
        double cylinderHeightPosition = getLevelHeight(position);
        Vector3 levelPosition = this.ray.cast(cylinderHeightPosition);

        return new Ray(position, position.subtract(levelPosition).normalize());
    }

    private double getLevelHeight(Vector3 position) {
        return position.subtract(this.position).dot(this.axis);
    }

    @Override
    public double getUCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        return 0;
    }

    @Override
    public double getVCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        return getLevelHeight(intersectionPoint) / this.height;
    }
    
}
