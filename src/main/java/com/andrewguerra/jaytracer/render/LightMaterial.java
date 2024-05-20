package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

public class LightMaterial extends Material {
    private ColorUnbounded color;

    public LightMaterial(ColorUnbounded color) {
        this.color = color;
    }

    @Override
    public boolean scatter(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return false;
    }

    @Override
    public ColorUnbounded attenuation(IntersectionInformation intersectionInformation, Ray incidentRay) {
       return this.color;
    }

    @Override
    public Ray scatteredRay(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return incidentRay;
    }
    
    @Override
    public ColorUnbounded emitted(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return this.color;
    }
}
