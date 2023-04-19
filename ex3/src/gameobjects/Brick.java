package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {
    private Renderable renderable;
    private Vector2 dimensions;
    private Vector2 topLeftCorner;
    private CollisionStrategy collisionStrategy;
    private int num;
    private Counter counter;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, danogl.util.Counter counter, int num) {
        super(topLeftCorner, dimensions, renderable);
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.renderable = renderable;
        this.collisionStrategy = collisionStrategy;
        this.counter = counter;
        this.num = num;

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, counter);
    }

    public CollisionStrategy getCollisionStrategy() {
        return collisionStrategy;
    }
    public void setCollisionStrategy(CollisionStrategy collisionStrategy) {
        this.collisionStrategy = collisionStrategy;
    }
    public int getNum(){
        return num;
    }
    public void setNum(int num){
        this.num = num;
    }
    public Vector2 getTopLeftCorner(){
        return topLeftCorner;
    }
    public Vector2 getDimensions(){
        return dimensions;
    }
    public Renderable getRenderable(){
        return renderable;
    }
    public Counter getCounter(){
        return counter;
    }
}
