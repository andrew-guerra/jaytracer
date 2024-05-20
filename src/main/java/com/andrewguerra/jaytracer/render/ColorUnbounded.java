package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Vector3;

public class ColorUnbounded extends Color {
    public static final ColorUnbounded WHITE = new ColorUnbounded(Color.WHITE);
    public static final ColorUnbounded BLACK = new ColorUnbounded(Color.BLACK);
    public static final ColorUnbounded RED = new ColorUnbounded(Color.RED);
    public static final ColorUnbounded GREEN = new ColorUnbounded(Color.GREEN);
    public static final ColorUnbounded BLUE = new ColorUnbounded(Color.BLUE);

    public ColorUnbounded(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public ColorUnbounded(Color color) {
        this.red = color.red;
        this.green = color.green;
        this.blue = color.blue;
    }

    public Color boundColor() {
        return new Color(this.red, this.green, this.blue);
    }

    public ColorUnbounded product(ColorUnbounded otherColor) {
        double newRed = this.red * otherColor.red;
        double newGreen = this.green * otherColor.green;
        double newBlue = this.blue * otherColor.blue;

        return new ColorUnbounded(newRed, newGreen, newBlue);
    }

    public ColorUnbounded multiply(double coefficient) {
        double newRed = this.red * coefficient;
        double newGreen = this.green * coefficient;
        double newBlue = this.blue * coefficient;

        return new ColorUnbounded(newRed, newGreen, newBlue);
    }

    public ColorUnbounded add(ColorUnbounded otherColor) {
        double newRed = this.red + otherColor.red;
        double newGreen = this.green + otherColor.green;
        double newBlue = this.blue + otherColor.blue;

        return new ColorUnbounded(newRed, newGreen, newBlue);
    }

    @Override
    public Vector3 toVector() {
        return super.toVector();
    }
}
