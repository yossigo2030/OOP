package src.brick_strategies;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;
import src.brick_strategies.CollisionStrategy;


/**
 * without strategy just disapir
 */
public class SimpleStrategy implements CollisionStrategy {
    private GameObjectCollection gameObjectCollection;
    public SimpleStrategy(danogl.collisions.GameObjectCollection gameObjectCollection){
        this.gameObjectCollection = gameObjectCollection;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        gameObjectCollection.removeGameObject(thisObj);
        counter.decrement();
    }
}
