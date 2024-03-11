package com.andrewguerra.jaytracer.render;

import java.util.ArrayList;

/**
 * A class to represent a scene as a collection of intersectable scene entities and lights, with the background being a color gradient.
 */
public class Scene {
    /**
     * The collection of scene entities.
     */
    public final ArrayList<SceneEntity> entities;

    /**
     * The collection of lights.
     */
    public final ArrayList<Light> lights;

    /**
     * The background color gradient.
     */
    public final BackgroundGradient gradient;
    
    /**
     * Constructor with a collection of scene entities, lights, and a background color gradient.
     * 
     * @param entities the collection of scene entities
     * @param lights the colleciton of lights
     * @param gradient the background color gradient
     */
    public Scene(ArrayList<SceneEntity> entities, ArrayList<Light> lights, BackgroundGradient gradient) {
        this.entities = entities;
        this.lights = lights;
        this.gradient = gradient;
    }

    /**
     * Constructor with an empty collection of scene entities and lights, with a background color gradient.
     * 
     * @param gradient the background color gradient
     */
    public Scene(BackgroundGradient gradient) {
        this.entities = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.gradient = gradient;
    }

    /**
     * Constructor with an empty collection of scene entities and lights, with a sky color gradient.
     */
    public Scene() {
        this.entities = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.gradient = BackgroundGradient.SKY;
    }
}
