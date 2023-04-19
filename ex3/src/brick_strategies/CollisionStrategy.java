package src.brick_strategies;
/**
 * An interface of collision strategy
 */
public interface CollisionStrategy {
    /**
     * A method to define a behavior in collision case
     * @param thisObj The object in it collides
     * @param otherObj The object that collides
     * @param counter A counter of bricks
     */
    void onCollision(danogl.GameObject thisObj, danogl.GameObject otherObj, danogl.util.Counter counter);
}
