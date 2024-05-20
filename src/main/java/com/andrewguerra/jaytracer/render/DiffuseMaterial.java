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
    public final ColorUnbounded albedo;

    public static final DiffuseMaterial RED = new DiffuseMaterial(ColorUnbounded.RED);
    public static final DiffuseMaterial GREEN = new DiffuseMaterial(ColorUnbounded.GREEN);
    public static final DiffuseMaterial BLUE = new DiffuseMaterial(ColorUnbounded.BLUE);

    public DiffuseMaterial(ColorUnbounded albedo) {
        this.albedo = albedo;
    }

    public DiffuseMaterial() {
        this.albedo = ColorUnbounded.BLACK;
    }

    @Override
    public boolean scatter(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return true;
    }

    @Override
    public ColorUnbounded attenuation(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return this.albedo;            
    }

    @Override
    public Ray scatteredRay(IntersectionInformation intersectionInformation, Ray incidentRay) {
        Vector3 direction = Vector3.randomHemisphereUnitVector(intersectionInformation.normal);//intersectionInformation.normal.add(Vector3.randomUnitVector());

        if(!direction.equals(Vector3.ZERO)) {
            direction = direction.normalize();
        }

        return new Ray(intersectionInformation.intersectionPoint, direction);
    }
}
