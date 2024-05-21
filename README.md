# jaytracer

A simple Java based raytracer.

## Usage 

`Jaytracer.java` is defined as a command line application with the following parameters.
- sceneDescriptorFilePath (see [formatting](scene-file-formatting))
- imageFileName (name of rendered image)
- imageWidth
- imageHeight

## Scene File Formatting

```
{
  "entities": [
    {
      "type": String
      "material": {
        ...
      },
      "properties": {
        ...
      }
    },
    ...
  ],
  "background": {
    ...
  }
}
```

A scene file contains a singular scene object.

### Scene Object

```
{
  "entities": [
    ...
  ],
  "background": {
    ...
  }
}
```

A scene object contains a SceneEntity object list and a background object.

### SceneEntity Object

```
{
      "type": String
      "material": {
        ...
      },
      "properties": {
        ...
      }
    }
```

A SceneEntity object contains a type and a material. The type defines what type of SceneEntity the object is (sphere, quad, etc.). All SceneEntity objects must also
have a material to define how light is scattered of its surface or interior. A SceneEntity object can also have an optional properties object to define changes to the object's behavior (normal map, height map, etc).

#### Sphere

```
{
      "type": "sphere"
      "position": Vector3
      "radius": float
      "material": {
        ...
      },
      "properties": {
        ...
      }
    }
```

A Sphere is a SceneEntity object. It has a Vector3 position to represent its center point, and a float radius, along with the other components of a SceneEntity object.

#### Cylinder

```
{
      "type": "cylinder"
      "position": Vector3
      "radius": float
      "height": float
      "axis": Vector3
      "material": {
        ...
      },
      "properties": {
        ...
      }
    }
```

A Cylinder is a SceneEntity Object. It has a Vector3 position to represent the origin of the center of an end cap, a float radius, a float height, a Vector3 axis to represent the direction of the cylinder, along with the other components of a SceneEntity object.

#### Quad

```
{
      "type": "quad"
      "position": Vector3
      "u": Vector3
      "v": Vector3
      "material": {
        ...
      },
      "properties": {
        ...
      }
    }
```

A Quad is a SceneEntity Object. It has a Vector3 position to represent the origin of the direction vectors, a Vector3 u to represent a direction of the plane, a Vector3 v to represent the other direction of the plane, along with the other components of a SceneEntity object. Note that in this program, quads are defined as being strictly parallelograms.

#### Box

```
{
      "type": "box"
      "point1": Vector3
      "point2": Vector3
      "material": {
        ...
      },
      "properties": {
        ...
      }
    }
```

A Box is a SceneEntity Object. It has a Vector3 point1 to represent one corner of the box, a Vector3 point1 to represent the opposite corner of the box, along with the other components of a SceneEntity object. Note that in this program, quads are defined as being strictly parallelograms.

### Material

```
{
  "type": String
  ...
}
```

A Material object represents the material of a SceneEntity object. A Material can affect the color of an SceneEntity object, as well as how rays are scattered of that object. All Material objects have a String type which defines what type of material it is.

#### Diffuse

```
{
  "type": "diffuse",
  "albedo": Vector3
}
```

A Diffuse material is a Material object. A diffuse material is a material where most light ray reflects are scattered diffusely, meaning those rays are not reflected and are instead scattered in many directions due to surface imperfections. A Diffuse object has a Vector3 albedo to represent the color of the material.

#### Reflective

```
{
  "type": "reflective",
  "albedo": Vector3
  "reflectivity": float [0, 1]
}
```

A Reflective material is a Material object. A reflective material is a material where the light rays are reflected along the normal of the intersection point. A Reflective object has a Vector3 albedo to represent the color of the material, and a float reflectivity
on a domain of 0 to 1, where 1 represents a mirror surface and 0 represents a very blurry mirror surface. 

#### Dielectric

```
{
  "type": "dielectric",
  "refractionIndex": float
}
```

A Dielectric material is a Material object. A dielectric material is a material where the light rays are refracted by an objects surface. A Dielectric object has a float refractionIndex to represent how much rays are refracted by the SceneEntity object. 

#### Light

```
{
  "type": "light",
  "color": Vector3
}
```

A Light material is a Material object. A light material is a material where incoming rays are magnified due to the emitted light. A Light object has a Vector3 color to represent the color and intensity of the light, the greater the length of the vector, the greater the light intensity.

#### Texture

```
{
  "type": "texture",
  "texture": Texture
}
```

A Texture material is a Material object. A texture material is a material where a texture is mapped to the surface of a SceneEntity object. A Texture object contains a Texture object that defines what type of texture it is.

##### Texture

```
{
  "type": String
  ...
}
```

A Texture serves as a mapping of coordinates on a SceneEntity object, to UV coordinates, to an associated color. A Texture object has a type to define what type of texture it is.

###### Test

```
{
  "type": "test"
}
```

A Test Texture serves as a test to check to see if the UV coordinates are properly mapped on a SceneEntity object. For each intersection point, its color will be the (u, v, u + v).

###### Image

```
{
  "type": "image"
  "path": String
}
```

An Image Texture serves as a UV mapping to an image texture. An Image Texture has a String path to a image file.

### Properties

```
{
  "normalMap": String
}
```

A Properties object represents modifications to the behavior of a SceneEntity object. A Properties object has a series of optional properties to set. The normalMap property is a String path
to an image file of a normal mapping for a SceneEntity object.

### Background

```
{
  "type": ...
}
```

A Background object represents the color of the background of a scene. A Background object has a type to define what type it is.

#### Solid

```
{
  "type": "solid"
  "color": Vector3
}
```

A Solid Background object represents a scene background that is just one color. A Solid Background object has a Vector3 color to represent its color.

#### Gradient

```
{
  "type": "solid"
  "color": Vector3
}
```

A Gradient Background object represents a scene background that is the gradient between two colors, following the Y-axis. A Gradient Background object has a Vector3 topColor, and a Vector3 bottomColor.

### Value Formats

#### Vector3

A Vector3 is a string value that takes the form `"f, f, f"`, where f is a float value.

## Attributions

For inspiring me to start this project: 
- [Sebastian Lague](https://github.com/SebLague)

For providing me with guidance and invaluable insight:
- [_Ray Tracing in One Weekend_](https://raytracing.github.io/books/RayTracingInOneWeekend.html)
- [_Ray Tracing: The Next Week_](https://raytracing.github.io/books/RayTracingTheNextWeek.html)
