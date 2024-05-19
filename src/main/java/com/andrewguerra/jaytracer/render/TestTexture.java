package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Vector3;

public class TestTexture extends Texture {
    @Override
    public Color value(double u, double v, Vector3 point) {
        return new Color(u, v, u + v);
    }
}
