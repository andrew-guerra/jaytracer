package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.image.Image;

public abstract class SceneRenderer {
    protected Scene scene;
    protected Camera camera;
    protected int imageWidth, imageHeight;
    protected double aspectRatio;

    /**
     * 
     * @param scene
     * @param camera
     * @param imageWidth
     * @param imageHeight
     */
    public SceneRenderer(Scene scene, Camera camera, int imageWidth, int imageHeight) {
        this.scene = scene;
        this.camera = camera;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.aspectRatio = ((double) imageWidth) / imageHeight;
    }

    /**
     * 
     * @return
     */
    public abstract Image render();
}
