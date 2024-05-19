package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

/**
 * A class to represent a diffuse material.
 */
public class DiffuseMaterial extends Material{
    /**
     * The albedo for the surface material
     */
    public final Color albedo;

    public static final DiffuseMaterial RED = new DiffuseMaterial(Color.RED);
    public static final DiffuseMaterial GREEN = new DiffuseMaterial(Color.GREEN);
    public static final DiffuseMaterial BLUE = new DiffuseMaterial(Color.BLUE);

    public DiffuseMaterial(Color albedo) {
        this.albedo = albedo;
    }

    public DiffuseMaterial() {
        this.albedo = Color.BLACK;
    }

    @Override
    public boolean scatter(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return true;
    }

    @Override
    public Color attenuation(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return this.albedo;            
    }

    @Override
    public Ray scatteredRay(IntersectionInformation intersectionInformation, Ray incidentRay) {
        Vector3 direction = intersectionInformation.normal.add(Vector3.randomUnitVector());
        
        return new Ray(intersectionInformation.intersectionPoint, direction);
    }
}
