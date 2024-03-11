package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.image.Image;

/**
 * A class to represent a render for a scene, where the render generates an image based on a camera view point
 * with given image dimensions.
 */
public abstract class SceneRenderer {
    protected Scene scene;
    protected Camera camera;
    protected int imageWidth, imageHeight;
    protected double aspectRatio;

    /**
     * Constructor with a scene, camera, and image dimensions
     * 
     * @param scene the scene of the renderer
     * @param camera the camera of the renderer
     * @param imageWidth the height of the image to render
     * @param imageHeight the width of the image to render
     */
    public SceneRenderer(Scene scene, Camera camera, int imageWidth, int imageHeight) {
        this.scene = scene;
        this.camera = camera;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.aspectRatio = ((double) imageWidth) / imageHeight;
    }

    /**
     * Returns an image with the specified image dimensions which is a render of the scene.
     * 
     * @return a rendered image of the scene
     */
    public abstract Image render();
}
