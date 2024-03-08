package com.andrewguerra.jaytracer.math;

public class Random {
    public static double random() {
        return Math.random();
    }

    public static double random(double min, double max) {
        return min + (max - min) * random();
    }
}
