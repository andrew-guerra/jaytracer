package com.andrewguerra.jaytracer.render;

import java.util.ArrayList;

public class Scene {
    public final ArrayList<SceneEntity> entities;
    public final ArrayList<Light> lights;

    public final Color backgroundColor;
    
    public Scene(ArrayList<SceneEntity> entities, ArrayList<Light> lights, Color backgroundColor) {
        this.entities = entities;
        this.lights = lights;
        this.backgroundColor = backgroundColor;
    }

    public Scene(Color backgroundColor) {
        this.entities = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.backgroundColor = backgroundColor;
    }

    public Scene() {
        this.entities = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.backgroundColor = Color.BLACK;
    }
}
