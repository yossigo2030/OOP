package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * make sun
 */
public class Sun {
    private static final Float MIDNIGHT_OPACITY = 180f;

    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            Vector2 windowDimensions,
            float cycleLength){
        Renderable cycle = new OvalRenderable(Color.YELLOW);
        Vector2 dimension = new Vector2(100, 100);
        GameObject sun = new GameObject(new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2),
                dimension, cycle);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");
        gameObjects.addGameObject(sun, layer);

        new Transition<Float>(
                sun, //the game object being changed
                (angel) -> sun.setCenter(ellipse(windowDimensions,angel)),  //the method to call
                0f,    //initial transition value
                MIDNIGHT_OPACITY,   //final transition value
                Transition.LINEAR_INTERPOLATOR_FLOAT,  //use a cubic interpolator
                cycleLength/2,   //transtion fully over half a day
                Transition.TransitionType.TRANSITION_LOOP, // Choose appropriate ENUM value
                null);  //nothing further to execute upon reaching final value
        gameObjects.addGameObject(sun, layer);
        return sun;

    }

    /**
     * make the sun go in ellipse angle
     * @param windowDimensions dimension
     * @param num angle
     * @return ellipse
     */
    private static Vector2 ellipse(Vector2 windowDimensions,
                                           float num){
        Vector2 vector2 = windowDimensions.mult(0.5f);
        int radius = 250;
        return new Vector2((float) (radius * Math.cos(num) + vector2.x()),
                (float) (radius * Math.sin(num) + vector2.y()));
    }
}
