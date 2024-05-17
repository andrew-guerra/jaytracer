package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Random;
import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

/**
 * A class to represent a dieletric material. This transparent material has a refraction index which 
 * determines how much light rays are refracted as they enter the material.
 */
public class DielectricMaterial extends Material {
    /**
     * The refraction index of the material, determines how much light rays are refracted.
     */
    public final double refractionIndex;

    public static final DielectricMaterial GLASS = new DielectricMaterial(1.5);

    public DielectricMaterial(double refractionIndex) {
        this.refractionIndex = refractionIndex;
    }

    @Override
    public boolean scatter(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return true;
    }

    @Override
    public Color attenuation(IntersectionInformation intersectionInformation, Ray incidentRay) {
        return Color.WHITE;
    }

    @Override
    public Ray scatteredRay(IntersectionInformation intersectionInformation, Ray incidentRay) {
        double refractionCoefficient = intersectionInformation.internalCollision ? this.refractionIndex : 1.0 / this.refractionIndex;
        double cosTheta = Math.min(incidentRay.direction.negate().dot(intersectionInformation.normal), 1);
        double sinTheta = Math.sqrt(1 - cosTheta * cosTheta);

        boolean cannotRefract = refractionCoefficient * sinTheta > 1;
        Vector3 direction;

        if(cannotRefract || reflectance(cosTheta, refractionCoefficient) > Random.random()) {
            direction = incidentRay.direction.reflect(intersectionInformation.normal);
        } else {
            direction = incidentRay.direction.refract(intersectionInformation.normal, refractionCoefficient);
        }

        return new Ray(intersectionInformation.intersectionPoint, direction);
    }
    
    /**
     * Returns Schlick's approximation for reflectance.
     * 
     * @param cosine 
     * @param refractionCoefficient
     * @return Schlick's approximation for reflectance.
     */
    private double reflectance(double cosine, double refractionCoefficient) {
        double r0 = (1 - refractionCoefficient) / (1 + refractionCoefficient);
        r0 = r0 * r0;

        return r0 + (1 - r0) * Math.pow(1 - cosine, 5);
    }
}
