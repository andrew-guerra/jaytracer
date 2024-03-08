package com.andrewguerra.jaytracer.math;

import com.andrewguerra.jaytracer.render.Color;

public class Vector3 {
    public final double x, y, z;

    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public static final Vector3 X = new Vector3(1, 0, 0);
    public static final Vector3 Y = new Vector3(0, 1, 0);
    public static final Vector3 Z = new Vector3(0, 0, 1);

    public static final double EQUALS_DELTA = 0.01;
    public static final double NUDGE_FACTOR = 0.0001;
    public static final double MAX_NUDGE_DISTANCE = Math.sqrt(3 * NUDGE_FACTOR * NUDGE_FACTOR);

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double[] asArray() {
        return new double[]{this.x, this.y, this.z};
    }

    public double norm() {
        return Math.sqrt(this.dot(this));
    }

    public double distance(Vector3 otherVector) {
        return this.subtract(otherVector).norm();
    }

    public double dot(Vector3 otherVector) {
        return this.x * otherVector.x + this.y * otherVector.y + this.z * otherVector.z;
    }

    public Vector3 cross(Vector3 otherVector) {
        double x = this.y * otherVector.z - this.z * otherVector.y;
        double y = this.x * otherVector.z - this.z * otherVector.x;
        double z = this.x * otherVector.y - this.y * otherVector.x;

        return new Vector3(x, -y, z);
    }

    public Vector3 add(Vector3 otherVector) {
        return new Vector3(this.x + otherVector.x, this.y + otherVector.y, this.z + otherVector.z);
    }

    public Vector3 subtract(Vector3 otherVector) {
        return new Vector3(this.x - otherVector.x, this.y - otherVector.y, this.z - otherVector.z);
    }

    public Vector3 average(Vector3 otherVector) {
        return this.add(otherVector).scale(0.5);
    }

    public Vector3 scale(double factor) {
        return new Vector3(factor * this.x, factor * this.y, factor * this.z);
    }

    public Vector3 sqrt() {
        if(this.x < 0 || this.y < 0 || this.z < 0) {
            throw new IllegalArgumentException("Cannat take root of negative number");
        }

        return new Vector3(Math.sqrt(this.x), Math.sqrt(this.y), Math.sqrt(this.z));
    }

    public Vector3 normalize() {
        if(this.equals(Vector3.ZERO)) {
            throw new IllegalArgumentException("Cannot normalize the zero vector");
        }

        double norm = norm();

        return new Vector3(this.x / norm, this.y / norm, this.z / norm);
    }

    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
    }

    public Vector3 nudge() {
        double newX = this.x + NUDGE_FACTOR * Random.random(-1, 1);
        double newY = this.y + NUDGE_FACTOR * Random.random(-1, 1);
        double newZ = this.z + NUDGE_FACTOR * Random.random(-1, 1);

        return new Vector3(newX, newY, newZ);
    }

    public Color toColor() {
        return new Color(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj.getClass() != Vector3.class) {
            return false;
        }

        Vector3 otherVector = (Vector3) obj;

        return equalsDelta(otherVector);
    }

    private boolean equalsDelta(Vector3 otherVector) {
        return doubleEquals(this.x, otherVector.x) && doubleEquals(this.y, otherVector.y) && doubleEquals(this.z, otherVector.z);
    }

    private boolean doubleEquals(double n1, double n2) {
        return Math.abs(n1 - n2) <= EQUALS_DELTA;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f)", this.x, this.y, this.z);
    }

    public static Vector3 randomVector() {
        return new Vector3(Random.random(), Random.random(), Random.random());
    }

    public static Vector3 randomVector(double min, double max) {
        return new Vector3(Random.random(min, max), Random.random(min, max), Random.random(min, max));
    }

    public static Vector3 randomHemisphereUnitVector(Vector3 normal) {
        Vector3 randomVector = randomUnitVector();

        if(normal.dot(randomVector) < 0) {
            randomVector = randomVector.negate();
        }

        return randomVector;
    }

    public static Vector3 randomUnitVector() {
        return randomUnitSphereVector().normalize();
    }

    private static Vector3 randomUnitSphereVector() {
        while(true) {
            Vector3 randomVector3 = Vector3.randomVector(-1, 1);

            if(randomVector3.dot(randomVector3) <= 1) {
                return randomVector3;
            }
        }
    }
}
