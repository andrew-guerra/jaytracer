package com.andrewguerra.jaytracer.render;

import java.util.ArrayList;

import com.andrewguerra.jaytracer.image.Image;
import com.andrewguerra.jaytracer.image.ImageWriter;
import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

public class RaytracerSceneRenderer extends SceneRenderer {
    private Ray[][] rays;
    private double viewWindowWidth, viewWindowHeight;

    private static final double D = 5;
    private static final double EPSILON = 0.001;
    private static final int DEPTH_LIMIT = 3;

    public RaytracerSceneRenderer(Scene scene, Camera camera, int imageWidth, int imageHeight) {
        super(scene, camera, imageWidth, imageHeight);

        this.rays = new Ray[imageHeight][imageWidth];
        this.viewWindowWidth = calculateViewWindowWidth(camera.hfov);
        this.viewWindowHeight = calculateViewWindowHeight(this.viewWindowWidth, this.aspectRatio);

        this.generateRays();
    }

    private double calculateViewWindowWidth(double hfov) {
        double angle = Math.toRadians(hfov) * 0.5;
	    return 2 * D * Math.tan(angle);
    }

    private double calculateViewWindowHeight(double viewWindowWidth, double aspectRatio) {
        return viewWindowWidth / aspectRatio;
    }

    private void generateRays() {
        Vector3 u = camera.ray.direction.cross(camera.up).normal();
        Vector3 v = u.cross(camera.ray.direction).normal();
        Vector3 uPrime = u.scale(this.viewWindowWidth / 2);
        Vector3 vPrime = v.scale(this.viewWindowHeight / 2);;

        Vector3 viewWindowCenter = camera.ray.cast(D);
        Vector3 ul = viewWindowCenter.add(vPrime.add(uPrime.negate()));
        Vector3 ur = viewWindowCenter.add(vPrime.add(uPrime));
        Vector3 ll = viewWindowCenter.add(vPrime.negate().add(uPrime.negate()));

        Vector3 uDelta = ur.subtract(ul).scale(1.0 / (this.imageWidth - 1));
        Vector3 vDelta = ll.subtract(ul).scale(1.0 / (this.imageWidth - 1));

        Vector3 fullUDelta, fullVDelta, direction;
        for(int row = 0; row < this.imageHeight; row++) {
            fullVDelta = vDelta.scale(row);

            for(int col = 0; col < this.imageWidth; col++) {
                fullUDelta = uDelta.scale(col);
                direction = ul.add(fullUDelta.add(fullVDelta)).subtract(this.camera.ray.origin).normal();

                rays[row][col] = new Ray(this.camera.ray.origin, direction);
            }
        }
    }

    @Override
    public Image render() {
        Color[][] pixels = castRays();

        return new Image(pixels);
    }

    private Color[][] castRays() {
        Color[][] pixels = new Color[this.imageHeight][this.imageWidth];

        for(int row = 0; row < this.imageHeight; row++) {
            for(int col = 0; col < this.imageWidth; col++) {
                pixels[row][col] = this.traceRay(this.rays[row][col]);
            }
        }

        return pixels;
    }

    protected Color traceRay(Ray ray) {
        return traceRay(ray, 0);
    }

    protected Color traceRay(Ray ray, int depth) {
        if(depth == DEPTH_LIMIT) {
            return Color.BLUE;
        }

        double minDistance = Double.POSITIVE_INFINITY;
        double entityDistance;
        SceneEntity closestEntity = null;

        for(SceneEntity entity : this.scene.entities) {
            entityDistance = entity.intersectionDistance(ray);

            if(entityDistance < minDistance && entityDistance >= 0) {
                minDistance = entityDistance;
                closestEntity = entity;
            }
        }

        if(minDistance == Double.POSITIVE_INFINITY || closestEntity == null) {
            return scene.backgroundColor;
        }

        return this.intersectionColor(new IntersectionInformation(closestEntity, ray, minDistance, this.camera), depth);
    }

