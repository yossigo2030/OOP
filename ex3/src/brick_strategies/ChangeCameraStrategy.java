//package brick_strategies;
//
//public class ChangeCameraStrategy {
//}
package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.Puck;
import src.gameobjects.BallCollisionCount;
//import src.gameobjects.RemoveBrickStrategyDecorator;
import src.brick_strategies.CollisionStrategy;





/**
 * A class to change the camera focus
 */
public class ChangeCameraStrategy implements src.brick_strategies.CollisionStrategy {
    /**
     * WindowController instance
     */
    private WindowController windowController;
    private GameObjectCollection gameObjectCollection;


    /**
     * The game manager
     */
    private BrickerGameManager gameManager;

    /**
     * BallCollisionCountdownAgent instance
     */
    private BallCollisionCount ballCollisionCount;

    /**
     * A CollisionStrategy object
     */


    /**
     * A flag to check if camera change is turn off
     */
    private boolean turnOffCameraChangeFlag;

    /**
     * A constructor
     * @param toBeDecorated
     * @param windowController
     * @param gameManager
     */
    public ChangeCameraStrategy(danogl.collisions.GameObjectCollection gameObjectCollection,
                                danogl.gui.WindowController windowController,
                                BrickerGameManager gameManager) {
        this.gameObjectCollection = gameObjectCollection;
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * Definition of behavior in case of collision
     * @param thisObj
     * @param otherObj A Ball object that collides
     * @param counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        gameObjectCollection.removeGameObject(thisObj);
        counter.decrement();
        if(gameManager.getCamera() == null && otherObj instanceof Ball){
            gameManager.setCamera(
                    new Camera(
                            otherObj,
                            Vector2.ZERO,
                            windowController.getWindowDimensions().mult(1.2f),
                            windowController.getWindowDimensions()));
            GameObject BallCollisionCount = new BallCollisionCount((Ball) otherObj,
                    gameManager, gameObjectCollection, 4);
            gameObjectCollection.addGameObject(BallCollisionCount);
        }
    }
}
