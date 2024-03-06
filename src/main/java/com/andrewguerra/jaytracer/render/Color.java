package com.andrewguerra.jaytracer.render;

public class Color {
    public final double red, green, blue;

    public static final Color RED = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color BLUE = new Color(0, 0, 1);
    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color BLACK = new Color(0, 0, 0);

    private static final double MAX = 1.0;
    private static final double MIN = 0.0;

    public Color(double red, double green, double blue) {
        this.red = clamp(red);
        this.green = clamp(green);
        this.blue = clamp(blue);
    }

    public Color add(Color otherColor) {
        return new Color(this.red + otherColor.red, this.green + otherColor.green, this.blue + otherColor.blue);
    }

    public Color multiply(double coefficient) {
        if(coefficient < 0) {
            throw new IllegalArgumentException("Cannot multiply color by negative coefficient");
        }
        
        return new Color(coefficient * this.red, coefficient * this.green, coefficient * this.blue);
    }

    public int toRGB() {
        int intRed = ((int) Math.round(255 * this.red)) << 16;
        int intGreen = ((int) Math.round(255 * this.green)) << 8;
        int intBlue = ((int) Math.round(255 * this.blue));

        return intRed + intGreen + intBlue;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj.getClass() != Color.class) {
            return false;
        }

        Color otherColor = (Color) obj;

        return this.red == otherColor.red && this.green == otherColor.green && this.blue == otherColor.blue;
    }

    private double clamp(double target) {
        return Math.max(Math.min(target, MAX), MIN);
    }
}
