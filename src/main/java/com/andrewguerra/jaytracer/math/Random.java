package com.andrewguerra.jaytracer.math;

/**
 * Class of utility functions to generate random doubles.
 */
public class Random {
    /**
     * Generates a random double between 0 and 1.
     * 
     * @return a random double between 0 and 1
     */
    public static double random() {
        return Math.random();
    }

    /**
     * Generates a random double between min and max.
     * 
     * @param min Minimum value to generate
     * @param max Maximum value to generate
     * @return a random double between min and max
     */
    public static double random(double min, double max) {
        return min + (max - min) * random();
    }
}
