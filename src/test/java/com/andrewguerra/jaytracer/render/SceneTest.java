package com.andrewguerra.jaytracer.render;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.Test;

public class SceneTest {
    @Test
    public void testConstructorEntitiesColor() {
        ArrayList<SceneEntity> entities = new ArrayList<>();
        //entities.add(mock(SceneEntity.class));

        Scene scene = new Scene(entities, BackgroundGradient.SKY);

        //assertEquals(1, scene.entities.size());
        //assertEquals(Color.WHITE, scene.backgroundColor);
        assertArrayEquals(entities.toArray(), scene.entities.toArray());
    }

    @Test
    public void testConstructorColor() {
        Scene scene = new Scene(BackgroundGradient.SKY);

        assertEquals(0, scene.entities.size());
        //assertEquals(Color.RED, scene.backgroundColor);
    }

    @Test
    public void testConstructorEmpty() {
        Scene scene = new Scene();

        assertEquals(0, scene.entities.size());
        //assertEquals(Color.BLACK, scene.backgroundColor);
    }
}
