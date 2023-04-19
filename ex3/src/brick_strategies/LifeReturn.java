package src.brick_strategies;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.CollisionStrategy;
import src.gameobjects.Heart;


/**
 * add life to the game by create falling heart
 */

public class LifeReturn implements CollisionStrategy {
    private final ImageReader imageReader;
    private final Vector2 windowDimensions;
    private Heart[] hreats;
    private Counter lifeCounter;
    private GameObjectCollection gameObjectCollection;

    public LifeReturn(danogl.collisions.GameObjectCollection gameObjectCollection,
                      danogl.gui.ImageReader imageReader,
                      danogl.util.Vector2 windowDimensions,
                      Heart[] hreats, Counter lifeCounter){
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.windowDimensions = windowDimensions;
        this.hreats = hreats;
        this.lifeCounter = lifeCounter;

    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        gameObjectCollection.removeGameObject(thisObj);
        counter.decrement();
        Renderable image = imageReader.readImage("assets/heart.png", false);
        GameObject heart = new Heart(
                Vector2.ZERO, new Vector2(20, 20), image, gameObjectCollection,
                lifeCounter);
        heart.setCenter(new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()/2));
        heart.setVelocity(new Vector2(0, 100));
        gameObjectCollection.addGameObject(heart);
        for (int i = 0; i < hreats.length; i++) {
            if(hreats[i] == null){
                hreats[i] = (Heart) heart;
            }
        }
    }
}