    private Color intersectionColor(IntersectionInformation intersectionInformation, int depth) {
        Ray surfaceNormalRay = intersectionInformation.entity.getSurfaceNormalRay(intersectionInformation.intersectionPoint);  

        if(intersectionInformation.entity.material.isMirror) {
            Ray reflectedRay = intersectionInformation.incidentRay.reflect(surfaceNormalRay);

            return this.traceRay(reflectedRay, depth + 1);
        }
        
        Color illuminationColor = intersectionInformation.entity.material.ambientColor.multiply(intersectionInformation.entity.material.ambient);
        for(Light light : this.scene.lights) {
            Vector3 ligthDirection = light.entity.position.subtract(intersectionInformation.intersectionPoint).normal();//normalize(subtractVectors(lights.at(i)->vector, surfaceIntersectionVector)); 
            Vector3 h = ligthDirection.average(intersectionInformation.camera.ray.direction); //hVector = normalize(averageVector(ligthDirectionVector, viewDirectionVector));
            
            Color diffuseIlluminationColor = intersectionInformation.entity.material.ambientColor.multiply(intersectionInformation.entity.material.diffuse * Math.max(0, surfaceNormalRay.direction.dot(ligthDirection)));//diffuseIlluminationColor = multiplyCoefficient(multiplyCoefficient(colorToVector(diffuseColor), sphere.materialColor->diffuse), std::max(0.0f, dotProduct(surfaceNormal, ligthDirectionVector)));
            Color specularIlluminationColor = intersectionInformation.entity.material.specularColor.multiply(intersectionInformation.entity.material.specular * Math.pow(Math.max(0, surfaceNormalRay.direction.dot(h)), intersectionInformation.entity.material.specularFalloff));//specularIlluminationColor = multiplyCoefficient(multiplyCoefficient(colorToVector(sphere.materialColor->specularColor), sphere.materialColor->specular), float(pow(std::max(0.0f, dotProduct(surfaceNormal, hVector)), sphere.materialColor->specularFalloff))); 
            illuminationColor = illuminationColor.add(diffuseIlluminationColor).add(specularIlluminationColor);//illuminationColor = addVectors(illuminationColor, multiplyCoefficient(multiplyCoefficient(multiplyVectors(colorToVector(lights.at(i)->color), addVectors(diffuseIlluminationColor, specularIlluminationColor)), shadowFlag), attentuationFactor));
        }

        return illuminationColor;
    }

    protected double closestIntersectionDistance(Ray ray) {
        double minDistance = Double.POSITIVE_INFINITY;
        double entityDistance;

        for(SceneEntity entity : this.scene.entities) {
            entityDistance = entity.intersectionDistance(ray);

            if(entityDistance < minDistance && entityDistance >= 0) {
                minDistance = entityDistance;
            }
        }

        return minDistance;
    }

    protected boolean isShadowed(Vector3 position) {
        for(Light light : this.scene.lights) {
            double lightDistance = light.distance(position);
            double closestEntityDistance = closestIntersectionDistance(light.reverseLightRay(position));

            if(closestEntityDistance < lightDistance && closestEntityDistance > EPSILON) {
                return true;
            }   
        }

        return false;
    }

    public static void main(String[] args) {
        ArrayList<SceneEntity> sceneEntities = new ArrayList<>();
        sceneEntities.add(new Sphere(new Vector3(-4, 0, -3), Material.DEFAULT, 1));
        sceneEntities.add(new Sphere(new Vector3(4, 0, -3), new Material(Color.GREEN, false), 1));
        sceneEntities.add(new Sphere(new Vector3(0, 0, -3), new Material(Color.BLUE, true), 1));

        ArrayList<Light> lights = new ArrayList<>();
        lights.add(new Light(new Sphere(new Vector3(0, 10, 0), Material.DEFAULT, 1)));

        RaytracerSceneRenderer renderer = new RaytracerSceneRenderer(new Scene(sceneEntities, lights, Color.BLACK), Camera.CANONICAL, 1000, 1000);

        ImageWriter.writeImage(renderer.render(), "sphere.png");
    }
}
