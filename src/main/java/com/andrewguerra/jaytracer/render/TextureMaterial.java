package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

public class TextureMaterial extends DiffuseMaterial {
    private Texture texture;

    public TextureMaterial(Texture texture) {
        this.texture = texture;
    }

    @Override
    public boolean scatter(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return super.scatter(intersectionInformation, incidentRay);
    }

    @Override
    public Color attenuation(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return texture.value(intersectionInformation.u, intersectionInformation.v, intersectionInformation.intersectionPoint);
    }

    @Override
    public Ray scatteredRay(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return super.scatteredRay(intersectionInformation, incidentRay);
    }
    
}
