package com.andrewguerra.jaytracer.render;

public class PixelRenderThread extends Thread {
    private RaytracerSceneRenderer renderer;
    private PixelRenderStack renderStack;

    /**
     * 
     * @param renderer
     * @param renderStack
     */
    public PixelRenderThread(RaytracerSceneRenderer renderer, PixelRenderStack renderStack) {
        this.renderer = renderer;
        this.renderStack = renderStack;
    }
    
    @Override
    public void run() {
        PixelRender pixelRender;
        while((pixelRender = renderStack.pop()) != null) {
            renderer.castRay(pixelRender.pixels, pixelRender.row, pixelRender.col);
        }
    }
}
