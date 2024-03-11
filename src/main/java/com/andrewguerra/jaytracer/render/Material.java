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
     * The default material, a red non emittant material.
     */
    public final static Material DEFAULT = new Material(Color.RED);

    /**
     * Constructor with a color, emissionColor, and emission strength.
     * 
     * @param color the absorbtion color
     * @param emissionColor the emittance color
     * @param emission the emission strength
     */
    public Material(Color color, Color emissionColor, double emission) {
        this.color = color;
        this.emissionColor = emissionColor;
        this.emission = emission;
    }

    /**
     * Constructor of a non emittant material with a color
     * 
     * @param color the absorbtion color
     */
    public Material(Color color) {
        this(color, Color.WHITE, 0.0);
    }
}
