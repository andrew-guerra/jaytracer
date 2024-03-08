package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

public class BackgroundGradient {
    private Color topColor, baseColor;

    public static final BackgroundGradient SKY = new BackgroundGradient(new Color(0.5, 0.7, 1.0), Color.WHITE);

    public BackgroundGradient(Color topColor, Color baseColor) {
        this.topColor = topColor;
        this.baseColor = baseColor;
    }

    public Color getColor(Ray ray) {
        double a = 0.5 * (ray.direction.y + 1.0);
        return this.baseColor.multiply(1.0-a).add(this.topColor.multiply(a));
    }
}
