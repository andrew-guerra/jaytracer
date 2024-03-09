package com.andrewguerra.jaytracer.math;

/**
 * Class to represent a ray, a collection of an origin and a direction vector.
 */
public class Ray {
    /**
     * The origin vector of the ray
     */
    public final Vector3 origin;

    /**
     * The direction vector of the ray
     */
    public final Vector3 direction;

    /**
     * The ray starting at the origin with a unit vector direction along the positve X-axis
     */
    public static final Ray X = new Ray(Vector3.ZERO, Vector3.X);

    /**
     * The ray starting at the origin with a unit vector direction along the positve Y-axis
     */
    public static final Ray Y = new Ray(Vector3.ZERO, Vector3.Y);

    /**
     * The ray starting at the origin with a unit vector direction along the positve Z-axis
     */
    public static final Ray Z = new Ray(Vector3.ZERO, Vector3.Z);

    /**
     * Constructor of ray with an origin and a direction
     * 
     * @param origin Origin vector of the ray
     * @param direction Direction vector of the ray
     */
    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     * Cast the ray out from its origin via its direction vector by a factor.
     * 
     * @param factor Factor to scale the direction vector
     * @return Vector of the point made by casting the direction vector by factor.
     */
    public Vector3 cast(double factor) {
        return this.origin.add(this.direction.scale(factor));
    }

    /**
     * Reflect the ray along a normal ray, so that the reflected ray has an origin of the normal ray
     * and the direction is reflected via the normal ray's direction.
     * 
     * @param normal Normal ray to reflect along
     * @return Reflected ray along the normal ray
     */
    public Ray reflect(Ray normal) {
        Vector3 reflectedDirection = this.direction.subtract(normal.direction.scale(2 * this.direction.dot(normal.direction)));

        return new Ray(normal.origin, reflectedDirection);
    }

    /**
     * Return a ray so that the direction is nudged slightly.
     * 
     * @return A ray nudged slightly in a random direction 
     */
    public Ray nudge() {
        return new Ray(this.origin, this.direction.nudge());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj.getClass() != Ray.class) {
            return false;
        }

        Ray otherRay = (Ray) obj;

        return this.origin.equals(otherRay.origin) && this.direction.equals(otherRay.direction);
    }

    @Override
    public String toString() {
        return String.format("%s:%s", this.origin, this.direction);
    }
}
