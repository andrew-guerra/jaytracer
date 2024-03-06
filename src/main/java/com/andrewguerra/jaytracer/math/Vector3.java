package com.andrewguerra.jaytracer.math;

public class Vector3 {
    public final double x, y, z;

    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public static final Vector3 X = new Vector3(1, 0, 0);
    public static final Vector3 Y = new Vector3(0, 1, 0);
    public static final Vector3 Z = new Vector3(0, 0, 1);

    public static final double EQUALS_DELTA = 0.001;

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
        return otherVector.subtract(otherVector).norm();
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

    public Vector3 normal() {
        double norm = norm();

        return new Vector3(this.x / norm, this.y / norm, this.z / norm);
    }

    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
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
        return String.format("(%f, %f, %f)", this.x, this.y, this.z);
    }
}
