package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Vector3;

public class SolidTexture extends Texture {
    private Color albedo;

    public SolidTexture(Color albedo) {
        this.albedo = albedo;
    }

    @Override
    public Color value(double u, double v, Vector3 point) {
        return this.albedo;
    }
}
