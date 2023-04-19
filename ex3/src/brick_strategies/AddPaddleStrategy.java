package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Mock;
import src.brick_strategies.CollisionStrategy;

/**
 * add paddle strategy that add paddle to the game
 */
public class AddPaddleStrategy implements CollisionStrategy{
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 150;
     /**
     * ImageReader instance
     */
    private ImageReader imageReader;
    private GameObjectCollection gameObjectCollection;

    /**
     * UserInputListener instance
     */
    private UserInputListener inputListener;
    /**
     * A vector of window dimensions
     */
    private Vector2 windowDimensions;

    private static boolean mockPaddleExist;

    /**
     * A constructor
     */
    public AddPaddleStrategy(danogl.collisions.GameObjectCollection gameObjectCollection,
                             danogl.gui.ImageReader imageReader,
                             danogl.gui.UserInputListener inputListener,
                             danogl.util.Vector2 windowDimensions, boolean mockPaddleExist) {
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.mockPaddleExist = mockPaddleExist;
    }

    /**
     * A behavior in case of collision
     * @param thisObj A paddle object
     * @param otherObj other object that collides in paddle object
     * @param counter counter of lives
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        gameObjectCollection.removeGameObject(thisObj);
        counter.decrement();
        if(!mockPaddleExist){
            mockPaddleExist = true;
            Renderable image = imageReader.readImage("assets/botGood.png", false);
            GameObject paddle = new src.gameobjects.Mock(
                    Vector2.ZERO,
                    new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), image, inputListener,
                    windowDimensions, gameObjectCollection, 0, 3, this);
            paddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()- 80));
            gameObjectCollection.addGameObject(paddle);
        }
    }
    public void setMockPaddleExist(){
        mockPaddleExist = false;
    }
}
