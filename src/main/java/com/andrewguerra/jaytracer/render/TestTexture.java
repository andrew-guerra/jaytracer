package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Vector3;

public class TestTexture extends Texture {
    @Override
    public ColorUnbounded value(double u, double v, Vector3 point) {
        return new ColorUnbounded(u, v, u + v);
    }
}
