package com.andrewguerra.jaytracer.math;

import com.andrewguerra.jaytracer.render.Color;
import com.andrewguerra.jaytracer.render.ColorUnbounded;

/**
 * Class to represent a 3D vector
 */
public class Vector3 {
    /**
     * The x componenet of the vector
     */
    public final double x;

    /**
     * The y componenet of the vector
     */
    public final double y;

     /**
     * The z componenet of the vector
     */
    public final double z;

    /**
     * The zero vector, with all components set to zero
     */
    public static final Vector3 ZERO = new Vector3(0, 0, 0);

    /**
     * The unit vector in the positive x direction
     */
    public static final Vector3 X = new Vector3(1, 0, 0);

    /**
     * The unit vector in the positive y direction
     */
    public static final Vector3 Y = new Vector3(0, 1, 0);

    /**
     * The unit vector in the positive z direction
     */
    public static final Vector3 Z = new Vector3(0, 0, 1);

    /**
     * The delta between two vector components for them to be considered equal
     */
    public static final double EQUALS_DELTA = 0.01;

    /**
     * The maximum amount by which any vector component can be nudged
     */
    public static final double NUDGE_FACTOR = 0.0001;

    /**
     * The maximum distance a vector can be nudged
     */
    public static final double MAX_NUDGE_DISTANCE = Math.sqrt(3 * NUDGE_FACTOR * NUDGE_FACTOR);

    /**
     * Constructor of a ray with all three components.
     * 
     * @param x The x componenet of the vector
     * @param y The y componenet of the vector
     * @param z The z componenet of the vector
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the vector componets in a three length double array.
     * 
     * @return The vector componets in a three length double array
     */
    public double[] asArray() {
        return new double[]{this.x, this.y, this.z};
    }

    /**
     * Returns the norm of the vector.
     * 
     * @return The norm of the vector
     */
    public double norm() {
        return Math.sqrt(this.dot(this));
    }

    /**
     * Returns the distance between this vector and otherVector.
     * 
     * @param otherVector The other vector to calculate the distance to
     * @return The distance between two vectors
     */
    public double distance(Vector3 otherVector) {
        return this.subtract(otherVector).norm();
    }

    /**
     * Returns the dot product of this vecto0r and otherVector.
     * 
     * @param otherVector The other vector to take the dot product with
     * @return The dot product of this vector and otherVector
     */
    public double dot(Vector3 otherVector) {
        return this.x * otherVector.x + this.y * otherVector.y + this.z * otherVector.z;
    }

    /**
     * Returns the cross product of this vector and otherVector
     * 
     * @param otherVector The other vector to take the cross product with
     * 
     * @return The cross product of this vector and otherVector
     */
    public Vector3 cross(Vector3 otherVector) {
        double x = this.y * otherVector.z - this.z * otherVector.y;
        double y = this.x * otherVector.z - this.z * otherVector.x;
        double z = this.x * otherVector.y - this.y * otherVector.x;

        return new Vector3(x, -y, z);
    }

    /**
     * Returns the sum of this vector and otherVector.
     * 
     * @param otherVector The other vector to take the sum with
     * 
     * @return The sum of this vector and otherVector
     */
    public Vector3 add(Vector3 otherVector) {
        return new Vector3(this.x + otherVector.x, this.y + otherVector.y, this.z + otherVector.z);
    }

    /**
     * Returns the difference between this vector and otherVector.
     * 
     * @param otherVector The other vector to subtract
     * @return The difference between this vector and otherVector
     */
    public Vector3 subtract(Vector3 otherVector) {
        return new Vector3(this.x - otherVector.x, this.y - otherVector.y, this.z - otherVector.z);
    }

    /**
     * Returns the vector whose components are the average components of this vector and otherVector
     * 
     * @param otherVector The vector to take the average with
     * @return The vector whose components are the average components of this vector and otherVector
     */
    public Vector3 average(Vector3 otherVector) {
        return this.add(otherVector).scale(0.5);
    }

    /**
     * Returns the vector whose components are this vector's components scaled by factor
     * 
     * @param factor The factor to scale the vector by
     * @return The vector whose components are this vector's components scaled by factor
     */
    public Vector3 scale(double factor) {
        return new Vector3(factor * this.x, factor * this.y, factor * this.z);
    }

    /**
     * Returns the vector whose components are the swaure root of this vetor's components
     * 
     * @return The vector whose components are the square root of this vector's components
     */
    public Vector3 sqrt() {
        if(this.x < 0 || this.y < 0 || this.z < 0) {
            throw new IllegalArgumentException("Cannat take root of negative number");
        }

        return new Vector3(Math.sqrt(this.x), Math.sqrt(this.y), Math.sqrt(this.z));
    }

