package com.andrewguerra.jaytracer.math;

public class Ray {
    public final Vector3 origin, direction;

    public static final Ray X = new Ray(Vector3.ZERO, Vector3.X);
    public static final Ray Y = new Ray(Vector3.ZERO, Vector3.Y);
    public static final Ray Z = new Ray(Vector3.ZERO, Vector3.Z);

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 cast(double factor) {
        return this.origin.add(this.direction.scale(factor));
    }

    public Ray reflect(Ray normal) {
        Vector3 reflectedDirection = this.direction.subtract(normal.direction.scale(2 * this.direction.dot(normal.direction)));

        return new Ray(normal.origin, reflectedDirection);
    }

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
