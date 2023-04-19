package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.util.Random;

/**
 * make tree
 */
public class Tree{

    private static final Color BASE_TREE_COLOR = new Color(100, 50, 20);
    private static final Color LEAF_TREE_COLOR = new Color(50, 200, 30);
    private final GameObjectCollection gameObjects;
    private final Terrain terrain;
    private final int seed;
    static Random random = new Random();


    public Tree(GameObjectCollection gameObjects,
                      Terrain terrain, int seed){
        this.gameObjects = gameObjects;
        this.terrain = terrain;
        this.seed = seed;

    }

    public void createInRange(int minX, int maxX) {
        Random random = new Random(seed);
        for (int i = minX; i < maxX; i+= Block.SIZE*2) {
            int flipCoin = random.nextInt(10);
            if(flipCoin == 0){
                float terrainHigh = (float) Math.floor(terrain.groundHeightAt(i) / Block.SIZE) * Block.SIZE;
                float treeHigh = (float)random.nextInt(4) + 4;
                Tree.create(gameObjects, i, terrainHigh, (terrainHigh - (treeHigh * Block.SIZE)));
            }
        }
    }
    public static void create(GameObjectCollection gameObjects, float x, float yMax, float yMin) {
        Tree.createStem(gameObjects, x, yMax, yMin);
        Tree.createLeaf(gameObjects, x, yMax, yMin);
    }
    private static void createStem(GameObjectCollection gameObjects, float x, float yMax, float yMin){
        while (yMin <= yMax) {
            RectangleRenderable rectangleRenderable =
                    new RectangleRenderable(ColorSupplier.approximateColor(BASE_TREE_COLOR));
            GameObject block = new Block(new Vector2(x, yMin), rectangleRenderable);
            block.setTag("Stem");
            gameObjects.addGameObject(block, Layer.STATIC_OBJECTS + 20);
            yMin += Block.SIZE;
        }
    }

    private static void createLeaf(GameObjectCollection gameObjects, float x, float yMax, float yMin){
        int width = random.nextInt(3) + 3;
        int high = random.nextInt(3) + 3;
        int xLeft = (int) (x - (width / 2) * Block.SIZE);
        int yLeft = (int) (yMin - (high / 2) * Block.SIZE);
        for (int row = xLeft; row < xLeft + width * Block.SIZE; row += Block.SIZE) {
            for (int col = yLeft; col < yLeft + high * Block.SIZE; col += Block.SIZE) {
                int flipCoin = random.nextInt(10);
                if (flipCoin < 9) {
                    RectangleRenderable rectangleRenderable =
                            new RectangleRenderable(ColorSupplier.approximateColor(LEAF_TREE_COLOR));
                    Leaf leaf = new Leaf(new Vector2(row, col), Vector2.ONES.mult(Block.SIZE), rectangleRenderable);
                    leaf.setTag("leaf");
                    leaf.alive();

                    gameObjects.addGameObject(leaf, Layer.STATIC_OBJECTS + 20);
                }
            }
        }
    }

}