    /**
     * Return the normalized vector of this vector.
     * 
     * @return The normalized vector of this vector
     */
    public Vector3 normalize() {
        if(this.equals(Vector3.ZERO)) {
            throw new IllegalArgumentException("Cannot normalize the zero vector");
        }

        double norm = norm();

        return new Vector3(this.x / norm, this.y / norm, this.z / norm);
    }

    /**
     * Returns the vector whose components are the negative of this vector's components
     * 
     * @return The vector whose components are the negative of this vector's components
     */
    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
    }

    /**
     * Returns the vector reflected across the normal. The vector returned will respect 
     * the direction of the normal.
     * 
     * @return The vector reflected across the normal
     */
    public Vector3 reflect(Vector3 normal) {
        return this.subtract(normal.scale(2 * this.dot(normal)));
    }

    /**
     * Returns the refracted vector with respect to the nomal vector. The refractance coefficent
     * is the ratio of the initial refractance to the the final refractance.
     * 
     * @return The vector refracted across the normal
     */
    public Vector3 refract(Vector3 normal, double refractanceCoefficent) {
        double cosTheta = Math.min(this.negate().dot(normal), 1);
        Vector3 refractPerpendicular = this.add(normal.scale(cosTheta)).scale(refractanceCoefficent);
        Vector3 refractParallel = normal.scale(-Math.sqrt(Math.abs(1 - refractPerpendicular.dot(refractPerpendicular))));

        return refractPerpendicular.add(refractParallel);
    }

    /**
     * Returns the vector whose components are nudged ranomly by NUDGE_FACTOR
     * 
     * @return The vector whose components are nudged randomly by NUDGE_FACTOR
     */
    public Vector3 nudge() {
        double newX = this.x + NUDGE_FACTOR * Random.random(-1, 1);
        double newY = this.y + NUDGE_FACTOR * Random.random(-1, 1);
        double newZ = this.z + NUDGE_FACTOR * Random.random(-1, 1);

        return new Vector3(newX, newY, newZ);
    }

    /**
     * Returns the color whose color values map to this vector's components
     * 
     * @return The color whose color values map to this vector's components
     */
    public Color toColor() {
        return new Color(this.x, this.y, this.z);
    }

    public ColorUnbounded toColorUnbounded() {
        return new ColorUnbounded(this.x, this.y, this.z);
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

    /**
     * Returns a random vector whose component values are fom 0 to 1.
     * 
     * @return A random vector whose component values are from 0 to 1
     */
    public static Vector3 randomVector() {
        return new Vector3(Random.random(), Random.random(), Random.random());
    }

    /**
     * Returns a random vector whose component values are from min to max.
     * 
     * @param min The minimum value to be generated
     * @param max The maximum value to be generated
     * @return A random vector whose component values are from min to max
     */
    public static Vector3 randomVector(double min, double max) {
        return new Vector3(Random.random(min, max), Random.random(min, max), Random.random(min, max));
    }

    /**
     * Returns a random vector whose direction is within the hemisphere define dby normal.
     * 
     * @param normal The normal vector of some surface
     * @return A random vector whose direction is within the hemisphere defined by normal
     */
    public static Vector3 randomHemisphereUnitVector(Vector3 normal) {
        Vector3 randomVector = randomUnitVector();

        if(normal.dot(randomVector) < 0) {
            randomVector = randomVector.negate();
        }

        return randomVector;
    }

    /**
     * Returns a random unit vector.
     * 
     * @return A random unit vector
     */
    public static Vector3 randomUnitVector() {
        return randomUnitSphereVector().normalize();
    }

    private static Vector3 randomUnitSphereVector() {
        while(true) {
            Vector3 randomVector3 = Vector3.randomVector(-1, 1);

            if(randomVector3.dot(randomVector3) <= 1 && !randomVector3.equals(ZERO)) {
                return randomVector3;
            }
        }
    }

    /**
     * Returns a random vector inside of the unit disk on the XY plane.
     * 
     * @return A random vector inside of the unit disk on the XY plane
     */
    public Vector3 randomVectorInUnitDisk() {
        while(true) {
            Vector3 vector = new Vector3(Random.random(-1, 1), Random.random(-1, 1), 0);

            if(vector.norm() < 1) {
                return vector;
            }
        }
    }
}
