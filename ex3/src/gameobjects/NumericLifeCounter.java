package src.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {
    private final TextRenderable textRenderable;
    private final Counter livesCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public NumericLifeCounter(danogl.util.Counter livesCounter,
                              danogl.util.Vector2 topLeftCorner,
                              danogl.util.Vector2 dimensions,
                              danogl.collisions.GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        textRenderable = new TextRenderable("LIFE: " + livesCounter.value());
        this.livesCounter = livesCounter;

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(livesCounter.value() == 4){
            textRenderable.setColor(Color.GREEN);
        }
        if(livesCounter.value() == 3){
            textRenderable.setColor(Color.GREEN);
        }
        if(livesCounter.value() == 2){
            textRenderable.setColor(Color.YELLOW);
        }
        if(livesCounter.value() == 1){
            textRenderable.setColor(Color.RED);
        }
        this.renderer().setRenderable(textRenderable);
    }
}
