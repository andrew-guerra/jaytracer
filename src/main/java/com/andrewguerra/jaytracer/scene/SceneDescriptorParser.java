package com.andrewguerra.jaytracer.scene;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.*;

import com.andrewguerra.jaytracer.image.Image;
import com.andrewguerra.jaytracer.math.Vector3;
import com.andrewguerra.jaytracer.render.Background;
import com.andrewguerra.jaytracer.render.BackgroundGradient;
import com.andrewguerra.jaytracer.render.BackgroundSolid;
import com.andrewguerra.jaytracer.render.Box;
import com.andrewguerra.jaytracer.render.Camera;
import com.andrewguerra.jaytracer.render.Color;
import com.andrewguerra.jaytracer.render.ColorUnbounded;
import com.andrewguerra.jaytracer.render.Cylinder;
import com.andrewguerra.jaytracer.render.DielectricMaterial;
import com.andrewguerra.jaytracer.render.DiffuseMaterial;
import com.andrewguerra.jaytracer.render.ImageTexture;
import com.andrewguerra.jaytracer.render.LightMaterial;
import com.andrewguerra.jaytracer.render.Material;
import com.andrewguerra.jaytracer.render.Quad;
import com.andrewguerra.jaytracer.render.ReflectiveMaterial;
import com.andrewguerra.jaytracer.render.Scene;
import com.andrewguerra.jaytracer.render.SceneEntity;
import com.andrewguerra.jaytracer.render.SolidTexture;
import com.andrewguerra.jaytracer.render.Sphere;
import com.andrewguerra.jaytracer.render.TestTexture;
import com.andrewguerra.jaytracer.render.Texture;
import com.andrewguerra.jaytracer.render.TextureMaterial;

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
            Background background = parseBackground(sceneJson.getJSONObject("background"));

            return new Scene(sceneEntities, background);
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
        Vector3 position = parseVector3(obj.optString("position", "0, 0, 0"));
        Material material = parseMaterial(obj.getJSONObject("material"));

        if(type.equals("sphere")) {
            double radius = obj.getDouble("radius");

            return new Sphere(position, material, radius);
        } else if(type.equals("cylinder")) {
            double radius = obj.getDouble("radius");
            double height = obj.getDouble("height");
            Vector3 axis = parseVector3(obj.getString("axis")).normalize();

            return new Cylinder(position, material, axis, radius, height);
        } else if(type.equals("quad")) {
            Vector3 u = parseVector3(obj.getString("u"));
            Vector3 v = parseVector3(obj.getString("v"));

            return new Quad(position, material, u, v);
        } else if(type.equals("box")) {
            Vector3 point1 = parseVector3(obj.getString("point1"));
            Vector3 point2 = parseVector3(obj.getString("point2"));

            return new Box(point1, point2, material);
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
            ColorUnbounded albedo = parseVector3(obj.getString("albedo")).toColorUnbounded();
            
            return new DiffuseMaterial(albedo);
        } else if(type.equals("reflective")) {
            ColorUnbounded albedo = parseVector3(obj.getString("albedo")).toColorUnbounded();
            double reflectivity = obj.getDouble("reflectivity");

            return new ReflectiveMaterial(albedo, reflectivity);
        } else if(type.equals("dielectric")) {
            double refractionIndex = obj.getDouble("refractionIndex");
            
            return new DielectricMaterial(refractionIndex);
        } else if(type.equals("light")) {
            ColorUnbounded color = parseVector3(obj.getString("color")).toColorUnbounded(); 

            return new LightMaterial(color);
        } else if(type.equals("texture")) {
            return new TextureMaterial(parseTexture(obj.getJSONObject("texture")));
        }

        return null;
    }

    private static Background parseBackground(JSONObject obj) {
        String type = obj.getString("type");

        if(type.equals("solid")) {
            ColorUnbounded color = parseVector3(obj.getString("color")).toColorUnbounded();

            return new BackgroundSolid(color);
        } else if(type.equals("gradient")) {
            ColorUnbounded topColor = parseVector3(obj.getString("topColor")).toColorUnbounded();
            ColorUnbounded bottomColor = parseVector3(obj.getString("bottomColor")).toColorUnbounded();

            return new BackgroundGradient(topColor, bottomColor);
        }   
        
        return null;
    }

    private static Texture parseTexture(JSONObject obj) {
        String type = obj.getString("type");

        if(type.equals("solid")) {
            ColorUnbounded albedo = parseVector3(obj.getString("albedo")).toColorUnbounded();

            return new SolidTexture(albedo);
        } else if(type.equals("test")) {
            return new TestTexture();
        } else if(type.equals("image")) {
            String path = obj.getString("path");
            Image image;

            try {
                image = new Image(path);
                return new ImageTexture(image);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        
        return null;
    }
}
