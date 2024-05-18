package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

public class BackgroundSolid extends Background {
    private Color color;

    public BackgroundSolid(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(Ray ray) {
        return this.color;
    }
}
