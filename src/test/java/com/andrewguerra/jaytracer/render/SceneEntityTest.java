package com.andrewguerra.jaytracer.render;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.andrewguerra.jaytracer.math.Vector3;

public class SceneEntityTest {
    public static SceneEntity sceneEntityMock;

    @Before
    public void initSceneEntityMock() {
        sceneEntityMock = mock(SceneEntity.class, Mockito.withSettings().useConstructor().defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @After
    public void resetSceneEntity() {
        sceneEntityMock.setPosition(Vector3.ZERO);
    }

    @Test
    public void testPositionConstructorVector() {
        SceneEntity sceneEntity = mock(SceneEntity.class, Mockito.withSettings().useConstructor(Vector3.ZERO, DiffuseMaterial.RED).defaultAnswer(Mockito.CALLS_REAL_METHODS));
        
        assertEquals(Vector3.ZERO, sceneEntity.getPosition());
    }

    @Test
    public void testPositionConstructor() {
        assertEquals(Vector3.ZERO, sceneEntityMock.getPosition());
    }

    @Test
    public void testGetPosition() {
        assertEquals(Vector3.ZERO, sceneEntityMock.getPosition());
    }

    @Test
    public void testSetPosition() {
        sceneEntityMock.setPosition(Vector3.X);

        assertEquals(Vector3.X, sceneEntityMock.getPosition());
    }
}
