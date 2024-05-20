package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

public class BackgroundSolid extends Background {
    private ColorUnbounded color;

    public BackgroundSolid(ColorUnbounded color) {
        this.color = color;
    }

    @Override
    public ColorUnbounded getColor(Ray ray) {
        return this.color;
    }
}
