package pepse;

import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import danogl.GameManager;
import danogl.GameObject;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;

import java.awt.*;
import java.util.Random;

/**
 * run the all game
 */
public class PepseGameManager extends GameManager{
    private Tree tree;
    private Terrain terrain;
    private Avatar avatar;
    private WindowController windowController;
    /**
     * use to know min x of the current world
     */
    private int minX;
    /**
     * use to know max x of the current world
     */
    private int maxX;

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        Sky.create(this.gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);

        Random random = new Random();
        int seed = random.nextInt(100)+ 5;
        this.terrain = new Terrain(this.gameObjects(), Layer.STATIC_OBJECTS,
                windowController.getWindowDimensions(), seed);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        // not check
        double min = (Math.floor( -windowDimensions.x() / Block.SIZE) * Block.SIZE);
        double max = (Math.floor((2 * windowDimensions.x()) / Block.SIZE) * Block.SIZE);
        this.minX = (int) min;
        this.maxX = (int) max;
        terrain.createInRange(minX, maxX);
        Night.create(this.gameObjects(), Layer.FOREGROUND, windowDimensions, 10f);
        GameObject sun = Sun.create(this.gameObjects(), Layer.BACKGROUND, windowDimensions, 1000);
        SunHalo.create(this.gameObjects(), 10 + Layer.BACKGROUND, sun, new Color(255, 255, 0, 20));
        this.tree = new Tree(gameObjects(), terrain, seed);
        tree.createInRange(this.minX, this.maxX);
        gameObjects().layers().shouldLayersCollide(Layer.DEFAULT, Layer.STATIC_OBJECTS + 20, true);
        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.STATIC_OBJECTS + 20, true);


//        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.BACKGROUND + 20, true);


        // check

        Renderable image = imageReader.readImage("assets/stand.png", true);
//        System.out.println("C:\\Users\\OWNER\\IdeaProjects\\ex5\\src\\assets\\fly.png");
        float x = windowController.getWindowDimensions().x()* 1/2f;
        float y = terrain.groundHeightAt(x);
        Vector2 location = new Vector2(x, y - 100);
        Vector2 dimension = new Vector2(40, 40);
        this.avatar = new Avatar(location, dimension, image, inputListener, imageReader);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);
        setCamera(new Camera(avatar, windowController.getWindowDimensions().mult(0.5f).subtract(location),
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()));


    }
    @Override
    public void update(float deltaTime) {
        unlimitedWorld();
        removeObject();
        super.update(deltaTime);
    }

    /**
     * create unlimited world
     */
    private void unlimitedWorld() {
        int x = (int) windowController.getWindowDimensions().x();
        left(x);
        right(x);
    }

    /**
     * left world
     * @param x min x
     */
    private void left(int x){
        if (avatar.getCenter().x() - this.minX < x) {
            tree.createInRange(this.minX - x, this.minX);
            terrain.createInRange(this.minX - x ,this.minX);
            this.maxX -= x;
            this.minX -= x;
        }
    }

    /**
     * right world
     * @param x max x
     */
    private void right(int x){
        if (this.maxX - avatar.getCenter().x() < x) {
            tree.createInRange(this.maxX, this.maxX + x);
            terrain.createInRange(this.maxX, this.maxX + x);
            this.maxX += x;
            this.minX += x;
        }
    }

    /**
     * remove object that arr not in the screen right now
     */
    private void removeObject() {
        for(GameObject gameObject : gameObjects()){
            if(gameObject.getTopLeftCorner().x() < this.minX || gameObject.getTopLeftCorner().x() > this.maxX){
                gameObjects().removeGameObject(gameObject);
            }
        }
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
