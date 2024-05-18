package com.andrewguerra.jaytracer.scene;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.*;

import com.andrewguerra.jaytracer.math.Vector3;
import com.andrewguerra.jaytracer.render.BackgroundGradient;
import com.andrewguerra.jaytracer.render.Color;
import com.andrewguerra.jaytracer.render.Cylinder;
import com.andrewguerra.jaytracer.render.DielectricMaterial;
import com.andrewguerra.jaytracer.render.DiffuseMaterial;
import com.andrewguerra.jaytracer.render.Light;
import com.andrewguerra.jaytracer.render.Material;
import com.andrewguerra.jaytracer.render.ReflectiveMaterial;
import com.andrewguerra.jaytracer.render.Scene;
import com.andrewguerra.jaytracer.render.SceneEntity;
import com.andrewguerra.jaytracer.render.Sphere;

public class SceneDescriptorParser {
    public static Scene parse(String filename) {
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();

            while(scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }

            scanner.close();
            String json = stringBuilder.toString();
            JSONObject sceneJson = new JSONObject(json);

            ArrayList<SceneEntity> sceneEntities = parseSceneEntities(sceneJson.getJSONArray("entities"));
            ArrayList<Light> lights = new ArrayList<>();
            BackgroundGradient backgroundGradient = parseBackgroundGradient(sceneJson.getJSONObject("gradient"));

            return new Scene(sceneEntities, lights, backgroundGradient);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<SceneEntity> parseSceneEntities(JSONArray arr) {
        ArrayList<SceneEntity> sceneEntities = new ArrayList<>();
        SceneEntity sceneEntity;

        for(int i = 0; i < arr.length(); i++) {
            sceneEntity = parseSceneEntity(arr.getJSONObject(i));

            if(sceneEntity != null) {
                sceneEntities.add(sceneEntity);
            }
        }

        return sceneEntities;
    }

    private static SceneEntity parseSceneEntity(JSONObject obj) {
        String type = obj.getString("type");
        Vector3 position = parseVector3(obj.getString("position"));
        Material material = parseMaterial(obj.getJSONObject("material"));

        if(type.equals("sphere")) {
            double radius = obj.getDouble("radius");

            return new Sphere(position, material, radius);
        } else if(type.equals("cylinder")) {
            double radius = obj.getDouble("radius");
            double height = obj.getDouble("height");
            Vector3 axis = parseVector3(obj.getString("axis")).normalize();

            return new Cylinder(position, material, axis, radius, height);
        }

        return null;
    }

    private static Vector3 parseVector3(String vector) {
        String[] vectorComponents = vector.split(", ");

        if(vectorComponents.length != 3) {
            return Vector3.ZERO;
        }   
        
        double x = Double.parseDouble(vectorComponents[0]);
        double y = Double.parseDouble(vectorComponents[1]);
        double z = Double.parseDouble(vectorComponents[2]);

        return new Vector3(x, y, z);
    }

    private static Material parseMaterial(JSONObject obj) {
        String type = obj.getString("type");

        if(type.equals("diffuse")) {
            Color albedo = parseVector3(obj.getString("albedo")).toColor();

            return new DiffuseMaterial(albedo);
        } else if(type.equals("reflective")) {
            Color albedo = parseVector3(obj.getString("albedo")).toColor();
            double reflectivity = obj.getDouble("reflectivity");

            return new ReflectiveMaterial(albedo, reflectivity);
        } else if(type.equals("dielectric")) {
            double refractionIndex = obj.getDouble("refractionIndex");
            
            return new DielectricMaterial(refractionIndex);
        }

        return null;
    }

    private static BackgroundGradient parseBackgroundGradient(JSONObject obj) {
        Color topColor = parseVector3(obj.getString("topColor")).toColor();
        Color bottomColor = parseVector3(obj.getString("bottomColor")).toColor();

        return new BackgroundGradient(topColor, bottomColor);
    }
}
