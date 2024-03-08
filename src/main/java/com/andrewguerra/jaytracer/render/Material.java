package com.andrewguerra.jaytracer.render;

public class Material {
    public final Color color, emissionColor;
    public final double emission;

    public final static Material DEFAULT = new Material(Color.RED);

    public Material(Color color, Color emissionColor, double emission) {
        this.color = color;
        this.emissionColor = emissionColor;
        this.emission = emission;
    }

    public Material(Color color) {
        this(color, Color.WHITE, 0.0);
    }
}
