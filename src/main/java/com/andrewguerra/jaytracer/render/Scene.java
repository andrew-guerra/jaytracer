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
     * The background color gradient.
     */
    public final Background background;
    
    /**
     * Constructor with a collection of scene entities, and a background color gradient.
     * 
     * @param entities the collection of scene entities
     * @param background The background of the scene
     */
    public Scene(ArrayList<SceneEntity> entities, Background background) {
        this.entities = entities;
        this.background = background;
    }

    /**
     * Constructor with an empty collection of scene entities and lights, with a background.
     * 
     * @param background the background 
     */
    public Scene(Background background) {
        this.entities = new ArrayList<>();
        this.background = background;
    }

    /**
     * Constructor with an empty collection of scene entities and lights, with a sky color gradient.
     */
    public Scene() {
        this.entities = new ArrayList<>();
        this.background = BackgroundGradient.SKY;
    }
}
