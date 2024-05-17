package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

/**
 * A class to represent material properties for a scene entity.
 */
public abstract class Material {
    /**
     * Returns if the ray for the given intersection will be scattered. If it is not scattered, it is absorbed.
     * 
     * @param intersectionInformation The information for an intersection with this material
     * @param incidentRay The incident ray for the intersection
     * @return If the ray for the given intersection will be scattered
     */
    public abstract boolean scatter(IntersectionInformation intersectionInformation, Ray incidentRay);

    /**
     * Returns the attenuation of the material at a given intersection.
     * 
     * @param intersectionInformation The information for an intersection with this material
     * @param incidentRay The incident ray for the intersection
     * @return The attenuation of the material at a given intersection
     */
    public abstract Color attenuation(IntersectionInformation intersectionInformation, Ray incidentRay);

    /**
     * Returns the scattered ray of the material at a given intersection.
     * 
     * @param intersectionInformation The information for an intersection with this material
     * @param incidentRay The incident ray for the intersection
     * @return The scattered ray of the material at a given intersection
     */
    public abstract Ray scatteredRay(IntersectionInformation intersectionInformation, Ray incidentRay);
}
