package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.util.Random;

public class Leaf extends GameObject {
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private final Renderable renderable;
//    private final GameObjectCollection gameObjects;
    private Transition<Float> horizontalTransition;
    private int location;
    Random random = new Random();



    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Leaf(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.renderable = renderable;
        this.location = 0;

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.removeComponent(horizontalTransition);
        transform().setVelocityY(0);
        transform().setVelocityX(0);
    }
    void moveLeaf() {
        // angle
        new Transition<Float>(
                this, //the game object being changed
                (angel) -> this.renderer().setRenderableAngle(angel), //the method to call
                -4f,    //initial transition value
                4f,   //final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT,  //use a cubic interpolator
                2f,   //transtion fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                null);  //nothing further to execute upon reaching final value
        // width
        Vector2 originalBlock = new Vector2(Block.SIZE, Block.SIZE);
        Vector2 newBlock = new Vector2(Block.SIZE - 10, Block.SIZE - 2);
        new Transition<>(
                this, //the game object being changed
                this::setDimensions, //the method to call
                originalBlock, //initial transition value
                newBlock, //final transition value
                Transition.CUBIC_INTERPOLATOR_VECTOR, //use a cubic interpolator
                1f, //transtion fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                null); //nothing further to execute upon reaching final value
        fallingLeaf();
    }

    /**
     * get life to leafs
     */

    public void alive() {
//        Random random = new Random();
        new ScheduledTask(this, random.nextFloat() * 15,
                true, this::moveLeaf);
    }

    /**
     * falling leaf transmition
     */
    private void fallingLeaf(){
        if(location == 0 && random.nextFloat() <= 0.1){
            // falling status
            location = 1;
            // x velocity
            horizontalTransition = setHorizontal();
            // y velocity
            transform().setVelocityY(40);
            // fade and reLife
            renderer().fadeOut(10, this::reLife);
        }



    }

    /**
     * get re life to leafs
     */
    private void reLife(){
        // no fading
        renderer().setOpaqueness(1f);
        // return to first location
        setTopLeftCorner(topLeftCorner);
        // no velocity
        transform().setVelocityY(0);
        transform().setVelocityX(0);
        location = 0;
        new ScheduledTask(this, random.nextFloat() * 15,
                true, this::fallingLeaf);
    }
    private Transition<Float> setHorizontal() {
        return new Transition<>(
                this,
                horizontal -> transform().setVelocityX(horizontal),
                -10f,
                10f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                1f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }
}

