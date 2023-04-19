package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.PuckStrategy;
import src.brick_strategies.AddPaddleStrategy;
import src.brick_strategies.ChangeCameraStrategy;
import src.brick_strategies.CollisionStrategy;
import src.brick_strategies.SimpleStrategy;
import src.brick_strategies.LifeReturn;
import src.gameobjects.Heart;
import src.brick_strategies.DoubleStrategy;
import src.gameobjects.Brick;


import java.util.Random;

/**
 * build the strategy of the bricks
 */
public class BrickFactory {
    private final boolean mockPaddleExist;
    private GameObjectCollection gameObjectCollection;
    private src.BrickerGameManager gameManager;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Counter lifeCounter;

    private Heart[] hearts;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private int countDouble = 2;

    public BrickFactory(danogl.collisions.GameObjectCollection gameObjectCollection,
                        src.BrickerGameManager gameManager,
                        danogl.gui.ImageReader imageReader,
                        danogl.gui.SoundReader soundReader,
                        danogl.gui.UserInputListener inputListener,
                        danogl.gui.WindowController windowController,
                        danogl.util.Vector2 windowDimensions, Heart[] hreat,
                        boolean mockPaddleExist, Counter lifeCounter) {
        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
        this.hearts = hreat;
        this.mockPaddleExist = mockPaddleExist;
        this.lifeCounter = lifeCounter;
    }

    private CollisionStrategy newBrick(int strategyNum){
        Random random = new Random();
        int stratagy = 0;
        if (strategyNum == 0){
            stratagy = random.nextInt(6);
        }
        if (strategyNum == 1){
            stratagy = random.nextInt(5);
        }
        if (strategyNum == 2){
            stratagy = random.nextInt(5);
        }
        if (strategyNum == 3){
            stratagy = 5;
        }
        switch (stratagy) {
            case 0:
                return new DoubleStrategy(gameObjectCollection, this);
            case 1:
                return new AddPaddleStrategy(gameObjectCollection, imageReader, inputListener,
                        windowDimensions, mockPaddleExist);
            case 2:
            return new PuckStrategy(gameObjectCollection, imageReader, soundReader);
            case 3:
            return new ChangeCameraStrategy(gameObjectCollection, windowController, gameManager);
            case 4:
            return new LifeReturn(gameObjectCollection, imageReader, windowDimensions, hearts, lifeCounter);
            case 5:
                return new SimpleStrategy(gameObjectCollection);
        }
        return new SimpleStrategy(gameObjectCollection);
    }
    public CollisionStrategy getStrategy(int strategyNum) {
        return newBrick(strategyNum);
    }

}
