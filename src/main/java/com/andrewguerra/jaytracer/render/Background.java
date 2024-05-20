package com.andrewguerra.jaytracer.render;

import com.andrewguerra.jaytracer.math.Ray;

public abstract class Background {
    public abstract ColorUnbounded getColor(Ray ray); 
}
