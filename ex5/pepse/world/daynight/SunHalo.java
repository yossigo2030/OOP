package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * make sun halo
 */
public class SunHalo {
    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            GameObject sun,
            Color color){
        Renderable renderable = new OvalRenderable(color);
        Vector2 dimension = new Vector2(400, 400);
        GameObject sunHalo = new GameObject(sun.getTopLeftCorner(),
                dimension, renderable);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("sunHalo");
        gameObjects.addGameObject(sunHalo, layer);

        sunHalo.addComponent((deltaTime)->sunHalo.setCenter(sun.getCenter()));
        return sunHalo;
    }
}
