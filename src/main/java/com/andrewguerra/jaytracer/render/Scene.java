package com.andrewguerra.jaytracer.render;

import java.util.ArrayList;

public class Scene {
    public final ArrayList<SceneEntity> entities;
    public final ArrayList<Light> lights;
    public final BackgroundGradient gradient;
    
    public Scene(ArrayList<SceneEntity> entities, ArrayList<Light> lights, BackgroundGradient gradient) {
        this.entities = entities;
        this.lights = lights;
        this.gradient = gradient;
    }

    public Scene(BackgroundGradient gradient) {
        this.entities = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.gradient = gradient;
    }

    public Scene() {
        this.entities = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.gradient = BackgroundGradient.SKY;
    }
}
