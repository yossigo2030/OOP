package src.gameobjects;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import src.gameobjects.Paddle;
import src.brick_strategies.AddPaddleStrategy;



public class Mock extends GameObject{
    public static final int MOVE_SPEED = 300;

    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 20;
    public static boolean isInstantiated;
    private final int minDistanceFromEdge;
    private final src.brick_strategies.AddPaddleStrategy addPaddleStrategy;
    private GameObjectCollection gameObjectCollection;
    private UserInputListener inputListener;
    private int numCollisionsToDisappear;
    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object. Can be null, in which case
     * @param inputListener       The input about direction of paddle
     * @param windowDimensions    A dimensions of the window
     * @param minDistanceFromEdge
     */
    public Mock(danogl.util.Vector2 topLeftCorner,
                      danogl.util.Vector2 dimensions,
                      danogl.gui.rendering.Renderable renderable,
                      danogl.gui.UserInputListener inputListener,
                      danogl.util.Vector2 windowDimensions,
                      danogl.collisions.GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge,
                      int numOfCollisions, AddPaddleStrategy addPaddleStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.minDistanceFromEdge = minDistanceFromEdge;
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numOfCollisions;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.addPaddleStrategy = addPaddleStrategy;
    }

    /**
     * action in collision case
     * @param other
     * @param collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        numCollisionsToDisappear --;
        if(numCollisionsToDisappear == -1){
            addPaddleStrategy.setMockPaddleExist();
            gameObjectCollection.removeGameObject(this);
        }
    }

    /**
     * update the game
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && getTopLeftCorner().x() > this.minDistanceFromEdge){
            movementDir = movementDir.add(Vector2.LEFT.mult(MOVE_SPEED));
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && getTopLeftCorner().x() < windowDimensions.x() -
                this.minDistanceFromEdge - getDimensions().x()){
            movementDir = movementDir.add(Vector2.RIGHT.mult(MOVE_SPEED));
        }
        setVelocity(movementDir.mult(2));
    }
}
