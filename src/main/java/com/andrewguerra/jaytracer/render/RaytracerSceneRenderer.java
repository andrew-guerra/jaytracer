package com.andrewguerra.jaytracer.render;

import java.util.ArrayList;

import com.andrewguerra.jaytracer.image.Image;
import com.andrewguerra.jaytracer.image.ImageWriter;
import com.andrewguerra.jaytracer.math.Random;
import com.andrewguerra.jaytracer.math.Ray;
import com.andrewguerra.jaytracer.math.Vector3;

/**
 * A class to represent a scene render using the ray tracing methods to render the scene.
 */
public class RaytracerSceneRenderer extends SceneRenderer {
    private Ray[][] rays;
    private PixelRenderStack renderStack;
    private PixelRenderThread[] renderThreads;
    private double viewWindowWidth, viewWindowHeight;

    private static final double D = 5;
    private static final double EPSILON = 0.001;
    private static final int DEPTH_LIMIT = 10;
    private static final int TRACE_AMOUNT = 10;
    private static final int NUM_THREADS = 20;

    /**
     * Constructor with a scene, camera, and image width and height.
     * 
     * @param scene the scene to render
     * @param camera the camera in the scene
     * @param imageWidth the image height
     * @param imageHeight the image widht
     */
    public RaytracerSceneRenderer(Scene scene, Camera camera, int imageWidth, int imageHeight) {
        super(scene, camera, imageWidth, imageHeight);

        this.rays = new Ray[imageHeight][imageWidth];
        this.renderThreads = new PixelRenderThread[NUM_THREADS];
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
        Vector3 u = camera.ray.direction.cross(camera.up).normalize();
        Vector3 v = u.cross(camera.ray.direction).normalize();
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
                direction = ul.add(fullUDelta.add(fullVDelta)).subtract(this.camera.ray.origin).normalize();

                rays[row][col] = new Ray(this.camera.ray.origin, direction);
            }
        }
    }

    /**
     * Render the scene via casting rays into the scene with intersectable entities.
     */
    @Override
    public Image render() {
        Color[][] pixels = castRays();

        return new Image(pixels);
    }

    private Color[][] castRays() {
        Color[][] pixels = new Color[this.imageHeight][this.imageWidth];
        this.renderStack = new PixelRenderStack(pixels, this.rays);

        initializeRenderThreads();
        startRenderThreads();
        joinRenderThreads();

        return pixels;
    }

    private void initializeRenderThreads() {
        for(int i = 0; i < NUM_THREADS; i++) {
            this.renderThreads[i] = new PixelRenderThread(this, renderStack);
        }
    }

    private void startRenderThreads() {
        for(int i = 0; i < NUM_THREADS; i++) {
            this.renderThreads[i].start();
        }
    }

    private void joinRenderThreads() {
        for(int i = 0; i < NUM_THREADS; i++) {
            try {
                this.renderThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cast the ray into the scene, upon the termination of its route, fetch and set the color for its associated pixel.
     * 
     * @param pixels the matrix of pixel colors to set
     * @param row the row of the ray and pixel
     * @param col the column of the ray and pixel
     */
    protected void castRay(Color[][] pixels, int row, int col) {
        Vector3 colorAggregate = Color.BLACK.toVector();

        for(int i = 0; i < TRACE_AMOUNT; i++) {
            colorAggregate = colorAggregate.add(this.traceRay(this.rays[row][col].nudge(), DEPTH_LIMIT).toVector());
        }

        pixels[row][col] = colorAggregate.scale(1.0 / TRACE_AMOUNT).sqrt().toColor();
    }

    /**
     * Returns the color of the cast ray along its path.
     * 
     * @param ray the ray to get the color of
     * @param depth the depth of the ray's bounce
     * @return the color of the cast ray
     */
    protected Color traceRay(Ray ray, int depth) {
        if(depth <= 0) {
            return Color.BLACK;
        }

        IntersectionInformation intersectionInformation = getRayIntersectionInfo(ray);

        if(intersectionInformation.collision) {
            Vector3 direction;
            Color attenuation = intersectionInformation.material.color;

            if(intersectionInformation.material.reflectivity > 0) {
                direction = ray.direction.reflect(intersectionInformation.normal).add(Vector3.randomUnitVector().scale(1 - intersectionInformation.material.reflectivity));
            } else {
                direction = intersectionInformation.normal.add(Vector3.randomUnitVector());
            }

            if(intersectionInformation.material.refractionIndex != 0) {
                double refractionCoefficient = intersectionInformation.internalCollision ? intersectionInformation.material.refractionIndex : 1.0 / intersectionInformation.material.refractionIndex;
                double cosTheta = Math.min(ray.direction.negate().dot(intersectionInformation.normal), 1);
                double sinTheta = Math.sqrt(1 - cosTheta * cosTheta);

                boolean cannotRefract = refractionCoefficient * sinTheta > 1;

                if(cannotRefract || reflectance(cosTheta, refractionCoefficient) > Random.random()) {
                    direction = ray.direction.reflect(intersectionInformation.normal);
                } else {
                    direction = ray.direction.refract(intersectionInformation.normal, refractionCoefficient);
                }

                attenuation = Color.WHITE;
            }

            Ray diffusedRay = new Ray(intersectionInformation.intersectionPoint, direction);

            return traceRay(diffusedRay, depth - 1).product(attenuation);
        } 
        
        return this.scene.gradient.getColor(ray);
    }

    private double reflectance(double cosine, double refractionCoefficient) {
        double r0 = (1 - refractionCoefficient) / (1 + refractionCoefficient);
        r0 = r0 * r0;

        return r0 + (1 - r0) * Math.pow(1 - cosine, 5);
    }

    private IntersectionInformation getRayIntersectionInfo(Ray ray) {
        double minDistance = Double.POSITIVE_INFINITY;
        double entityDistance;
        SceneEntity closestEntity = null;
        boolean collision = false;

        for(SceneEntity entity : this.scene.entities) {
            entityDistance = entity.intersectionDistance(ray);

            if(entityDistance < minDistance && entityDistance >= EPSILON) {
                minDistance = entityDistance;
                closestEntity = entity;
            }
        }

        if(minDistance != Double.POSITIVE_INFINITY && closestEntity != null) {
            collision = true;
        }

        return new IntersectionInformation(closestEntity, ray, minDistance, collision);
    }

    /**
     * The distance of the closest intersection point of the ray to its position.
     * 
     * @param ray the ray to cast
     * @return the distance to the closest intersection point of the ray
     */
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

    public static void main(String[] args) {
        ArrayList<SceneEntity> sceneEntities = new ArrayList<>();
        sceneEntities.add(new Sphere(new Vector3(0, 0, -1.2), new Material(new Color(0.1, 0.2, 0.5), Color.BLACK, 0, 0, 0), 0.5));
        sceneEntities.add(new Sphere(new Vector3(-1, 0, -1), new Material(new Color(0.8, 0.8, 0.8), Color.BLACK, 0, 0.3, 1.5), 0.5));
        sceneEntities.add(new Sphere(new Vector3(-1, 0, -1), new Material(new Color(0.8, 0.8, 0.8), Color.BLACK, 0, 0.3, 1/1.5), 0.4));
        sceneEntities.add(new Sphere(new Vector3(1, 0, -1), new Material(new Color(0.8, 0.6, 0.2), Color.BLACK, 0, 0.8, 0), 0.5));
        sceneEntities.add(new Sphere(new Vector3(0, -100.5, -1), new Material(new Color(0.8, 0.8, 0.0), Color.BLACK, 0, 0, 0), 100));

        ArrayList<Light> lights = new ArrayList<>();
        lights.add(new Light(new Sphere(new Vector3(0, 10, 0), Material.DEFAULT, 1)));

        RaytracerSceneRenderer renderer = new RaytracerSceneRenderer(new Scene(sceneEntities, lights, BackgroundGradient.SKY), Camera.CANONICAL, 1000, 1000);

        ImageWriter.writeImage(renderer.render(), "sphere.png");
    }
}
