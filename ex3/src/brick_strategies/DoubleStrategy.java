package src.brick_strategies;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;
import src.gameobjects.Brick;
import src.brick_strategies.BrickFactory;

/**
 * double strategy that create anothe srtategy to the brick
 */
public class DoubleStrategy implements CollisionStrategy {
    private GameObjectCollection gameObjectCollection;

    private BrickFactory brickFactory;
    public DoubleStrategy(danogl.collisions.GameObjectCollection gameObjectCollection, BrickFactory brickFactory){
        this.gameObjectCollection = gameObjectCollection;
        this.brickFactory = brickFactory;
    }
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        Brick myBrick = (Brick) thisObj;
        CollisionStrategy collisionStrategy;
        if(myBrick.getNum() == 0){
            collisionStrategy = brickFactory.getStrategy(1);
            myBrick.setCollisionStrategy(collisionStrategy);
            GameObject newBrick = new Brick(myBrick.getTopLeftCorner(), myBrick.getDimensions(),
                    myBrick.getRenderable(), collisionStrategy, myBrick.getCounter(), 1);
            gameObjectCollection.removeGameObject(thisObj);
            gameObjectCollection.addGameObject(newBrick);
        }
        else if (myBrick.getNum() == 1){
            collisionStrategy = brickFactory.getStrategy(2);
            myBrick.setCollisionStrategy(collisionStrategy);
            GameObject newBrick = new Brick(myBrick.getTopLeftCorner(), myBrick.getDimensions(),
                    myBrick.getRenderable(), collisionStrategy, myBrick.getCounter(), 2);
            gameObjectCollection.removeGameObject(thisObj);
            gameObjectCollection.addGameObject(newBrick);
        }
        else if(myBrick.getNum() == 2){
            collisionStrategy = brickFactory.getStrategy(3);
            myBrick.setCollisionStrategy(collisionStrategy);
            GameObject newBrick = new Brick(myBrick.getTopLeftCorner(), myBrick.getDimensions(),
                    myBrick.getRenderable(), collisionStrategy, myBrick.getCounter(), 3);
            gameObjectCollection.removeGameObject(thisObj);
            gameObjectCollection.addGameObject(newBrick);
        }
    }
}
