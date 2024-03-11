package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Vector3;

/**
 * Class to represent a color with each component scaled between 0 and 1.
 */
public class Color {
    /**
     * The red component.
     */
    public final double red;

    /**
     * The green component.
     */
    public final double green;

    /**
     * The blue component.
     */
    public final double blue;

    /**
     * The color of pure red.
     */
    public static final Color RED = new Color(1, 0, 0);

    /**
     * The color of pure green.
     */
    public static final Color GREEN = new Color(0, 1, 0);

    /**
     * The color of pure blue.
     */
    public static final Color BLUE = new Color(0, 0, 1);

    /**
     * The color of pure white.
     */
    public static final Color WHITE = new Color(1, 1, 1);

    /**
     * The color of pure black.
     */
    public static final Color BLACK = new Color(0, 0, 0);
    
    private static final double MAX = 1.0;
    private static final double MIN = 0.0;

    /**
     * Constructor with color components red, green, and blue.
     * 
     * @param red The red component
     * @param green The green component
     * @param blue The blue component
     */
    public Color(double red, double green, double blue) {
        this.red = clamp(red);
        this.green = clamp(green);
        this.blue = clamp(blue);
    }

    /**
     * Returns the color whose components are the sum of this and otherColor. The components of the sum color
     * is clamped to values of 0 to 1.
     * 
     * @param otherColor The other color to add to this
     * @return The color whose components are the sum of this and otherColor
     */
    public Color add(Color otherColor) {
        return new Color(this.red + otherColor.red, this.green + otherColor.green, this.blue + otherColor.blue);
    }

    /**
     * Returns the color whose components are the product of this and otherColor.
     * 
     * @param otherColor The other color to multiply by this
     * @return The color whose components are the product of this and otherColor
     */
    public Color product(Color otherColor) {
        return new Color(this.red * otherColor.red, this.green * otherColor.green, this.blue * otherColor.blue);
    }

    /**
     * Returns the color whose components are scaled by coefficent. The scaled components are clamped to value of 0 to 1.
     * 
     * @param coefficient The coefficent to scale by
     * @return The color whose componenets are scaled by coefficent
     */
    public Color multiply(double coefficient) {
        /*if(coefficient < 0) {
            throw new IllegalArgumentException("Cannot multiply color by negative coefficient");
        }*/

        return new Color(coefficient * this.red, coefficient * this.green, coefficient * this.blue);
    }

    /**
     * Returns the integer RGB representation of this. Where B is stored the least significant byte, followed by G and R.
     * 
     * @return The integer RGB represetnation of this
     */
    public int toRGB() {
        int intRed = ((int) Math.round(255 * this.red)) << 16;
        int intGreen = ((int) Math.round(255 * this.green)) << 8;
        int intBlue = ((int) Math.round(255 * this.blue));

        return intRed + intGreen + intBlue;
    }

    /**
     * Returns this color as a vector repsentation.
     * 
     * @return This color as a vector representation
     */
    public Vector3 toVector() {
        return new Vector3(this.red, this.green, this.blue);
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
