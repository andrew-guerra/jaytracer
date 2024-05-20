package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

/**
 * Class to represent a gradient for the background of a scene. This gradient is linearly interpolated
 * based on the y-axis.
 */
public class BackgroundGradient extends Background {
    private ColorUnbounded topColor, baseColor;

    /**
     * A gradient to represent a sky.
     */
    public static final BackgroundGradient SKY = new BackgroundGradient(new ColorUnbounded(0.5, 0.7, 1.0), ColorUnbounded.WHITE);

    /**
     * Constructor for a gradient where the higher the y value, the closer the color to topValue and the lower, the
     * closer to baseColor.
     * 
     * @param topColor The color for high y values
     * @param baseColor The color for low y values
     */
    public BackgroundGradient(ColorUnbounded topColor, ColorUnbounded baseColor) {
        this.topColor = topColor;
        this.baseColor = baseColor;
    }

    /**
     * Returns the color interpolated in the gradient based on the view ray.
     * 
     * @param ray A view ray
     * @return The color interpolated in the gradient based on the view ray
     */
    @Override
    public ColorUnbounded getColor(Ray ray) {
        double a = 0.5 * (ray.direction.y + 1.0);
        return this.baseColor.multiply(1.0-a).add(this.topColor.multiply(a));
    }
}
