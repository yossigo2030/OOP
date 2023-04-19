package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * make avatar
 */
public class Avatar extends GameObject {
    private final UserInputListener inputListener;
    Renderable ran;
    Renderable stand;
    Renderable fly;

    private float energy = 100;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,
                  ImageReader imageReader ) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        stand = imageReader.readImage("assets/stand.png", true);
        ran = imageReader.readImage("assets/ran.png", true);
        fly = imageReader.readImage("assets/fly.png", true);
        transform().setAccelerationY(500);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);


    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.renderer().setRenderable(this.stand);
        float velocity = 0;
        // left
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            velocity -= 300;
            this.renderer().setRenderable(this.ran);
            renderer().setIsFlippedHorizontally(true);
            transform().setVelocityX(velocity);
        }
        // right
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            velocity += 300;
            this.renderer().setRenderable(ran);
            renderer().setIsFlippedHorizontally(false);
            transform().setVelocityX(velocity);
        }
        transform().setVelocityX(velocity);
        // fly
        if(inputListener.isKeyPressed(KeyEvent.VK_SHIFT) &&
                inputListener.isKeyPressed(KeyEvent.VK_SPACE) && energy > 0){
            energy -= 0.5f;
            this.renderer().setRenderable(this.fly);
            transform().setVelocityY(-300);
        }
        // jump
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0){
                this.renderer().setRenderable(this.fly);
                transform().setVelocityY(-300);
        }
        if (transform().getVelocity().equals(Vector2.ZERO) && energy < 100) {
            energy += 0.5f;
        }
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals("ground")){
            this.setVelocity(Vector2.ZERO);
        }
    }

    public static Avatar create(GameObjectCollection gameObjects, int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener, ImageReader imageReader) {
        return null;
    }
}