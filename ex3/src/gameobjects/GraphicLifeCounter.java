package src.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public GraphicLifeCounter(danogl.util.Vector2 widgetTopLeftCorner,
                              danogl.util.Vector2 widgetDimensions,
                              danogl.util.Counter livesCounter,
                              danogl.gui.rendering.Renderable widgetRenderable,
                              danogl.collisions.GameObjectCollection gameObjectsCollection,
                              int numOfLives){
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
