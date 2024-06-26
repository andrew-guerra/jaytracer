package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Vector3;

public abstract class Texture {
    public abstract ColorUnbounded value(double u, double v, Vector3 point);
}
