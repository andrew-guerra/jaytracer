package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Vector3;

public class SolidTexture extends Texture {
    private ColorUnbounded albedo;

    public SolidTexture(ColorUnbounded albedo) {
        this.albedo = albedo;
    }

    @Override
    public ColorUnbounded value(double u, double v, Vector3 point) {
        return this.albedo;
    }
}
