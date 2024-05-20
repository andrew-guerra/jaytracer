package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

/**
 * A class to represent a reflective material.
 */
public class ReflectiveMaterial extends Material {
    /**
     * The albedo for the surface material
     */
    public final ColorUnbounded albedo;

    /**
     * The reflectivity of the material on a scale of 0 to 1. A value of 1 represents 
     * a pure mirror surface.
     */
    public final double reflectivity;

    public static final ReflectiveMaterial MIRROR = new ReflectiveMaterial(ColorUnbounded.WHITE, 1);

    public ReflectiveMaterial(ColorUnbounded albedo, double reflectivity) {
        this.albedo = albedo;
        this.reflectivity = reflectivity;
    }

    @Override
    public boolean scatter(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return scatteredRay(intersectionInformation, incidentRay).direction.dot(intersectionInformation.normal) > 0;
    }

    @Override
    public ColorUnbounded attenuation(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return this.albedo;
    }

    @Override
    public Ray scatteredRay(IntersectionInformation intersectionInformation, Ray incidentRay) {
        Vector3 direction = incidentRay.direction.reflect(intersectionInformation.normal);
        direction = direction.add(Vector3.randomUnitVector().scale(1 - this.reflectivity));

        return new Ray(intersectionInformation.intersectionPoint, direction);
    }
    
}
