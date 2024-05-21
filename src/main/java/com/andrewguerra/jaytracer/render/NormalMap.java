package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class NormalMap extends SceneEntity {
    private SceneEntity entity;
    private ImageTexture normals;

    public NormalMap(SceneEntity entity, ImageTexture normals) {
        this.entity = entity;
        this.normals = normals;
    }

    @Override
    public double intersectionDistance(Ray ray) {
        return this.entity.intersectionDistance(ray);
    }

    @Override
    public Ray getSurfaceNormalRay(Vector3 position) {
        double u = this.entity.getUCoordinate(position, null);
        double v = this.entity.getVCoordinate(position, null);
        Vector3 normal = this.normals.value(u, v, position).toVector();

        return new Ray(position, normal);
    }

    @Override
    public double getUCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        return this.entity.getUCoordinate(intersectionPoint, normal);
    }

    @Override
    public double getVCoordinate(Vector3 intersectionPoint, Vector3 normal) {
        return this.entity.getVCoordinate(intersectionPoint, normal);
    }
}
