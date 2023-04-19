package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.PerlinNoise;

import java.awt.*;

/**
 * make the terrain
 */
public class Terrain {

    private final PerlinNoise perlinNoise;
    GameObjectCollection gameObjectCollection;
    int groundLayer;
    Vector2 windowDimensions;

    public Terrain(GameObjectCollection gameObjects,
                   int groundLayer, Vector2 windowDimensions,
                   int seed) {
        this.gameObjectCollection = gameObjects;
        this.groundLayer = groundLayer;
        this.windowDimensions = windowDimensions;
        this.perlinNoise = new PerlinNoise(seed);

    }
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);

    /**
     * dicide the high of terrain
     * @param minX min x
     * @param maxX max x
     */
    public void createInRange(int minX, int maxX){
        for (int i = minX; i < maxX; i+= Block.SIZE) {
            double high = Math.floor(groundHeightAt(i) / Block.SIZE) * Block.SIZE;
            float condition = this.windowDimensions.y()*(3f/2);
            while (high <= condition) {
                makeBlock(i, high);
                high += Block.SIZE;
//                break;
            }
        }
    }

    /**
     * make one block
     * @param i x
     * @param high high
     */
    private void makeBlock(int i, double high){
        RectangleRenderable rectangleRenderable =
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
        GameObject block = new Block(new Vector2(i, (int)high), rectangleRenderable);
        block.setTag("ground");
        this.gameObjectCollection.addGameObject(block, groundLayer);
    }

    /**
     * use in perlin noise to choose y
     * @param x x
     * @return y
     */
    public float groundHeightAt(float x){
        float noise = perlinNoise.noise(x / 100) + 1;
        float y = this.windowDimensions.y();
        return y * noise * (2f/3);
    }
}
