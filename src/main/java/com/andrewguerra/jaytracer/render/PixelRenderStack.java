package com.andrewguerra.jaytracer.render;

import java.util.Stack;
import java.util.concurrent.Semaphore;

import com.andrewguerra.jaytracer.math.Ray;

public class PixelRenderStack {
    private Stack<PixelRender> renderStack;
    private Color[][] pixels;
    private Ray[][] rays;
    private final Semaphore semaphore;

    /**
     * 
     * @param pixels
     * @param rays
     */
    public PixelRenderStack(Color[][] pixels, Ray[][] rays) {
        this.pixels = pixels;
        this.rays = rays;
        this.renderStack = new Stack<>();
        this.semaphore = new Semaphore(1);

        populateRenderStack();
    }

    /**
     * 
     */
    private void populateRenderStack() {
        for(int row = 0; row < this.rays.length; row++) {
            for(int col = 0; col < this.rays[0].length; col++) {
                renderStack.add(new PixelRender(pixels, row, col, rays[row][col]));
            } 
        }
    } 

    /**
     * 
     * @return
     */
    public PixelRender pop() {
        PixelRender pixelRender;

        try {
            semaphore.acquire();

            if(renderStack.size() != 0) {
                pixelRender = renderStack.pop();
            } else {
                pixelRender = null;
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();

            return null;
        }

        semaphore.release();

        return pixelRender;
    }

    /**
     * 
     * @return
     */
    public boolean hasRendersLeft() {
        boolean hasRendersLeft;

        try {
            semaphore.acquire();
            hasRendersLeft = renderStack.size() != 0;
        } catch (InterruptedException e) {
            e.printStackTrace();

            return false;
        }

        semaphore.release();

        return hasRendersLeft;
    }
}
