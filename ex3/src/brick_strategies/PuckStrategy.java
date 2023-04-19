package src.brick_strategies;


import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.gameobjects.Puck;
import java.util.Random;
import src.brick_strategies.CollisionStrategy;


/**
 * A class to add balls to the game
 */
public class PuckStrategy implements CollisionStrategy{

    /**
     * A constant of speed of ball
     */
    private static final float BALL_SPEED = 250;
    private GameObjectCollection gameObjectCollection;


    /**
     * A CollisionStrategy object
     */

    /**
     * ImageReader instance
     */
    private ImageReader imageReader;

    /**
     * SoundReader instance
     */
    private SoundReader soundReader;

    /**
     * a constructor
     * @param toBeDecorated
     * @param imageReader
     * @param soundReader
     */
    public PuckStrategy(danogl.collisions.GameObjectCollection gameObjectCollection,
                        danogl.gui.ImageReader imageReader, danogl.gui.SoundReader soundReader){
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }
    // ok
    /**
     *
     * @param thisObj The object in it collides
     * @param otherObj The object that collides
     * @param counter A counter of bricks
     */
    public void onCollision(danogl.GameObject thisObj, danogl.GameObject otherObj, danogl.util.Counter counter) {
        gameObjectCollection.removeGameObject(thisObj);
        counter.decrement();
        Random rand = new Random();
        for (int i = 0; i < 1; i++) {
            Renderable image = imageReader.readImage("assets/mockBall.png", false);
            Sound sound = soundReader.readSound("assets/blop_cut_silenced.wav");
            GameObject puck = new Puck(new Vector2(thisObj.getCenter().x(), thisObj.getCenter().y()), new Vector2(thisObj.getDimensions().x() / 3,
                    thisObj.getDimensions().y()), image, sound);
            float ballVelX = BALL_SPEED;
            float ballVely = BALL_SPEED;
            if (rand.nextBoolean()) {
                ballVelX *= -1;
            }
            if (rand.nextBoolean()) {
                ballVely *= -1;
            }
            puck.setVelocity(new Vector2(ballVelX, ballVely));
            gameObjectCollection.addGameObject(puck);
        }
    }
}
