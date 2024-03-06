package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.image.Image;

public abstract class SceneRenderer {
    protected Scene scene;
    protected Camera camera;
    protected int imageWidth, imageHeight;
    protected double aspectRatio;

    public SceneRenderer(Scene scene, Camera camera, int imageWidth, int imageHeight) {
        this.scene = scene;
        this.camera = camera;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.aspectRatio = ((double) imageWidth) / imageHeight;
    }

    public abstract Image render();
}
