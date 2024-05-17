package com.andrewguerra.jaytracer.render;

/**
 * A class to represent material properties for a scene entity.
 */
public class Material {
    /**
     * The color that this material absorbs.
     */
    public final Color color;

    /**
     * The color that this material emmits.
     */
    public final Color emissionColor;

    /**
     * The strength of the emission.
     */
    public final double emission;

    /**
     * The reflectivity of a material on a scale from 0-1. A reflectivity of 1 represents
     * a purly reflective material. A reflectivity of 0 represents no reflection (a purly diffuse material).
     */
    public final double reflectivity;

    /**
     * The refractivity of the material.
     */
    public final double refractionIndex;

    /**
     * The default material, a red non emittant material.
     */
    public final static Material DEFAULT = new Material(Color.RED);

    /**
     * Constructor with a color, emissionColor, and emission strength.
     * 
     * @param color the absorbtion color
     * @param emissionColor the emittance color
     * @param emission the emission strength
     * @param reflectivity the reflectivity
     * @param refractivity the refractivity
     */
    public Material(Color color, Color emissionColor, double emission, double reflectivity, double refractionIndex) {
        this.color = color;
        this.emissionColor = emissionColor;
        this.emission = emission;
        this.reflectivity = reflectivity;
        this.refractionIndex = refractionIndex;
    }

    /**
     * Constructor of a non emittant material with a color
     * 
     * @param color the absorbtion color
     */
    public Material(Color color) {
        this(color, Color.WHITE, 0.0, 0.0, 0.0);
    }
}
