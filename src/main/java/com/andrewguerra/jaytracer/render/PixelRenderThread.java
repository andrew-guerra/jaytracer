package com.andrewguerra.jaytracer.render;

/**
 * A class to represent a thread to render pixel render jobs. This thread will run until there are no
 * more render jobs left.
 */
public class PixelRenderThread extends Thread {
    private RaytracerSceneRenderer renderer;
    private PixelRenderStack renderStack;

    /**
     * Constructor with a scene renderer used to render pixels and a render stack to get pixel render jobs from.
     * 
     * @param renderer the renderer to render pixels
     * @param renderStack the stack to get pixel render jobs from
     */
    public PixelRenderThread(RaytracerSceneRenderer renderer, PixelRenderStack renderStack) {
        this.renderer = renderer;
        this.renderStack = renderStack;
    }
    
    /**
     * Renders pixel jobs on the stack until none remain.
     */
    @Override
    public void run() {
        PixelRender pixelRender;
        while((pixelRender = renderStack.pop()) != null) {
            renderer.castRay(pixelRender.pixels, pixelRender.row, pixelRender.col);
        }
    }
}
