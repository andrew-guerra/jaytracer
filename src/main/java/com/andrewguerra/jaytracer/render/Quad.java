package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class Quad extends SceneEntity {
    private Vector3 u, v, normal, w;
    private double D;

    public Quad(Vector3 position, Material material, Vector3 u, Vector3 v) {
        super(position, material);

        this.u = u;
        this.v = v;
        this.normal = u.cross(v);
        this.D = this.normal.dot(this.position);
        this.w = this.normal.scale(1 / this.normal.dot(this.normal));
    }

    @Override
    public double intersectionDistance(Ray ray) {
        double denominator = this.normal.dot(ray.direction);

        if(Math.abs(denominator) < 1e-8) {
            
            return Double.POSITIVE_INFINITY;
        }

        double distance = (this.D - this.normal.dot(ray.origin)) / denominator;

        if(distance < 0) {
            return Double.POSITIVE_INFINITY;
        }

        Vector3 intersectionPoint = ray.cast(distance);
        Vector3 intersectionOriginVector = intersectionPoint.subtract(this.position);
        double alpha = this.w.dot(intersectionOriginVector.cross(this.v));
        double beta = this.w.dot(this.u.cross(intersectionOriginVector));

        if((alpha < 0 || alpha > 1) || (beta < 0 || beta > 1) || ray.direction.dot(this.normal) >= 0) {
            return Double.POSITIVE_INFINITY;
        }
        
        return distance;
    }

    @Override
    public Ray getSurfaceNormalRay(Vector3 position) {
        return new Ray(position, this.normal);
    }

    @Override
    public double getUCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        Vector3 intersectionOriginVector = intersectionPoint.subtract(this.position);
        
        return this.w.dot(intersectionOriginVector.cross(this.v));
    }

    @Override
    public double getVCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        Vector3 intersectionOriginVector = intersectionPoint.subtract(this.position);
        
        return this.w.dot(this.u.cross(intersectionOriginVector));
    }
    
    public boolean onSurface(Vector3 point) {
        Vector3 intersectionOriginVector = point.subtract(this.position);
        double alpha = this.w.dot(intersectionOriginVector.cross(this.v));
        double beta = this.w.dot(this.u.cross(intersectionOriginVector));

        if((alpha < 0 || alpha > 1) || (beta < 0 || beta > 1)) {
            return false;
        }
        
        return true;
    }
}
