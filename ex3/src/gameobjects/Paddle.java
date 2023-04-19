package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    public static final int MOVE_SPEED = 300;
    private final int minDistFromEdge;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions, int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && getTopLeftCorner().x() > minDistFromEdge){
            movementDir = movementDir.add(Vector2.LEFT.mult(MOVE_SPEED));
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && getTopLeftCorner().x() < windowDimensions.x() - minDistFromEdge - getDimensions().x()){
            movementDir = movementDir.add(Vector2.RIGHT.mult(MOVE_SPEED));
        }
        setVelocity(movementDir.mult(2));
    }
}
