package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class Box extends SceneEntity {
    private Quad[] quads;

    public Box(Vector3 point1, Vector3 point2, Material material) {
        super(null, material);

        this.quads = new Quad[6];

        Vector3 min = new Vector3(Math.min(point1.x, point2.x), Math.min(point1.y, point2.y), Math.min(point1.z, point2.z));
        Vector3 max = new Vector3(Math.max(point1.x, point2.x), Math.max(point1.y, point2.y), Math.max(point1.z, point2.z));

        Vector3 dx = new Vector3(max.x - min.x, 0, 0);
        Vector3 dy = new Vector3(0, max.y - min.y, 0);
        Vector3 dz = new Vector3(0, 0, max.z - min.z);

        this.quads[0] = new Quad(new Vector3(min.x, min.y, max.z), material, dx, dy);
        this.quads[1] = new Quad(new Vector3(max.x, min.y, max.z), material, dz.negate(), dy);
        this.quads[2] = new Quad(new Vector3(max.x, min.y, min.z), material, dx.negate(), dy);
        this.quads[3] = new Quad(new Vector3(min.x, min.y, min.z), material, dz, dy);
        this.quads[4] = new Quad(new Vector3(min.x, max.y, max.z), material, dx, dz.negate());
        this.quads[5] = new Quad(new Vector3(min.x, min.y, min.z), material, dx, dz);
    }

    @Override
    public double intersectionDistance(Ray ray) {
        double minDistnace = Double.POSITIVE_INFINITY;
        double distance;

        for(Quad quad : quads) {
            distance = quad.intersectionDistance(ray);

            if(distance < minDistnace) {
                minDistnace = distance;
            }
        }

        return minDistnace;
    }

    @Override
    public Ray getSurfaceNormalRay(Vector3 position) {
        for(Quad quad : quads) {
            if(quad.onSurface(position)) {
                return quad.getSurfaceNormalRay(position);
            }
        }

        return null;
    }

    @Override
    public double getUCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        /*for(Quad quad : quads) {
            if(quad.onSurface(position)) {
                return quad.getUCoordinate(intersectionPoint, normal);
            }
        }*/

        return 0;
    }

    @Override
    public double getVCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        /*for(Quad quad : quads) {
            if(quad.onSurface(position)) {
                return quad.getVCoordinate(intersectionPoint, normal);
            }
        }*/

        return 0;
    }
}
