package src.gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;
import src.gameobjects.Ball;
import src.BrickerGameManager;



/**
 * A class to define the ball be agent in the game
 */
public class BallCollisionCount extends GameObject {

    private final BrickerGameManager gameManeger;
    private GameObjectCollection gameObjectCollection;

    /**
     * A ball
     */
    private Ball ball;

    /**
     * The owner of this class (a ChangeCameraStrategy)
     */
    private ChangeCameraStrategy owner;

    /**
     * A counter that got down after a collision of the ball (in case that the focus camera is change)
     */
    private int countDownValue;

    /**
     * A counter of collisions
     */
    private int countCollision;

    /**
     * A constructor
     * @param ball
     * @param owner
     * @param countDownValue
     */
    public BallCollisionCount(Ball ball, BrickerGameManager gameManager,
                              GameObjectCollection gameObjectCollection, int countDownValue) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.countDownValue = countDownValue;
        this.gameManeger = gameManager;
        this.gameObjectCollection = gameObjectCollection;
        countCollision = ball.getCollisionCount();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(countDownValue == 0){
            gameManeger.setCamera(null);
            gameObjectCollection.removeGameObject(this);

        }
        else if(ball.getCollisionCount() > countCollision){
            countCollision = ball.getCollisionCount();
            countDownValue--;
        }
    }
}
