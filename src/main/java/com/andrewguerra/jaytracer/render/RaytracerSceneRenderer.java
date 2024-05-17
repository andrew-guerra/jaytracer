package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.image.Image;
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

        this.rays = new Ray[this.imageHeight][this.imageWidth];
        this.renderThreads = new PixelRenderThread[NUM_THREADS];
        this.viewWindowWidth = calculateViewWindowWidth(this.camera.hfov);
        this.viewWindowHeight = this.viewWindowWidth / this.aspectRatio;

        this.generateRays();
    }

    private double calculateViewWindowWidth(double hfov) {
        double angle = Math.toRadians(hfov) * 0.5;
	    return 2 * this.camera.focalLength * Math.tan(angle);
    }

    private double calculateViewWindowHeight(double vfov) {
        double angle = Math.toRadians(vfov) * 0.5;
	    return 2 * this.camera.focalLength * Math.tan(angle);
    }

    private double calculateViewWindowWidth(double viewWindowHeight, double aspectRatio) {
        return viewWindowWidth * aspectRatio;
    }

    private double calculateViewWindowHeight(double viewWindowWidth, double aspectRatio) {
        return viewWindowWidth / aspectRatio;
    }

    private void generateRays() {
        Vector3 u = camera.ray.direction.cross(camera.up).normalize();
        Vector3 v = u.cross(camera.ray.direction).normalize();
        Vector3 uPrime = u.scale(this.viewWindowWidth / 2);
        Vector3 vPrime = v.scale(this.viewWindowHeight / 2);;

        Vector3 viewWindowCenter = camera.ray.cast(this.camera.focalLength);
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
            if(intersectionInformation.material.scatter(intersectionInformation, ray)) {
                Ray scatteredRay = intersectionInformation.material.scatteredRay(intersectionInformation, ray);
                Color attenuation = intersectionInformation.material.attenuation(intersectionInformation, ray);

                return traceRay(scatteredRay, depth - 1).product(attenuation);
            }
            
            return Color.BLACK;
        } 
        
        return this.scene.gradient.getColor(ray);
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
}
