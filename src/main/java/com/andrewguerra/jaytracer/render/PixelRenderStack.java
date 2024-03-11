package com.andrewguerra.jaytracer.render;

import java.util.Stack;
import java.util.concurrent.Semaphore;

import com.andrewguerra.jaytracer.math.Ray;

/**
 * A class to represent a stack of available pixel render jobs. Operations on the stack are thread safe.
 */
public class PixelRenderStack {
    private Stack<PixelRender> renderStack;
    private Color[][] pixels;
    private Ray[][] rays;
    private final Semaphore semaphore;

    /**
     * Constructor for the pixel render stack with pixel matrix and rays array.
     * 
     * @param pixels the matrix of all pixels
     * @param rays the array of all pixel rays
     */
    public PixelRenderStack(Color[][] pixels, Ray[][] rays) {
        this.pixels = pixels;
        this.rays = rays;
        this.renderStack = new Stack<>();
        this.semaphore = new Semaphore(1);

        populateRenderStack();
    }

    private void populateRenderStack() {
        for(int row = 0; row < this.rays.length; row++) {
            for(int col = 0; col < this.rays[0].length; col++) {
                renderStack.add(new PixelRender(pixels, row, col, rays[row][col]));
            } 
        }
    } 

    /**
     * Returns and removes the first pixel render on the stack.
     * 
     * @return the first pixel render on the stack
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
     * Returns the number of remaining pixel renders on the stack.
     * 
     * @return the number of remaining pixel renders on the stack
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
