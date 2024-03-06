package com.andrewguerra.jaytracer.render;

public class Material {
    public final Color ambientColor, specularColor;
    public final double ambient, diffuse, specular, specularFalloff;
    public final boolean isMirror;

    public final static Material DEFAULT = new Material(Color.RED, Color.WHITE, false);

    public Material(Color ambientColor, Color specularColor, double ambient, double diffuse, double specular, double specularFalloff, boolean isMirror) {
        this.ambientColor = ambientColor;
        this.specularColor = specularColor;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.specularFalloff = specularFalloff;
        this.isMirror = isMirror;
    }

    public Material(Color ambientColor, Color specularColor, boolean isMirror) {
        this.ambientColor = ambientColor;
        this.specularColor = specularColor;
        this.ambient = 0.2;
        this.diffuse = 0.4;
        this.specular = 0.4;
        this.specularFalloff = 20;
        this.isMirror = isMirror;
    }

    public Material(Color ambientColor, boolean isMirror) {
        this.ambientColor = ambientColor;
        this.specularColor = Color.WHITE;
        this.ambient = 0.2;
        this.diffuse = 0.4;
        this.specular = 0.4;
        this.specularFalloff = 20;
        this.isMirror = isMirror;
    }
}
