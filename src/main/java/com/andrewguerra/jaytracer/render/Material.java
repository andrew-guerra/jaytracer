package com.andrewguerra.jaytracer.render;

public class Material {
    public final Color color, emissionColor;
    public final double emission;

    public final static Material DEFAULT = new Material(Color.RED);

    /**
     * 
     * @param color
     * @param emissionColor
     * @param emission
     */
    public Material(Color color, Color emissionColor, double emission) {
        this.color = color;
        this.emissionColor = emissionColor;
        this.emission = emission;
    }

    /**
     * 
     * @param color
     */
    public Material(Color color) {
        this(color, Color.WHITE, 0.0);
    }
}
