package com.andrewguerra.jaytracer.app;

import com.andrewguerra.jaytracer.image.Image;
import com.andrewguerra.jaytracer.image.ImageWriter;
import com.andrewguerra.jaytracer.math.Vector3;
import com.andrewguerra.jaytracer.render.Camera;
import com.andrewguerra.jaytracer.render.RaytracerSceneRenderer;
import com.andrewguerra.jaytracer.render.Scene;
import com.andrewguerra.jaytracer.scene.SceneDescriptorParser;

public class Jaytracer {
    public static void main(String[] args) {
        if(args.length < 4) {
            System.out.println("Usuage - sceneDescriptorFilePath imageFileName imageWidth imageHeight");
            return;
        }
        
        String sceneDescriptorFileName = args[0];
        String imageFileName = args[1];
        int imageWidth = Integer.parseInt(args[2]);
        int imageHeight = Integer.parseInt(args[3]);

        Scene scene = SceneDescriptorParser.parse(sceneDescriptorFileName);
        Camera camera = Camera.CANONICAL;//new Camera(new Vector3(278, 278, -800), new Vector3(278, 278, 0), Vector3.Y, 40, 40);
        RaytracerSceneRenderer render = new RaytracerSceneRenderer(scene, camera, imageWidth, imageHeight);
        Image image = render.render();

        ImageWriter.writeImage(image, imageFileName);
    }
}
